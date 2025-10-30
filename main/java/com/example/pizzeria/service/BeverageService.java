package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Beverage;
import com.example.pizzeria.dto.BeverageDto;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.BeverageMapper;
import com.example.pizzeria.repository.BeverageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BeverageService {

    private final BeverageRepository repository;

    public BeverageService(BeverageRepository repository) {
        this.repository = repository;
    }

    public List<Beverage> findAll() {
        return repository.findAll();
    }

    public Beverage findByPublicId(UUID publicId) {
        return repository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Beverage not found: " + publicId));
    }

    public Beverage create(BeverageDto dto) {
        Beverage beverage = new Beverage();
        BeverageMapper.updateEntity(dto, beverage);
        beverage.setPublicId(UUID.randomUUID());
        return repository.save(beverage);
    }

    public Beverage update(UUID publicId, BeverageDto dto) {
        Beverage beverage = findByPublicId(publicId);
        BeverageMapper.updateEntity(dto, beverage);
        return repository.save(beverage);
    }

    public void delete(UUID publicId) {
        repository.delete(findByPublicId(publicId));
    }
}
