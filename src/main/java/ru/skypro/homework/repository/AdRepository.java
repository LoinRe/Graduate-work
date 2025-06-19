package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.AdEntity;

import java.util.List;

import ru.skypro.homework.models.UserEntity;

public interface AdRepository extends JpaRepository<AdEntity, Integer> {
    List<AdEntity> findAllByAuthor(UserEntity author);

    List<AdEntity> findByAuthor_Id(Integer userId);
}
