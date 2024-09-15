package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value = "select * from ad a where a.pk =:id", nativeQuery = true)
    Ad findAdById(@Param("id") Long id);
}
