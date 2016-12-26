package ua.nure.crm.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.crm.service.PhotoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.currentTimeMillis;

@Service
public class PhotoServiceImpl implements PhotoService, InitializingBean {

    @Value("${local.photo.directory}")
    private String photoDirectoryPath;

    private Path photoDirectory;

    @Value("${local.photo.default}")
    private String defaultPhotoId;

    @Override
    public void afterPropertiesSet() throws Exception {
        photoDirectory = Paths.get(photoDirectoryPath);
        checkDirectoryExists(photoDirectory.toFile());
    }

    private void checkDirectoryExists(File directory) {
        if(!isExistentDirectory(directory))
            throw new IllegalStateException("Photo directory doesn't exist");
    }

    private boolean isExistentDirectory(File directory) {
        return directory.exists() && directory.isDirectory();
    }

    @Override
    public byte[] loadPhoto(String photoId) throws IOException {
        return Files.readAllBytes(findPhotoFile(photoId));
    }

    @Override
    public String storePhoto(MultipartFile file) throws IOException {
        String photoId = generateId(file);
        Path storagePath = photoDirectory.resolve(photoId);
        Files.copy(file.getInputStream(), storagePath);
        return photoId;
    }

    @Override
    public String getDefaultPhotoId() {
        return defaultPhotoId;
    }

    private String generateId(MultipartFile file) {
        return  Long.toString(currentTimeMillis(), 32);
    }

    private Path findPhotoFile(String photoId) throws IOException {
        Path photoPath = photoDirectory.resolve(photoId);
        if(!photoPath.toFile().exists())
            throw new IOException();
        return photoPath;
    }
}
