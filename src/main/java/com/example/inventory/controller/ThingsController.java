package com.example.inventory.controller;

import com.example.inventory.entity.Things;
import com.example.inventory.service.ThingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/inventory", consumes = MediaType.ALL_VALUE)
public class ThingsController {
    ThingsService thingsService;

    @Autowired
    private void setThingsService(ThingsService thingsService) {
        this.thingsService = thingsService;
    }

    @GetMapping("/thing/{id}")
    public ResponseEntity<Things> getId(@PathVariable("id") int id) {
        Optional<Things> thinglData = thingsService.getId(id);
        if (thinglData.isPresent()) {
            return new ResponseEntity<>(thinglData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/thing/{id}")
    public ResponseEntity<HttpStatus> deleteThing(@PathVariable("id") int id) {
        try {
            thingsService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/things", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Things>> getAll(@RequestParam(required = false) String name) {
        try {
            List<Things> thingsList = new ArrayList<>();

            if (name == null)
                thingsService.getAll().forEach(thingsList::add);
            else
                thingsService.findSearch(name).forEach(thingsList::add);
            //?? thingsService.findSearchCategory(name, category).forEach(thingsList::add);

            if (thingsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(thingsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/things/category/{category}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Things>> getCategories(@PathVariable("category") int category, @RequestParam(required = false) String name) {
        try {
            List<Things> thingsList = new ArrayList<>();

            if (name == null)
                thingsService.findAllByCategory(category).forEach(thingsList::add);
            else
                //thingsService.findSearch(name).forEach(thingsList::add);
                thingsService.findSearchCategory(name, category).forEach(thingsList::add);

            if (thingsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(thingsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
