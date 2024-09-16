package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.MethodLog;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final CommentService commentService;

    @Value("${path.to.photo.folder}")
    private String photoDir;

    @Override
    public void uploadImageForAd(Long id, MultipartFile image) throws IOException {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        Ad ad = findById(id).get();
        log.info("Получено объявление: {}", ad);
        Path filePath = Path.of(photoDir, ad.getPk() + "." + getExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Image image1 = Optional.ofNullable(ad.getImage())
                .orElse(new Image());

        image1.setFilePath(filePath.toString());
        image1.setFileSize(image.getSize());
        image1.setMediaType(image.getContentType());
        image1.setData(image.getBytes());
        imageService.saveImage(image1);
        log.info("Изображение сохранено: {}", image1);
        ad.setImage(image1);
        adRepository.save(ad);
        log.info("Изображение объявления установлено: {}", ad);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public AdsDTO getAllAds() {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        List<AdDTO> listOfAds = adRepository.findAll().stream()
                .map(AdMapper.INSTANCE::adToAdDTO).collect(Collectors.toList());
        return new AdsDTO(listOfAds.size(), listOfAds);
    }

    @Override
    public AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image,
                       Authentication authentication) throws IOException {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        Ad ad = AdMapper.INSTANCE.createOrUpdateAdDTOToAd(createOrUpdateAdDTO);
        ad.setUser(userService.findByEmail(authentication.getName()));
        adRepository.save(ad);
        log.info("Объявление сохранено: {}", ad);

        uploadImageForAd(ad.getPk(), image);
        return AdMapper.INSTANCE.adToAdDTO(ad);
    }

    @Override
    public ExtendedAdDTO getById(Integer id) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        Optional<Ad> ad = findById(Long.valueOf(id));
        log.info("Получено объявление: {}", ad);

        return ad.map(AdMapper.INSTANCE::toExtendedAdDTO).orElse(null);
    }

    @Override
    public boolean deleteAd(Integer id) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        Optional<Ad> foundAd = findById(Long.valueOf(id));
        if (!foundAd.isPresent()) {
            log.info("Объявления не существует");
            return false;
        } else {
            Ad ad = foundAd.get();
            adRepository.getReferenceById(Long.valueOf(id))
                    .getComments().forEach(comment -> commentService.deleteAll());
            log.info("Комментарии удалены");
            imageService.deleteImage(ad.getImage().getId());
            log.info("Изображение удалено");
            adRepository.deleteById(id.longValue());
            log.info("Объявление удалено");
            return true;
        }
    }

    @Override
    public boolean isAdCreatorOrAdmin(Integer id, Authentication authentication){
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        Ad ad = findById(Long.valueOf(id)).get();
        log.info("Получено объявление: {}", ad);
        return userService.findByEmail(authentication.getName()).getRole() == Role.ADMIN
                || authentication.getName().equals(ad.getUser().getEmail());
    }

    @Override
    public AdDTO updateAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        Ad ad = findById(id.longValue()).get();
        log.info("Получено объявление: {}", ad);

        ad.setPrice(createOrUpdateAdDTO.getPrice());
        ad.setTitle(createOrUpdateAdDTO.getTitle());
        ad.setDescription(createOrUpdateAdDTO.getDescription());
        adRepository.save(ad);
        log.info("Объявление сохранено: {}", ad);
        return AdMapper.INSTANCE.adToAdDTO(ad);
    }

    @Override
    public AdsDTO getAdsMe(Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        List<AdDTO> listOfAds= adRepository.findAll().stream()
                .filter(ad -> (ad.getUser().getEmail()).equals(authentication.getName()))
                .map(AdMapper.INSTANCE::adToAdDTO).collect(Collectors.toList());
        return new AdsDTO(listOfAds.size(), listOfAds);
    }

    @Override
    public Optional<Ad> findById(Long id) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        return adRepository.findById(id);
    }
}