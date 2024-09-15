package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    @Value(value = "${path.to.image.folder}")
    private String photoDir;

    @Override
    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }

}
