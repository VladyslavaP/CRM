package ua.nure.crm.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    byte[] loadPhoto(String photoId) throws IOException;

    String storePhoto(MultipartFile file) throws IOException;

    String getDefaultPhotoId();
}
