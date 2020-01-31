package de.netview.service.Impl;

import de.netview.dao.IConvertJobDao;
import de.netview.model.ConvertJob;
import de.netview.service.IFileConverter;
import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileConverterService implements IFileConverter {

    @Autowired
    private IConvertJobDao convertJobDao;

    private static final String splitOutputDelimiter = ",";
    private static final String splitInputDelimier = ";";

    private HashMap<Integer, Integer> checkImport;
    private List<File> cleanFiles;
    private boolean insertNewLine = false;
    String inputType = "";


    public void setRows() {
        checkImport = new HashMap<>();
        if ("3er".equalsIgnoreCase(inputType)){
            checkImport.put(35, 0);
            checkImport.put(34, 1);
            checkImport.put(41, 2);
            checkImport.put(40, 3);
            checkImport.put(44, 5);
            checkImport.put(42, 7);
            checkImport.put(15, 16);
        } else if ("616er".equalsIgnoreCase(inputType)){
            checkImport.put(34, 0);
            checkImport.put(33, 1);
            checkImport.put(40, 2);
            checkImport.put(39, 3);
            checkImport.put(43, 5);
            checkImport.put(41, 7);
            checkImport.put(15, 16);
        }

    }

    private void setDefaultImportValue(String[] importValue) {
        importValue[4] = "49";
        importValue[6] = "DE";
        importValue[8] = "49";
        importValue[10] = "Reservix GmbH";
        importValue[11] = "Humboldstr. 2";
        importValue[12] = "FREIBURG IM BREISGAU";
        importValue[13] = "79098";
        importValue[14] = "0.2";
        importValue[15] = "0";
        importValue[17] = "Tickets";
        importValue[18] = "1";
        importValue[9] = "Versand";
        importValue[19] = "DE";
        importValue[20] = "0";
        importValue[21] = "144121191";
        importValue[22] = "versand-freiburg@reservix.de";
        importValue[23] = "07618878814";
    }


    @Transactional
    public void checkFolder() throws IOException {
        File dir = new File("\\\\rsvx.it\\Service\\UPS\\Input");
        File[] dir_contents = dir.listFiles();
        ConvertJob convertJob;
        cleanFiles = new ArrayList<>();
        checkImport = new HashMap<>();

        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().endsWith(".csv")) {
                convertJob = new ConvertJob();
                convertJob.setDate(new Date());
                convertJob.setInput(Files.readAllBytes(Paths.get(dir_contents[i].getAbsolutePath())));
                convertJob.setStatus("in Arbeit");

                if (dir_contents[i].getName().startsWith("3er")) {
                    convertJob.setName("Reservix 3er - Import");
                    inputType = "3er";
                } else if (dir_contents[i].getName().startsWith("616er")) {
                    convertJob.setName("Reservix 616er - Import");
                    insertNewLine = false;
                    inputType = "616er";
                }


                setRows();

                convertJobDao.addConvertJob(convertJob);

                try {
                    convertJob.setOutput(ConvertFile(dir_contents[i]));
                } catch (IOException e) {
                    System.out.println(e);
                }

                dir_contents[i].delete();

                convertJob.setStatus("Erledigt");
                convertJobDao.updateConvertJob(convertJob);
            }
        }

        for (File file : cleanFiles) {
            Files.move(file.toPath(), new File("\\\\rsvx.it\\Service\\UPS\\Archiv\\Archiv-" + file.getName()).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        cleanFiles.clear();
    }

    private byte[] zip(ArrayList<File> dateien, String output) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (File srcFile : dateien) {
            FileInputStream fis = new FileInputStream(srcFile);
            ZipEntry zipEntry = new ZipEntry(srcFile.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }

        zipOut.close();
        File zipData = new File(output);
        cleanFiles.add(zipData);
        return Files.readAllBytes(Paths.get(zipData.getAbsolutePath()));
    }

    @SuppressWarnings("resource")
    private byte[] ConvertFile(File file) throws IOException {
        ArrayList<String[]> csvExport = new ArrayList<>();
        readImportFile(file,csvExport);
		return exportResult(file, csvExport);
	}

	private byte[] exportResult(File file, ArrayList<String[]> csvExport) {
		try {
			String strDate = new SimpleDateFormat("yyyyMMdd HHmmssSSS").format(Calendar.getInstance().getTime());
			ArrayList<File> result = new ArrayList<File>();
			result.add(file);
			result.add(writeFile("\\\\rsvx.it\\\\Service\\UPS\\Output\\import-" + inputType + "-" + strDate + ".csv", csvExport));
			return zip(result, "\\\\rsvx.it\\Service\\UPS\\Archiv\\ouptut-" + strDate + ".zip");

		} catch (IOException e) {
			return null;
		}
	}

	private void readImportCSVValue3(String[] spaltenExport, ArrayList<String> csvImportSpalten) {
        for (int i = 0; i < csvImportSpalten.size(); i++) {
            Integer zeile = checkImport.get(i);
            if (zeile != null) {
                csvImportSpalten.get(i).replace(splitInputDelimier, ".");
                String value = "";
                if (i == 34) {
                    value = getNameValue(csvImportSpalten,i);
                } else if (i == 40) {
                    value = csvImportSpalten.get(i).replace(",", "");
                } else if (i== 44){
                    value = csvImportSpalten.get(i).contains("E") == true ? "07618878814" : csvImportSpalten.get(i);
                } else if (i == 35) {
                    value = csvImportSpalten.get(i);
                    if (StringUtils.isEmpty(value)) {
                        value = getNameValue(csvImportSpalten,34);
                    }
                }  else if (i == 22) {
                    value = (Double.parseDouble(csvImportSpalten.get(22).replace(",", ".")) + Double.parseDouble(csvImportSpalten.get(23).replace(",", "."))) + "";
                } else {
                    value = csvImportSpalten.get(i);
                }
                spaltenExport[zeile] = value == null ? "" : value;
            }
        }
    }

    private void readImportCSVValue616(String[] spaltenExport, ArrayList<String> csvImportSpalten) {
        for (int i = 0; i < csvImportSpalten.size(); i++) {
            Integer zeile = checkImport.get(i);
            if (zeile != null) {
                csvImportSpalten.get(i).replace(splitInputDelimier, ".");
                String value = "";
                if (i == 33) {
                    value = getNameValue(csvImportSpalten,i);
                } else if (i == 39) {
                    value = csvImportSpalten.get(i).replace(",", "");
                } else if (i== 43){
                    value = csvImportSpalten.get(i).contains("E") == true ? "07618878814" : csvImportSpalten.get(i);
                } else if (i == 34) {
                    value = csvImportSpalten.get(i);
                    if (StringUtils.isEmpty(value)) {
                        value = getNameValue(csvImportSpalten,33);
                    }
                }  else if (i == 22) {
                    value = (Double.parseDouble(csvImportSpalten.get(22).replace(",", ".")) + Double.parseDouble(csvImportSpalten.get(23).replace(",", "."))) + "";
                } else {
                    value = csvImportSpalten.get(i);
                }
                spaltenExport[zeile] = value == null ? "" : value;
            }
        }
    }

    private String getNameValue(ArrayList<String> csvImportSpalten, int i) {
        return csvImportSpalten.get(i) + " " + csvImportSpalten.get(i+3) + " " + csvImportSpalten.get(i+4);
    }

    private void cleanRowExport(String[] spaltenExport) {
        for (int index = 0; index < spaltenExport.length; index++) {
            if (StringUtils.isEmpty(spaltenExport[index])) {
                spaltenExport[index] = "";
            }
            if (spaltenExport[index].toLowerCase().contains("ä") || spaltenExport[index].toLowerCase().contains("ö") || spaltenExport[index].toLowerCase().contains("ü"))
                spaltenExport[index] = replaceUmlaut(spaltenExport[index]);
        }
    }

    private String replaceUmlaut(String input) {

        //replace all lower Umlauts
        String output = input.replace("ü", "ue")
                .replace("ö", "oe")
                .replace("ä", "ae");

        //now replace all the other capital umlaute
        output = output.replace("Ü", "Ue")
                .replace("Ö", "Oe")
                .replace("Ä", "Ae");

        return output;
    }

    private void readImportFile(File file, ArrayList<String[]> csvExport) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> spalten;
		String[] spaltenExport;
        String data = reader.readLine();
		String insert = "";

        while (data != null) {
            data = data.replace("\"", "");
            spalten = new ArrayList<>();
            String[] felder = data.split(splitInputDelimier);


            for (int index=0;index<felder.length;index++) {
                spalten.add(felder[index]);
                if (insertNewLine){
                    if (index == 31){
                        spalten.add("");
                    }
                }
            }

			if (spalten.size() > 14) {
				if (!insert.equalsIgnoreCase(spalten.get(14))) {
					spaltenExport = new String[24];
                    if (inputType.equalsIgnoreCase("3er")){
                        readImportCSVValue3(spaltenExport, spalten);
                    } else if (inputType.equalsIgnoreCase("616er")){
                        readImportCSVValue616(spaltenExport, spalten);
                    }
					setDefaultImportValue(spaltenExport);
					cleanRowExport(spaltenExport);
					csvExport.add(spaltenExport);

					insert = spalten.get(14);
				}
			}

            data = reader.readLine();

        }
        reader.close();
    }

    private File writeFile(String name, ArrayList<String[]> value) throws IOException {

        ArrayList<String> result = new ArrayList<String>();

        for (String[] zeile : value) {
            String temp = "";

            for (String spalte : zeile) {
                temp += spalte + splitOutputDelimiter;
            }
            result.add(temp);
        }
        File file = new File(name);
        FileWriter writer = new FileWriter(file);
        for (String str : result) {
            writer.write(str + "\n");
        }
        writer.close();

        return file;
    }
}
