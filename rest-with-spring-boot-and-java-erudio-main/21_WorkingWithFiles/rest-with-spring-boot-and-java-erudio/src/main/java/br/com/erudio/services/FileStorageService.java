package br.com.erudio.services;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.exception.FileNotFoundException;
import br.com.erudio.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation; // onde vamos armazenar a variavel

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath() // caminho para salvar
                .toAbsolutePath().normalize(); // normaliza o caminho do arquivo

        this.fileStorageLocation = path;
        try {
            logger.info("Creating Directories");
            Files.createDirectories(this.fileStorageLocation); //cria o arquivo
        } catch (Exception e) {
            logger.error("Could not create the directory where files will be stored!");
            throw new FileStorageException("Could not create the directory where files will be stored!", e);
        }
    }

    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // pega o nome do arquivo e trata

        try {
            if (fileName.contains("..")) { // evita o proble do ../
                logger.error("Sorry! Filename Contains a Invalid path Sequence " + fileName);
                throw new FileStorageException("Sorry! Filename Contains a Invalid path Sequence " + fileName);
            }

            logger.info("Saving file in Disk");

            Path targetLocation = this.fileStorageLocation.resolve(fileName); // onde vai ser salvo e o nome do arquivo
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING); // lugar que salva o arquivo. e da replace
            return fileName;
        } catch (Exception e) {
            logger.error("Could not store file " + fileName + ". Please try Again!");
            throw new FileStorageException("Could not store file " + fileName + ". Please try Again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                logger.error("File not found " + fileName);
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (Exception e) {
            logger.error("File not found " + fileName);
            throw new FileNotFoundException("File not found " + fileName, e);
        }
    }
}
