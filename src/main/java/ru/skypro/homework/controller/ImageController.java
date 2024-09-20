package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository repository;

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage (@PathVariable Long id) {
        Optional<Image> image = repository.findById(id);
        if (image.isEmpty()) {
            return
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.get().getMediaType()));
        headers.setContentLength(image.get().getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image.get().getData());
    }
}
