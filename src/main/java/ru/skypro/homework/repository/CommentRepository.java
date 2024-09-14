package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import java.util.List;

/**
 * Репозиторий для комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from \"comment\" c join ad_comments ac on ac.ad_pk = :id where ac.comments_pk = c.pk", nativeQuery = true)
    List<Comment> findByIdAdOrComments(@Param("id") Integer id);

    @Query(value = "select * from ad a where a.pk =:id", nativeQuery = true)
    Ad findAdById(@Param("id") Integer id);
}