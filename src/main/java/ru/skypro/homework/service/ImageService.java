package ru.skypro.homework.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    /**
     * Получение изображения по ID
     * Метод использует {@link JpaRepository#findById(Object)}
     *
     * @param id - id изображения
     * @return ResponseEntity с массивом байтов, представляющим изображение,
     * и соответствующими заголовками.
     */
    byte[] getImage(Long id);


    void uploadAd(Long adId, MultipartFile file) throws IOException;

    void uploadUser(Long userId, MultipartFile file) throws IOException;

    /**
     * Удаление изображения из репозитория
     * Метод использует {@link JpaRepository#deleteById(Object)}
     * @param imageId - id изображения
     */
    void deleteImage(Long imageId);
}
