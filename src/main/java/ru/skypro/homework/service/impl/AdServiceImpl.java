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
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AccessRightsNotAvailableException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.CheckAdmin;
import ru.skypro.homework.utils.CheckAuthentication;
import ru.skypro.homework.utils.MethodLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final CheckAuthentication checkAuthentication;
    private final CheckAdmin checkAdmin;

    @Value("${path.to.photo.folder}")
    private String photoDir;

    @Override
    public AdsDTO getAllAds() {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        List<AdDTO> listOfAds = adRepository.findAll().stream()
                .map(AdMapper.INSTANCE::adToAdDTO).collect(Collectors.toList());
        return new AdsDTO(listOfAds.size(), listOfAds);
    }

    @Override
    @Transactional
    public AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image,
                       Authentication authentication) throws IOException {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);
        checkAdmin.checkAdminAccess(authentication);

        Ad ad = AdMapper.INSTANCE.createOrUpdateAdDTOToAd(createOrUpdateAdDTO);
        ad.setUser(userService.findByEmail(authentication.getName()));
        ad.setImage(imageService.addImage(image));
        adRepository.save(ad);

        uploadImageForAd(ad.getPk().intValue(), image);
        return AdMapper.INSTANCE.adToAdDTO(ad);
    }

    @Override
    public ExtendedAdDTO getById(Integer id, Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);
        checkAdmin.checkAdminAccess(authentication);

        Optional<Ad> ad = findById(Long.valueOf(id));
        checkAdIsPresent(ad);

        return ad.map(AdMapper.INSTANCE::toExtendedAdDTO).orElse(null);
    }

    @Override
    public void deleteAd(Integer id, Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);

        Optional<Ad> foundAd = findById(Long.valueOf(id));
        checkAdIsPresent(foundAd);

        Ad ad = foundAd.get();

        if (isAdCreatorOrAdmin(ad, authentication)) {
            adRepository.deleteById(id.longValue());
            log.info("Объявление удалено");
        } else {
            throw new AccessRightsNotAvailableException("Отсутствует доступ к объявлению");
        }
    }

    @Override
    public boolean isAdCreatorOrAdmin(Ad ad, Authentication authentication) {
        return userService.findByEmail(authentication.getName()).getRole() == Role.ADMIN
                || authentication.getName().equals(ad.getUser().getEmail());
    }

    @Override
    public AdDTO updateAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO,
                          Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        checkAuthentication.checkAuthentication(authentication);

        Optional<Ad> foundAd = findById(id.longValue());
        checkAdIsPresent(foundAd);

        Ad ad = foundAd.get();

        if (isAdCreatorOrAdmin(ad, authentication)) {
            ad.setPrice(createOrUpdateAdDTO.getPrice());
            ad.setTitle(createOrUpdateAdDTO.getTitle());
            ad.setDescription(createOrUpdateAdDTO.getDescription());

            adRepository.save(ad);

            return AdMapper.INSTANCE.adToAdDTO(ad);
        } else {
            log.error("Отсутствует доступ к объявлению");
            throw new AccessRightsNotAvailableException("Отсутствует доступ к объявлению");
        }
    }

    @Override
    public AdsDTO getAdsMe(Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);
        checkAdmin.checkAdminAccess(authentication);

        User user = userService.findByEmail(authentication.getName());
        List<AdDTO> result = new ArrayList<>();
        adRepository.findAllByUser(user).forEach(u -> result.add(AdMapper.INSTANCE.adToAdDTO(u)));
        return new AdsDTO(result.size(), result);
    }

    @Override
    public void updateImage(Integer id, MultipartFile image,
                            Authentication authentication) throws IOException {
        checkAuthentication.checkAuthentication(authentication);

        Optional<Ad> foundAd = findById(id.longValue());
        checkAdIsPresent(foundAd);
        Ad ad = foundAd.get();
        if (isAdCreatorOrAdmin(ad, authentication)) {
            uploadImageForAd(id, image);
        } else {
            log.error("Отсутствует доступ к объявлению");
            throw new AccessRightsNotAvailableException("Отсутствует доступ к объявлению");
        }
    }

    @Override
    public Optional<Ad> findById(Long id) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        return adRepository.findById(id);
    }

    @Override
    public void uploadImageForAd(Integer id, MultipartFile image) throws IOException {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        Ad ad = adRepository.findById(id.longValue()).get();
        imageService.uploadAd(ad.getPk().longValue(), image);

        adRepository.save(ad);
        log.info("Изображение объявления установлено: {}", ad);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public void checkAdIsPresent(Optional<Ad> ad) {
        if (!ad.isPresent()) {
            log.error("Объявление не найдено");
            throw new AdNotFoundException("Объявление не найдено");
        }
    }
}