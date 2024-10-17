package com.samia.ecole.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e) {
            throw new IOException("can not save uploaded file : " + fileName, e);
        }
    }
}
public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Extract the filename without the extension
        int extensionIndex = fileName.lastIndexOf('.');
        String fileNameWithoutExtension = fileName.substring(0, extensionIndex);
        // Extract the first 10 characters from the filename without extension
        String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));
        // Include the extension
        String newFileName = shortenedFileName + fileName.substring(extensionIndex);
        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e) {
            throw new IOException("can not save uploaded file : " + newFileName, e);
        }
    }
    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }
}*/
@Service
public class FileUploadUtil {
    private final CloudinaryUtil cloudinaryUtil;

    public FileUploadUtil(CloudinaryUtil cloudinaryUtil) {
        this.cloudinaryUtil = cloudinaryUtil;
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinaryUtil.uploadFile(multipartFile);
    }
    public void deleteFile(String publicId) throws IOException {
        cloudinaryUtil.deleteFile(publicId);
    }
}