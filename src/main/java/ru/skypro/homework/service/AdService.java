package ru.skypro.homework.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Сервис для управления объявлениями.
 * Поддерживает операции CRUD и работу с изображениями согласно OpenAPI-спецификации.
 */
@Tag(name = "Объявления")
public interface AdService {
    /**
     * Сохраняет новый путь к изображению для существующего объявления.
     * @param adId      идентификатор объявления
     * @param imagePath относительный URL (например, "/media/file.jpg"), который будет сохранён
     */
    void updateImage(Integer adId, String imagePath);
    /**
     * Возвращает все объявления, созданные указанным пользователем.
     * @param email (или имя пользователя) автора
     * @return список объявлений пользователя
     */
    List<Ad> getAdsByUser(String email);
    /**
     * Возвращает список всех объявлений в системе.
     * @return список всех объявлений
     */
    List<Ad> getAllAds();
    /**
     * Получает полную информацию об объявлении вместе с данными автора.
     * @param id идентификатор объявления
     * @return расширенное представление объявления
     */
    ExtendedAd getAd(Integer id);
    /**
     * Создаёт новое объявление.
     * @param dto   данные с заголовком/ценой/описанием
     * @param image файл изображения для сохранения (может быть null)
     * @param auth  текущий аутентифицированный пользователь (автор)
     * @return DTO созданного объявления
     */
    Ad createAd(CreateOrUpdateAd dto, MultipartFile image, Authentication auth);
    /**
     * Обновляет объявление, если действующий пользователь — автор или ADMIN.
     * @param id   идентификатор объявления
     * @param dto  новые значения полей
     * @param auth пользователь, выполняющий обновление
     * @return DTO обновлённого объявления
     */
    Ad updateAd(Integer id, CreateOrUpdateAd dto, Authentication auth);
    /**
     * Удаляет объявление, если запрос выполняет автор или ADMIN.
     * @param id   идентификатор объявления
     * @param auth пользователь, выполняющий удаление
     */
    void deleteAd(Integer id, Authentication auth);
}
