package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    @Override
    public List<Ad> getAdsByUser(String email) {
        // Найти пользователя по email
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<AdEntity> ads = adRepository.findAllByAuthor(user);
        return ads.stream().map(adMapper::toDto).collect(java.util.stream.Collectors.toList());
    }
    @Override
    public void updateImage(Integer adId, String imagePath) {
        AdEntity ad = adRepository.findById(adId).orElseThrow(() -> new RuntimeException("Ad not found"));
        ad.setImage(imagePath);
        adRepository.save(ad);
    }

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;

    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll().stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Ad createAd(CreateOrUpdateAd dto, MultipartFile image, Authentication auth) {
        UserEntity user = userRepository.findByUsername(auth.getName())
                .orElseThrow(); // Здесь можно добавить кастомную ошибку

        AdEntity entity = adMapper.toEntity(dto, user);
        // Пример: сохраняем файл и путь к нему
        if (image != null && !image.isEmpty()) {
            String filename = image.getOriginalFilename();
            // TODO: Реализуйте сохранение файла на диск/в облако и получите путь
            entity.setImage(filename);
        }
        AdEntity saved = adRepository.save(entity);
        return adMapper.toDto(saved);
    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd dto, Authentication auth) {
        AdEntity ad = adRepository.findById(id).orElseThrow();

        if (!auth.getName().equals(ad.getAuthor().getUsername()) &&
                !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Not allowed");
        }

        adMapper.updateEntity(ad, dto); // Теперь здесь корректно используется description
        AdEntity saved = adRepository.save(ad);
        return adMapper.toDto(saved);
    }

    @Override
    public void deleteAd(Integer id, Authentication auth) {
        AdEntity ad = adRepository.findById(id).orElseThrow();

        if (!auth.getName().equals(ad.getAuthor().getUsername()) &&
                !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Not allowed");
        }

        adRepository.delete(ad);
    }
}

