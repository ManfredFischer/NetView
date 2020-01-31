package de.netview.function.impl;

import de.netview.function.IFileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Service
public class FileUtil implements IFileUtil {

    @Override
    public File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
}
