package ru.skypro.homework.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

public interface ImageService {

    /**
     * Получение изображения по ID
     * Метод использует {@link JpaRepository#findById(Object)}
     * @param id - id изображения
     * @return ResponseEntity с массивом байтов, представляющим изображение,
     * и соответствующими заголовками.
     */
    ResponseEntity<byte[]> getImage(Long id);

    /**
     * Добавление изображения в репозиторий
     * Метод использует {@link JpaRepository#save(Object)}
     * @param image - изображение
     * @return добавленное изображение
     */
    Image addImage(MultipartFile image);

    /**
     * Удаление изображения из репозитория
     * Метод использует {@link JpaRepository#deleteById(Object)}
     * @param imageId - id изображения
     */
    void deleteImage(Long imageId);
}
