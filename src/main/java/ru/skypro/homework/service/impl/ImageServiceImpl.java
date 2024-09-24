package ru.skypro.homework.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Value(value = "${imageUser.dir.path}")
    private String imageUser;
    @Value(value = "${imageAd.dir.path}")
    private String imageAd;

    @Value(value = "${imageAd.dir.path}")
    private String imageAd;

    @Override
    public byte[] getImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        return image.getData();
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

    @Override
    public void uploadUser(Long userId, MultipartFile file) throws IOException {
        if (file != null) {

            User user = userRepository.findById(userId).get();

            Path filePath = buildFilePathUser(user, file.getOriginalFilename());
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
            user.setImage(image);
        } else {
            log.warn("No file provided for upload.");
        }
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    public Path buildFilePathAd(Ad ad, String fileName) {
        return Path.of(imageAd, ad.getPk() + "." + getExtension(fileName));
    }
  
    public Path buildFilePathUser(User user, String fileName) {
        return Path.of(imageUser, user.getId() + "." + getExtension(fileName));
    }
}
