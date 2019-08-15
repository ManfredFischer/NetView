package de.netview.service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import de.netview.dao.IConvertJobDao;
import de.netview.dao.Impl.ConvertJobDao;
import de.netview.model.ConvertJob;
import de.netview.service.IFileConverter;

@Service
public class FileConverterService implements IFileConverter{
	
	@Autowired
	private IConvertJobDao convertJobDao;
	
	private HashMap<Integer,Integer> checkImport = new HashMap<>();
	private HashMap<Integer,Integer> checkWarenImport = new HashMap<>();
	private HashMap<String, String> warenWert = new HashMap<>();
	private List<File> cleanFiles = new ArrayList<>();
	
	private int referenzId = 13;
	private int warenWertId = 16;
	String inputType = "";
	
	public FileConverterService() {
		checkWarenImport.put(22, 0);
		checkWarenImport.put(26, 1);
		
		checkImport.put(30, 0);
		checkImport.put(29, 1); // Kommuliert: 29, 31,32,33
		checkImport.put(35, 2);
		checkImport.put(34, 3);
		checkImport.put(37, 5);

		checkImport.put(36, 8); // mit 0 vorne dran auffüllen! bis 5 Stellen
		checkImport.put(39, 9);
		checkImport.put(28, 11);
		
		checkImport.put(referenzId, 22); 
		
		
		// Bestellung Kommulieren zusammen mittels von BestellerID (13,14 > Eindeutig) den Warenwert ist die Summe aus 3er -> 16 (zeile) // 616 -> 17 (zeile)
	}
	
	
	private void setDefaultWarenValue(String[] warenValue) {
		warenValue[2] = "0";
		warenValue[3] = "DE";
		warenValue[4] = "PC";
		
		warenValue[6] = "EUR";
		warenValue[7] = "";
		warenValue[8] = "1";
		for (int i=9;i<13;i++) {
			warenValue[i] = "";
		}
	}
	
	private void setWarenWert(String[] warenValue) {		
		warenValue[5] = warenWert.get(warenValue[0]); 
	}
	
