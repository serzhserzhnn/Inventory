package com.example.inventory.controller;

import com.example.inventory.entity.Things;
import com.example.inventory.service.ThingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
        Optional<Things> thingData = thingsService.getId(id);
        return thingData.map(things -> new ResponseEntity<>(things, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/check_thing/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> chkId(@PathVariable("id") int id) {
        Optional<Things> thingData = thingsService.getId(id);
        if (thingData.isPresent()) {
            return new ResponseEntity<>("{\"check_thing\" : \"text-white\"}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"check_thing\" : \"text-danger\"}", HttpStatus.OK);
        }
    }

    @GetMapping("/things_chats")
    public ResponseEntity<List<Things>> getAll() {
        try {
            List<Things> thingsList = new ArrayList<>(thingsService.getAll());

            if (thingsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(thingsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/things", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            List<Things> thingsList;
            Pageable paging = PageRequest.of(page, size);

            Page<Things> pageThings;

            if (name == null)
                pageThings = thingsService.getAll(paging);
            else
                pageThings = thingsService.findByNameContaining(name, paging);

            thingsList = pageThings.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("things", thingsList);
            response.put("currentPage", pageThings.getNumber());
            response.put("totalItems", pageThings.getTotalElements());
            response.put("totalPages", pageThings.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/things/category/{category}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getCategories(@PathVariable("category") int category,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "3") int size) {
        try {
            List<Things> thingsList;

            Pageable paging = PageRequest.of(page, size);

            Page<Things> pageThings;

            if (name == null)
                pageThings = thingsService.findAllByCategory(category, paging);
            else
                pageThings = thingsService.findSearchCategory(category, name, paging);

            thingsList = pageThings.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("things", thingsList);
            response.put("currentPage", pageThings.getNumber());
            response.put("totalItems", pageThings.getTotalElements());
            response.put("totalPages", pageThings.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/thing_add")
    public ResponseEntity<Things> create(@Valid Things things) {
        try {
            System.out.println(things.toString());
            thingsService.create(new Things(things.getName(), things.getDescription(), things.getCategory()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
}
