package ru.skypro.homework.service;

import ru.skypro.homework.entity.Image;

public interface ImageService {
    void saveImage(Image image);

    void deleteImageById(Long id);
}
