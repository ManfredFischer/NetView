package de.netview.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import de.netview.model.Hardware;
import de.netview.service.IHardwareService;
import de.netview.service.IImportService;

@Controller
@RequestMapping("/import")
public class ImportController {
	
	@Autowired
	private IImportService importService;

	@RequestMapping("/hardware")
    public void uploadHardware(@RequestParam("file") MultipartFile file) throws IOException {
		importService.importHardware(file);
    }
	
	@RequestMapping("/lizenz")
    public void uploadLizenz(@RequestParam("file") MultipartFile file) throws IOException {
		importService.importLizenzen(file);
    }
	
	@RequestMapping("/mobile")
    public void uploadMobile(@RequestParam("file") MultipartFile file) throws IOException {
		importService.importLizenzen(file);
    }
	
	

}