package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
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
    void uploadImageForAd(Integer id, MultipartFile image) throws IOException;

    /**
     * Получение всех объявлений
     * Метод использует {@link AdRepository#findAll()}
     * {@link AdMapper#adToAdDTO(Ad)}
     * @return new {@link AdsDTO}
     */
    AdsDTO getAllAds();

    /**
     * Создание объявления
     * Метод использует
     * {@link ru.skypro.homework.utils.CheckAuthentication#checkAuthentication(Authentication)}
     * {@link AdService#checkAdminAccess(Authentication)}
     * {@link AdMapper#createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO)}
     * {@link AdRepository#save(Object)}
     * {@link UserService#findByEmail(String)}
     * {@link AdService#uploadImageForAd(Integer, MultipartFile)}
     * @param createOrUpdateAdDTO - DTO модель класса {@link CreateOrUpdateAdDTO}
     * @param image - изображение
     * @param authentication - авторизация пользователя
     * @throws IOException
     * @return AdDTO - DTO модель класса {@link AdDTO}
     */
    AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image,
                Authentication authentication) throws IOException;

    /**
     * Получение информации об объявлении
     * Метод использует
     * {@link ru.skypro.homework.utils.CheckAuthentication#checkAuthentication(Authentication)}
     * {@link AdService#checkAdminAccess(Authentication)}
     * {@link AdService#findById(Long)}
     * {@link AdService#checkAdIsPresent(Optional)}
     * @param id - id для поиска объявления
     * @param authentication - авторизация пользователя
     * @return ExtendedAdDTO - DTO модель класса {@link ExtendedAdDTO}
     */
    ExtendedAdDTO getById(Integer id, Authentication authentication);

    /**
     * Удаление объявления
     * Метод использует
     * {@link ru.skypro.homework.utils.CheckAuthentication#checkAuthentication(Authentication)}
     * {@link AdService#findById(Long)}
     * {@link AdService#checkAdIsPresent(Optional)}
     * {@link AdService#isAdCreatorOrAdmin(Ad, Authentication)}
     * {@link AdRepository#getReferenceById(Object)}
     * {@link CommentService#deleteAll()}
     * {@link ImageService#deleteImage(Long)}
     * {@link AdRepository#deleteById(Object)}
     * @param id - id для поиска объявления
     * @param authentication - авторизация пользователя
     */
    void deleteAd(Integer id, Authentication authentication);

    /**
     * Метод для проверки является пользователь создателем объявления/админом или нет
     * Метод использует {@link AdService#findById(Long)}
     * {@link UserService#findByEmail(String)}
     * {@link Role#ADMIN}
     * @param ad - объявление
     * @param authentication - авторизация пользователя
     * @return true - если пользователь создатель объявления/админ; false - в противоположном случае
     */
    boolean isAdCreatorOrAdmin(Ad ad, Authentication authentication);

    /**
     * Обновление информации об объявлении
     * Метод использует
     * {@link ru.skypro.homework.utils.CheckAuthentication#checkAuthentication(Authentication)}
     * {@link AdService#checkAdIsPresent(Optional)}
     * {@link AdService#findById(Long)}
     * {@link AdService#isAdCreatorOrAdmin(Ad, Authentication)}
     * {@link AdRepository#save(Object)}
     * @param id                  - id для поиска объявления
     * @param createOrUpdateAdDTO - DTO модель класса {@link CreateOrUpdateAdDTO}
     * @param authentication - авторизация пользователя
     * @return AdDTO - DTO модель класса {@link AdDTO}
     */
    AdDTO updateAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO,
                   Authentication authentication);

    /**
     * Получение объявлений авторизованного пользователя
     * Метод использует
     * {@link ru.skypro.homework.utils.CheckAuthentication#checkAuthentication(Authentication)}
     * {@link AdService#checkAdminAccess(Authentication)}
     * {@link AdRepository#findAll()}
     * {@link AdMapper#adToAdDTO(Ad)}
     * @param authentication - авторизация пользователя
     * @return AdsDTO - DTO модель класса {@link AdsDTO}
     */
    AdsDTO getAdsMe(Authentication authentication);

    /**
     * Обновление изображения объявления
     * Метод использует
     * {@link ru.skypro.homework.utils.CheckAuthentication#checkAuthentication(Authentication)}
     * {@link AdService#checkAdIsPresent(Optional)}
     * {@link AdService#findById(Long)}
     * {@link AdService#isAdCreatorOrAdmin(Ad, Authentication)}
     * {@link AdService#uploadImageForAd(Integer, MultipartFile)}
     * @param id - id объявления
     * @param image - изображение
     * @param authentication - авторизация пользователя
     */
    void updateImage(Integer id, MultipartFile image,
                     Authentication authentication) throws IOException;

    /**
     * Поиск объявления по id
     * Метод использует {@link AdRepository#findById(Object)}
     * @param id
     * @return Optional<Ad>
     */
    Optional<Ad> findById(Long id);

    /**
     * Метод проверяет, что пользователь имеет роль админа
     * Если админ не имеет доступа к действию возникает
     * {@link ru.skypro.homework.exception.AdminAccessException}
     * с сообщением: "Администратор не может выполнять это действие"
     * @param authentication - авторизация пользователя
     */
    void checkAdminAccess(Authentication authentication);

    /**
     * Метод проверяет, что объявление существует
     * Если объявления не существует
     * {@link ru.skypro.homework.exception.AdNotFoundException}
     * с сообщением: "Администратор не может выполнять это действие"
     * @param ad - объявление, полученное в мотоде {@link AdService#findById(Long)}
     */
    void checkAdIsPresent(Optional<Ad> ad);
}
