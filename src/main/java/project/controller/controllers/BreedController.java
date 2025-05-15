package project.controller.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.controller.dto.BreedDto;
import project.model.services.BreedServiceImpl;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/breeds")
public class BreedController {
    private final BreedServiceImpl breedService;

    public BreedController(BreedServiceImpl breedService) {
        this.breedService = breedService;
    }

    @GetMapping
    public ResponseEntity<List<BreedDto>> getAllBreeds() {
        return ResponseEntity.ok(breedService.getAllBreeds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedDto> getBreedById(@PathVariable Integer id) {
        return ResponseEntity.ok(breedService.getBreedById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreedDto> updateBreed(@PathVariable Integer id, @RequestBody BreedDto breedDto) {
        if (!Objects.equals(breedDto.getId(), id)) {
            return ResponseEntity.status(400).build();
        }
        boolean result = breedService.updateBreed(breedDto);
        if (result) {
            return ResponseEntity.ok(breedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreed(@PathVariable Integer id) {
        breedService.deleteBreedById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllBreeds() {
        breedService.deleteAllBreeds();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BreedDto> createNewBreed(@RequestBody String type) {
        return ResponseEntity.ok(breedService.createBreed(type));
    }
}