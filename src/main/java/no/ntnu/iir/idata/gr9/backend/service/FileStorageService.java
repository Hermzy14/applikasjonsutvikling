package no.ntnu.iir.idata.gr9.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class for handling file storage operations.
 */
@Service
public class FileStorageService {
  @Value("${file.upload-dir}")
  private String uploadDir;

  /**
   * Stores a file in the specified directory.
   *
   * @param file the file to be stored
   * @return the file name
   * @throws IOException if an error occurs during file storage
   */
  public String storeFile(MultipartFile file) throws IOException {
    Path uploadPath = Paths.get(this.uploadDir);
    // If the directory does not exist, create it
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    // Generate file name
    String fileName = file.getOriginalFilename();

    // Copy file to target location
    Path targetPath = uploadPath.resolve(fileName);
    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    // Return the file name
    return fileName;
  }

  /**
   * Deletes a file from the specified directory.
   *
   * @param fileName the name of the file to be deleted
   * @throws IOException if an error occurs during file deletion
   */
  public void deleteFile(String fileName) throws IOException {
    Path filePath = Paths.get(this.uploadDir).resolve(fileName);
    Files.deleteIfExists(filePath);
  }

}
