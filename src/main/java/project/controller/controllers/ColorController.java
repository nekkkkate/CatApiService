package project.controller.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.controller.dto.ColorDto;
import project.model.services.ColorServiceImpl;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
    private final ColorServiceImpl colorService;

    public ColorController(ColorServiceImpl colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public ResponseEntity<List<ColorDto>> getAllColors() {
        return ResponseEntity.ok(colorService.getAllColors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorDto> getColorById(@PathVariable Integer id) {
        return ResponseEntity.ok(colorService.getColorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorDto> updateColor(@PathVariable Integer id, @RequestBody ColorDto colorDto) {
        if (!Objects.equals(colorDto.getId(), id)) {
            return ResponseEntity.status(404).build();
        }
        boolean result = colorService.updateColor(colorDto);
        if (result) {
            return ResponseEntity.ok(colorDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Integer id) {
        colorService.deleteColorById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllColors() {
        colorService.deleteAllColors();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ColorDto> createNewColor(@RequestBody String type) {
        ColorDto colorDto = colorService.createColor(type);
        return ResponseEntity.ok(colorDto);
    }
}