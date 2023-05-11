package com.example.inventory.controller;

import com.example.inventory.dto.ThingsDTO;
import com.example.inventory.entity.Things;
import com.example.inventory.service.ThingsService;
import org.modelmapper.ModelMapper;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/inventory", consumes = MediaType.ALL_VALUE)
public class ThingsController {
    ThingsService thingsService;

    @Autowired
    private void setThingsService(ThingsService thingsService) {
        this.thingsService = thingsService;
    }

    ModelMapper modelMapper;

    @Autowired
    private void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/thing/{id}")
    public ResponseEntity<Things> getId(@PathVariable("id") UUID id) {
        Optional<Things> thingData = thingsService.getId(id);
        return thingData.map(things -> new ResponseEntity<>(things, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/check_thing/{id}")
    public ResponseEntity<String> chkId(@PathVariable("id") UUID id) {
        Optional<Things> thingData = thingsService.getId(id);
        if (thingData.isPresent()) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.OK);
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
    public ResponseEntity<Things> create(@Valid @RequestBody ThingsDTO thingsDTO) {
        try {
            Things things = convertToEntity(thingsDTO);
            thingsService.create(new Things(things.getName(), things.getDescription(), things.getLocation(),
                    things.getCategory(), things.getQuantity(), things.getDateEnd(), things.getUserId()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/thing/{id}")
    public ResponseEntity<Things> update(@PathVariable("id") UUID id,
                                         @Valid @RequestBody ThingsDTO thingsDTO) {
        try {
            Things things = convertToEntity(thingsDTO);
            if (thingsService.getId(id).isPresent()) {
                thingsService.update(things);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/thing/{id}")
    public ResponseEntity<HttpStatus> deleteThing(@PathVariable("id") UUID id) {
        try {
            thingsService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Things convertToEntity(ThingsDTO thingsDTO) {
        return modelMapper.map(thingsDTO, Things.class);
    }
}
