package project.controller.controllers;

import org.springframework.http.ResponseEntity;
import project.controller.dto.CatDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.model.entities.collections.Colors;
import project.model.services.CatServiceImpl;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/cats")
@Validated
public class CatController {

    private final CatServiceImpl catService;

    public CatController(CatServiceImpl catServiceImpl) {
        this.catService = catServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<CatDto>> getAllCats() {
        List<CatDto> list = catService.getAllCats();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getCatById(@PathVariable Integer id) {
        CatDto cat = catService.getCatById(id);
        return ResponseEntity.ok(cat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatDto> updateCat(@PathVariable Integer id, @RequestBody CatDto catDto) {
        if (!Objects.equals(catDto.getId(), id)) {
            return ResponseEntity.status(400).build();
        }
        boolean result = catService.updateCat(catDto);
        if (result) {
            return ResponseEntity.ok(catDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable Integer id) {
        catService.deleteCatById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllCats() {
        catService.deleteAllCats();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CatDto> createCat(@RequestBody CatDto catDto) {
        CatDto savedCat = catService.createCat(catDto);
        return ResponseEntity.ok(savedCat);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CatDto>> getCatsByColorWithPagination(
            @RequestParam("color") String colorType,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Colors colorEnum = Colors.valueOf(colorType.toUpperCase());
        List<CatDto> cats = catService.getCatsByColorWithPagination(colorEnum, page, size);
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<CatDto>> getCatsByOwnerWithFilterAndPagination(
            @PathVariable Integer ownerId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<CatDto> cats = catService.getCatsByOwnerWithFilterAndPagination(ownerId, name, page, size);

        return ResponseEntity.ok(cats);
    }
}