package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.UserEntity;

@Component
public class AdMapper {

    public Ad toDto(AdEntity entity) {
        Ad dto = new Ad();
        dto.setPk(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPrice(entity.getPrice());
        dto.setImage(entity.getImage());
        dto.setAuthor(entity.getAuthor().getId());
        return dto;
    }

    public AdEntity toEntity(CreateOrUpdateAd dto, UserEntity author) {
        AdEntity entity = new AdEntity();
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setAuthor(author);
        return entity;
    }

    public void updateEntity(AdEntity entity, CreateOrUpdateAd dto) {
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
    }
}