	private void setDefaultImportValue(String[] importValue ) {
		importValue[4] = "";
		importValue[6] = "";
		importValue[7] = "DE";
		importValue[10] = "";
		importValue[12] = "1";
		importValue[13] = "Reservix GmbH";
		importValue[14] = "";
		importValue[15] = "1";
		importValue[16] = "SV";
		importValue[17] = "EE";
		importValue[18] = "SHP";
		importValue[19] = "";
		importValue[20] = "";
		importValue[23] = "";
		importValue[24] = "";
		importValue[25] = "";
		importValue[26] = "Tickets";
		importValue[27] = "1";
		for (int i=28;i<28;i++) {
			importValue[i] = "";
		}
		
	}
	
	
	@Transactional
	public void checkFolder() throws IOException {
		File dir = new File("\\\\rsvx.it\\Service\\UPS\\Input");
	    File[] dir_contents = dir.listFiles();
	    ConvertJob convertJob;
	    
	    for(int i = 0; i<dir_contents.length;i++) {
	        if(dir_contents[i].getName().endsWith(".csv")) {
	        	convertJob = new ConvertJob();
	        	convertJob.setDate(new Date());
	        	convertJob.setInput(Files.readAllBytes(Paths.get(dir_contents[i].getAbsolutePath())));
	        	convertJob.setStatus("in Arbeit");
	        	
	        	if (dir_contents[i].getName().startsWith("3er")) {
	        		convertJob.setName("Reservix 3er - Import");
	        		inputType = "3er";
	        		referenzId = 13;
	        		warenWertId = 16;
	        	} else if (dir_contents[i].getName().startsWith("616er")) {
	        		convertJob.setName("Reservix 616er - Import");
	        		referenzId = 14;
	        		inputType = "616er";
	        		warenWertId = 17;
	        	} 
	        	
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
	    	file.delete();
	    }
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
            while((length = fis.read(bytes)) >= 0) {
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
		BufferedReader reader = new BufferedReader(new FileReader(file));
		ArrayList<ArrayList<String>> importAll = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> csvImport = new ArrayList<ArrayList<String>>();
		ArrayList<String> spalten;
		String data = reader.readLine();
		
		String bestellerID = "";
		while (data != null) {
			data = data.replace("\"", "");
			spalten = new ArrayList<String>();
			String[] felder = data.split(";");
			for(String value : felder) {
				spalten.add(value);
			}
			importAll.add(spalten);
			data = reader.readLine();
		}
		reader.close();
		
		Double warenWertResult = 0.0;
		for (int i=1;i<importAll.size();i++) {
			
			if (!importAll.get(i).get(referenzId).equals(bestellerID)) {				
				bestellerID = importAll.get(i).get(referenzId);
				csvImport.add(importAll.get(i));
				warenWertResult = 0.0;
			}
			
			String value = importAll.get(i).get(warenWertId).replace(",", ".").toString();
			
			warenWertResult+= Double.parseDouble(value);
			
			if (i != 1)
			 warenWert.put(bestellerID, warenWertResult+ "");
			
		}
		
		ArrayList<String[]> csvExport = new ArrayList<>();
		String[] spaltenExport = new String[47];
		
		for (ArrayList<String> csvImportSpalten  : csvImport) {
			spaltenExport = new String[47];
			for (int i=0;i< csvImportSpalten.size();i++) {
				Integer zeile = getExportZeile(i);
				if (zeile != null) {
					String value = "";
					if (i == 29) {
						value  = csvImportSpalten.get(29)+" "+csvImportSpalten.get(31)+" "+csvImportSpalten.get(32)+" "+csvImportSpalten.get(33);
					} else if (i == 36 && csvImportSpalten.get(29).length() != 5) {
						 int anz = 5-csvImportSpalten.get(29).length();
						 for (int a=0;a<anz;a++) {
							 value +="0";
						 }
						 value+=csvImportSpalten.get(i);
					} else if (i == referenzId) {
						value = csvImportSpalten.get(i);
						spaltenExport[21] = value; 
					} else {
						value = csvImportSpalten.get(i);
					}
					spaltenExport[zeile] = value == null ? "" : value;
				}
			}
			
			setDefaultImportValue(spaltenExport);	
			
			for (int index = 0; index<spaltenExport.length;index++) {
				if (spaltenExport[index] == null) {
					spaltenExport[index] = "";
				}
			}
			
			csvExport.add(spaltenExport);
		}
		
		ArrayList<String[]> warenExport = new ArrayList<>();
		String[] spaltenWarenExport;
		
		for (String[] spaltenValueExport : csvExport) {
			spaltenWarenExport = new String[13];
			for (int i=0;i< spaltenValueExport.length;i++) {
				if (getExportWarenZeile(i) != null) {
					spaltenWarenExport[getExportWarenZeile(i)] = spaltenValueExport[i];
				}
			}
			setDefaultWarenValue(spaltenWarenExport);
			setWarenWert(spaltenWarenExport);
			warenExport.add(spaltenWarenExport);
		}
		
		
		// Ergebniss:
		
		try { 
            String strDate =  new SimpleDateFormat("yyyyMMdd HHmmssSSS").format(Calendar.getInstance().getTime());  
            ArrayList<File> result = new ArrayList<File>();
            String firstLineExport ="Firma,Kontaktperson,Strasse,Adresse2,Adresse3,Ort,Staat,Land,PLZ,Telefon,UPS_KUNDENUMMER,Email,EmailOption,Absender_Firma,Email_Text,Gewicht,Serviceart,Pakettyp,BillTransportationTo,BillDutyTaxTo,SplitDutyAndTax,Referenz1,Rferenz2,Referenz3,Referenz4,Referenz5,Warenbeschreibung,Anzahl_Pakete,Rechnung_an_Dritte[TP]_Firma,TP_Kontakt,TP_Adr1,TP_ADR2,TP_ADR3,TP_PLZ,TP_ORT,TP_LAND,TP_STAAT,TP_TEL,TP_UPS_ACCOUNT,Rechnung_Erstellen,Währungskennung,Verkaufsbedingungen,Ausfuhrzweck,Zollerklärung,Frachtkosten,Versicherungskosten,sonstige_Tarife";
            String firstLineWare = "Key,Warenbeschreibung,Zolltarif,Ursprungsland/-gebiet,Maßeinheit,Stückpreis,Währungscode,Teilnummer,Anzahl,REWaehrung,REAusfuhrzweck,REZollerklaerung,REVerkaufsbedingung";
            result.add(writeFile("\\\\rsvx.it\\\\Service\\UPS\\Output\\import-"+inputType+"-"+strDate+".csv", csvExport, firstLineExport));
			result.add(writeFile("\\\\rsvx.it\\Service\\UPS\\Output\\Waren-"+inputType+"-"+strDate+".txt", warenExport, firstLineWare));
			
			return zip(result,"\\\\rsvx.it\\Service\\UPS\\temp\\ouptut-"+strDate+".zip");
			
		}catch(IOException e) {
			return null;
		}
		
		
	}
	
	private File writeFile(String name, ArrayList<String[]> value, String firstLine) throws IOException {
		
		ArrayList<String> result = new ArrayList<String>();
		
		for (String[] zeile : value) {
			String temp = "";
			
			for (String spalte : zeile) {
				temp += spalte+",";
			}
			result.add(temp);
		}
		File file = new File(name);
		FileWriter writer = new FileWriter(file); 
		writer.write(firstLine+"\n");
		for(String str: result) {
		  writer.write(str+"\n");
		}
		writer.close();
		
		return file;
	}
	
	private Integer getExportZeile(int spalteIndex) {
		return checkImport.get(spalteIndex);
	}
	
	private Integer getExportWarenZeile(int spalteIndex) {
		return checkWarenImport.get(spalteIndex);
	}
}
