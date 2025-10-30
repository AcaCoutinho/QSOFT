package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Side;
import com.example.pizzeria.dto.SideDto;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.SideMapper;
import com.example.pizzeria.repository.SideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SideService {

    private final SideRepository repository;

    public SideService(SideRepository repository) {
        this.repository = repository;
    }

    public List<Side> findAll() {
        return repository.findAll();
    }

    public Side findByPublicId(UUID publicId) {
        return repository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Side not found: " + publicId));
    }

    public Side create(SideDto dto) {
        Side side = new Side();
        SideMapper.updateEntity(dto, side);
        side.setPublicId(UUID.randomUUID());
        return repository.save(side);
    }

    public Side update(UUID publicId, SideDto dto) {
        Side side = findByPublicId(publicId);
        SideMapper.updateEntity(dto, side);
        return repository.save(side);
    }

    public void delete(UUID publicId) {
        repository.delete(findByPublicId(publicId));
    }
}
