package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;

    public boolean existsById(Integer id) {
        return adRepository.existsById(id.longValue());
    }

    public void deleteAd(Integer id) {
        adRepository.deleteById(id.longValue());
    }

    public Optional<AdEntity> updateAd(Integer id, CreateOrUpdateAdDTO properties) {
        Optional<AdEntity> adEntity = adRepository.findById(id.longValue());

        adEntity.get().setPrice(properties.getPrice());
        adEntity.get().setTitle(properties.getTitle());
        return Optional.of(adEntity.get());
    }
}