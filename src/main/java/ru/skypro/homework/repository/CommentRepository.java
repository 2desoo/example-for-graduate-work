package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from \"comment\" c where c.ad_pk = :id", nativeQuery = true)
    List<Comment> findCommentsByIdAd(@Param("id") Long id);
}