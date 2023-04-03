package com.example.inventory.service;

import com.example.inventory.entity.Things;
import com.example.inventory.repository.ThingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThingsServiceImp implements ThingsService {

    ThingsRepository thingsRepository;

    @Autowired
    private void setThingsRepository(ThingsRepository thingsRepository) {
        this.thingsRepository = thingsRepository;
    }

    @Override
    public Optional<Things> getId(int id) {
        return thingsRepository.findById(id);
    }

    @Override
    public List<Things> getAll() {
        return thingsRepository.findAll();
    }

    @Override
    public Page<Things> getAll(Pageable paging) {
        return thingsRepository.findAll(paging);
    }

    @Override
    public Page<Things> findAllByCategory(int category, Pageable paging) {
        return thingsRepository.findAllByCategory(category, paging);
    }

    @Override
    public Page<Things> findByNameContaining(String name, Pageable paging) {
        return thingsRepository.findByNameContainingIgnoreCase(name, paging);
    }

    public Page<Things> findSearchCategory(Integer category, String name, Pageable paging) {
        return thingsRepository.findByNameContainingIgnoreCaseAndCategory(name, category, paging);
    }

    @Override
    public void create(Things things) {
        thingsRepository.save(things);
    }

    @Override
    public void update(Things things) {
        thingsRepository.save(things);
    }

    @Override
    public void delete(int id) {
        thingsRepository.deleteById(id);
    }
}
