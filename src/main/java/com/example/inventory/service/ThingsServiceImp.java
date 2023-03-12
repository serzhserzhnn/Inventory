package com.example.inventory.service;

import com.example.inventory.entity.Things;
import com.example.inventory.repository.ThingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<Things> thing = thingsRepository.findById(id);
        return thing;
    }

    @Override
    public List<Things> getAll() {
        return thingsRepository.findAll();
    }

    @Override
    public List<Things> findAllByCategory(int category) {
        return thingsRepository.findAllByCategory(category);
    }

    @Override
    public List<Things> findSearch(String name) {
        return thingsRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Things> findSearchCategory(String name, Integer category) {
        return thingsRepository.findByNameContainingIgnoreCaseAndCategory(name, category);
    }

    @Override
    public void create(Things things) {

    }

    @Override
    public void update(Things things) {

    }

    @Override
    public void delete(int id) {
        thingsRepository.deleteById(id);
    }
}
