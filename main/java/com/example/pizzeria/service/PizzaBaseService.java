package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.dto.PizzaBaseDto;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.PizzaBaseMapper;
import com.example.pizzeria.repository.PizzaBaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PizzaBaseService {

    private final PizzaBaseRepository repository;

    public PizzaBaseService(PizzaBaseRepository repository) {
        this.repository = repository;
    }

    public List<PizzaBase> findAll() {
        return repository.findAll();
    }

    public PizzaBase findByPublicId(UUID publicId) {
        return repository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza base not found: " + publicId));
    }

    public PizzaBase create(PizzaBaseDto dto) {
        PizzaBase base = new PizzaBase();
        PizzaBaseMapper.updateEntity(dto, base);
        base.setPublicId(UUID.randomUUID());
        return repository.save(base);
    }

    public PizzaBase update(UUID publicId, PizzaBaseDto dto) {
        PizzaBase base = findByPublicId(publicId);
        PizzaBaseMapper.updateEntity(dto, base);
        return repository.save(base);
    }

    public void delete(UUID publicId) {
        repository.delete(findByPublicId(publicId));
    }
}
