package com.ajna.bookaboat.service;

import com.ajna.bookaboat.exception.StorageException;
import com.ajna.bookaboat.exception.StorageFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileUploadService {

    private Path locationPath;

    @Value("${photostorage.path}")
    private String location;


    @PostConstruct
    public void init() {
        System.out.println("Location: " + location);
        this.locationPath = Paths.get(location);
        if (Files.notExists(locationPath)) {
            try {
                Files.createDirectory(locationPath);
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }
    }

    public String store(MultipartFile file) {
        String fileName;
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            // change the name to minimize chance of duplicate names
            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            if (fileName.contains("..")) {
                throw new StorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Files.copy(file.getInputStream(), this.locationPath.resolve(fileName));


        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return fileName;
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.locationPath, 1)
                    .filter(path -> !path.equals(this.locationPath))
                    .map(this.locationPath::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public Path load(String filename) {
        return locationPath.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public void delete(String filename){
        String fileLocation = location + filename;
        Path filePath = Paths.get(fileLocation);
        FileSystemUtils.deleteRecursively(filePath.toFile());
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(locationPath.toFile());
    }

}
