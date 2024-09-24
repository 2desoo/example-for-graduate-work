package ru.skypro.homework.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.datical.liquibase.ext.init.InitProjectUtil.getExtension;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
@Data
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final AdRepository adRepository;

    @Value(value = "${path.to.image.folder}")
    private String photoDir;

    @Value(value = "${imageAd.dir.path}")
    private String imageAd;

    @Override
    public ResponseEntity<byte[]> getImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        byte[] imageBytes = image.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(imageBytes.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(imageBytes);
    }

    @Override
    public Image addImage(MultipartFile image) {
        Image imageNew = new Image();
        try {
            imageNew.setData(image.getBytes());
            imageNew.setMediaType(image.getContentType());
            imageNew.setFileSize(image.getSize());
            imageNew.setFilePath(photoDir + image.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepository.save(imageNew);
        log.info("Аватар добавлен: {}", imageNew);
        return imageNew;
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    @Override
    public void uploadAd(Long adId, MultipartFile file) throws IOException {
        if (file != null) {

            Ad ad = adRepository.findById(adId).get();

            Path filePath = buildFilePathAd(ad, file.getOriginalFilename());
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);

            try (
                    InputStream is = file.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            ) {
                bis.transferTo(bos);
            }

            Image image = new Image();
            image.setFileSize(file.getSize());
            image.setFilePath(filePath.toString());
            image.setMediaType(file.getContentType());
            image.setData(file.getBytes());
            imageRepository.save(image);
            ad.setImage(image);
        } else {
            log.warn("No file provided for upload.");
        }
    }

    public Path buildFilePathAd(Ad ad, String fileName) {
        return Path.of(imageAd, ad.getPk() + "." + getExtension(fileName));
    }
}
