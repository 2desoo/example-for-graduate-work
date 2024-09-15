package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
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

import java.io.IOException;
import java.util.Optional;

public interface AdService {

    /**
     * Загрузка изображения объявления
     * Метод использует {@link AdService#findById(Long)}
     * {@link AdRepository#save(Object)}
     * {@link ImageService#saveImage(Image)}
     * @param id - id для поиска объявления, у которого необходимо загрузить изображение
     * @param image
     * @throws IOException
     */
    void uploadImageForAd(Long id, MultipartFile image) throws IOException;

    /**
     * Получение всех объявлений
     * Метод использует {@link AdRepository#findAll()}
     * {@link AdMapper#adToAdDTO(Ad)}
     * @return new {@link AdsDTO}
     */
    AdsDTO getAllAds();


    /**
     * Создание объявления
     * Метод использует {@link AdMapper#createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO)}
     * {@link AdRepository#save(Object)}
     * {@link UserService#findByEmail(String)}
     * {@link AdService#uploadImageForAd(Long, MultipartFile)}
     * @param createOrUpdateAdDTO - DTO модель класса {@link CreateOrUpdateAdDTO}
     * @param image
     * @param authentication
     * @throws IOException
     * @return AdDTO - DTO модель класса {@link AdDTO}
     */
    AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image,
                Authentication authentication) throws IOException;

    /**
     * Получение информации об объявлении
     * Метод использует {@link AdService#findById(Long)}
     * @param id - id для поиска объявления
     * @return ExtendedAdDTO - DTO модель класса {@link ExtendedAdDTO}
     */
    ExtendedAdDTO getById(Integer id);

    /**
     * Удаление объявления
     * Метод использует {@link AdService#findById(Long)}
     * {@link AdRepository#getReferenceById(Object)}
     * {@link CommentService#deleteAll()}
     * {@link ImageService#deleteImageById(Long)}
     * {@link AdRepository#deleteById(Object)}
     * @param id - id для поиска объявления
     * @return false - если объявления не существует; true - если объявление удалено
     */
    boolean deleteAd(Integer id);

    /**
     * Метод для проверки является пользователь создателем объявления/админом или нет
     * Метод использует {@link AdService#findById(Long)}
     * {@link UserService#findByEmail(String)}
     * {@link Role#ADMIN}
     * @param id - id для поиска объявления
     * @param authentication
     * @return true - если пользователь создатель объявления/админ; false - в противоположном случае
     */
    boolean isAdCreatorOrAdmin(Integer id, Authentication authentication);

    /**
     * Обновление информации об объявлении
     * Метод использует {@link AdService#findById(Long)}
     * {@link AdRepository#save(Object)}
     * @param id - id для поиска объявления
     * @param createOrUpdateAdDTO - DTO модель класса {@link CreateOrUpdateAdDTO}
     * @return AdDTO - DTO модель класса {@link AdDTO}
     */
    AdDTO updateAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    /**
     * Получение объявлений авторизованного пользователя
     * Метод использует {@link AdRepository#findAll()}
     * {@link AdMapper#adToAdDTO(Ad)}
     * @param authentication
     * @return AdsDTO - DTO модель класса {@link AdsDTO}
     */
    AdsDTO getAdsMe(Authentication authentication);

    /**
     * Поиск объявления по id
     * Метод использует {@link AdRepository#findById(Object)}
     * @param id
     * @return Optional<Ad>
     */
    Optional<Ad> findById(Long id);
}
