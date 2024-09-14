package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
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
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserServiceImpl userService;
    private final ImageServiceImpl imageService;
    private final CommentRepository commentRepository;

    @Value("${path.to.photo.folder}")
    private String photoDir;

    public void uploadImageForAd(Long id, MultipartFile image) throws IOException {
        Ad ad = findById(id).get();

        Path filePath = Path.of(photoDir, ad.getPk() + "." + getExtension(Objects.requireNonNull(image.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try
                (InputStream is = image.getInputStream();
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
        ad.setImage(image1);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public AdsDTO getAllAds() {
        List<AdDTO> listOfAds= adRepository.findAll().stream()
                .map(AdMapper.INSTANCE::adToAdDTO).collect(Collectors.toList());
        return new AdsDTO(listOfAds.size(), listOfAds);
    }

    public AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image, Authentication authentication) throws IOException {
        Ad ad = AdMapper.INSTANCE.createOrUpdateAdDTOToAd(createOrUpdateAdDTO);
        ad.setUser(userService.findByEmail(authentication.getName()));
        adRepository.save(ad);

        uploadImageForAd(Long.valueOf(ad.getPk()), image);
        return AdMapper.INSTANCE.adToAdDTO(ad);
    }

    public ExtendedAdDTO getById(Integer id) {
        Optional<Ad> ad = adRepository.findById(Long.valueOf(id));

        return ad.map(AdMapper.INSTANCE::toExtendedAdDTO).orElse(null);
    }

    public Optional<Ad> findById(Long id) {
        return adRepository.findById(id);
    }

    public boolean deleteAd(Integer id) {
        Optional<Ad> foundAd = adRepository.findById(Long.valueOf(id));
        if (!foundAd.isPresent()) {
            return false;
        } else {
            Ad ad = foundAd.get();
            adRepository.getReferenceById(Long.valueOf(id))
                    .getComments().forEach(comment -> commentRepository.deleteAll());
            imageService.deleteImageById(ad.getImage().getId());
            adRepository.deleteById(id.longValue());
            return true;
        }
    }

    public boolean isAdCreatorOrAdmin(Integer id, Authentication authentication){
        Ad ad = adRepository.findById(Long.valueOf(id)).get();
        return userService.findByEmail(authentication.getName()).getRole() == Role.ADMIN
                || authentication.getName().equals(ad.getUser().getEmail());
    }

    public AdDTO updateAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        Ad ad = findById(id.longValue()).get();

        ad.setPrice(createOrUpdateAdDTO.getPrice());
        ad.setTitle(createOrUpdateAdDTO.getTitle());
        ad.setDescription(createOrUpdateAdDTO.getDescription());
        adRepository.save(ad);
        return AdMapper.INSTANCE.adToAdDTO(ad);
    }

    public AdsDTO getAdsMe(Authentication authentication) {
        List<AdDTO> listOfAds= adRepository.findAll().stream()
                .filter(ad -> (ad.getUser().getEmail()).equals(authentication.getName()))
                .map(AdMapper.INSTANCE::adToAdDTO).collect(Collectors.toList());
        return new AdsDTO(listOfAds.size(), listOfAds);
    }

//    public void updateImageForAd(Long id, MultipartFile image) throws IOException {
//        uploadImageForAd(id, image);
//    }
}