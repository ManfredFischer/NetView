package de.netview.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IImportService {
 public List importHardware(MultipartFile file) throws IOException;
 public List importLizenzen(MultipartFile file) throws IOException;
 public List importMobile(MultipartFile file) throws IOException;
}
