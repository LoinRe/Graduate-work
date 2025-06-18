package ru.skypro.homework.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Объявления")
public interface AdService {
    void updateImage(Integer adId, String imagePath);
    List<Ad> getAdsByUser(String email);
    List<Ad> getAllAds();
    ExtendedAd getAd(Integer id);
    Ad createAd(CreateOrUpdateAd dto, MultipartFile image, Authentication auth);
    Ad updateAd(Integer id, CreateOrUpdateAd dto, Authentication auth);
    void deleteAd(Integer id, Authentication auth);
}
