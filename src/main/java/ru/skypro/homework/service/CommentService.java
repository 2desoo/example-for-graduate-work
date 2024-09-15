package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;

/**
 * Сервис для управления комментариями
 */
public interface CommentService {
    /**
     * Метод для возращения списка всех комментариев к объявлению
     * @param id параметр по которому мы идентифицируем для какова объявления нужен список комментариев
     * С помощью {@link ru.skypro.homework.repository.AdRepository#existsById(Object id)}
     * Мы проверяем существует ли это объявления, если такого объявления не существует, срабатывает исключение
     * @throws ru.skypro.homework.exceptions.EntityNotFoundException
     * Если объявления существует, то создается List для {@link CommentDTO} где осуществляется поиск комментариев,
     * которые относятся к объявлению с помощью {@link ru.skypro.homework.repository.CommentRepository#findCommentsByIdAd(Long id)}
     * найденные комментария с помощью {@link ru.skypro.homework.mapper.CommentMapper#commentToCommentDTO(Comment)}
     * преобразуется в {@link CommentDTO} и эти DTO уже добавляются в List.
     * После этого создается {@link CommentsDTO} и в него добавляем наш List.
     * @return {@link CommentsDTO} список комментариев к объявлению.
     */
    CommentsDTO getComments(Long id);
    /**
     * Метод для создания комментария к объявлению
     * @param adId параметр по которому мы идентифицируем для какова объявления будет писаться комментарий
     * @param comment это {@link CreateOrUpdateCommentDTO} в котором будем принимать текст
     * @param authentication идентифицируем пользователя, который создает комментарий
     * С помощью {@link ru.skypro.homework.repository.AdRepository#existsById(Object id)}
     * Мы проверяем существует ли это объявления, если такого объявления не существует, срабатывает исключение
     * @throws ru.skypro.homework.exceptions.EntityNotFoundException
     * С помощью {@link ru.skypro.homework.repository.UserRepository#findByEmail(String)} идентифицируем автора комментария.
     * {@link ru.skypro.homework.repository.AdRepository#findById(Object)} идентифицируем объявления к которому будет писаться комментарий.
     * Создаем {@link Comment} используя {@link ru.skypro.homework.mapper.CommentMapper#createOrUpdateCommentDTOToComment(CreateOrUpdateCommentDTO)}
     * Сохраняем {@link Comment} используя {@link ru.skypro.homework.repository.CommentRepository#save(Object)}
     * @return {@link CommentDTO} используя {@link ru.skypro.homework.mapper.CommentMapper#commentToCommentDTO(Comment)}
     */
    CommentDTO createComment(Long adId, CreateOrUpdateCommentDTO comment, Authentication authentication);
    /**
     * Метод для удаления комментария.
     * @param adId параметр по которому мы идентифицируем объявления
     * @param commentId параметр по которому мы идентифицируем
     * С помощью {@link ru.skypro.homework.repository.CommentRepository#existsById(Object)}
     * Мы проверяем существует ли этот комментарий, если такого комментария не существует то, срабатывает исключение
     * @throws ru.skypro.homework.exceptions.EntityNotFoundException
     * используя метод {@link ru.skypro.homework.service.impl.CommentServiceImpl#getComment(Long)} мы находим комментарий
     * используя {@link ru.skypro.homework.repository.CommentRepository#delete(Object)} удаляем комментарий.
     */
    void removalComment(Long adId, Long commentId);

    /**
     * Метод для изменения комментария.
     * @param adId параметр по которому мы идентифицируем объявления
     * @param commentId параметр по которому мы идентифицируем
     * @param createOrUpdateCommentDTO это {@link CreateOrUpdateCommentDTO} в котором будем принимать текст
     * С помощью {@link ru.skypro.homework.repository.CommentRepository#existsById(Object)}
     * Мы проверяем существует ли этот комментарий, если такого комментария не существует то, срабатывает исключение
     * @throws ru.skypro.homework.exceptions.EntityNotFoundException
     * используя метод {@link ru.skypro.homework.service.impl.CommentServiceImpl#getComment(Long)} мы находим комментарий,
     * который будем изменять.
     * С помощью {@link ru.skypro.homework.repository.CommentRepository#save(Object)} сохраняем измененный комментарий
     * @return {@link CommentDTO} используя {@link ru.skypro.homework.mapper.CommentMapper#commentToCommentDTO(Comment)}
     */
    CommentDTO editComment(Long adId, Long commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}
