package com.staynomadly.api.controller;

import com.staynomadly.api.entity.TouristAttraction;
import com.staynomadly.api.enums.Category;
import com.staynomadly.api.repository.TouristAttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class TouristAttractionController {

    private final TouristAttractionRepository attractionRepository;

    @GetMapping
    public ResponseEntity<List<TouristAttraction>> getAttractions(@RequestParam(required = false) Category category) {
        if (category != null) {
            return ResponseEntity.ok(attractionRepository.findByCategory(category));
        }
        return ResponseEntity.ok(attractionRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<TouristAttraction> createAttraction(@RequestBody TouristAttraction attraction) {
        return ResponseEntity.ok(attractionRepository.save(attraction));
    }
}
