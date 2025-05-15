package project.controller.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.controller.dto.OwnerDto;
import project.model.services.OwnerServiceImpl;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerServiceImpl ownerService;

    public OwnerController(OwnerServiceImpl ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        List<OwnerDto> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable Integer id) {
        OwnerDto owner = ownerService.getOwnerById(id);
        return ResponseEntity.ok(owner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable Integer id, @RequestBody OwnerDto ownerDto) {
        if (!Objects.equals(ownerDto.getId(), id)) {
            return ResponseEntity.status(404).build();
        }
        boolean result = ownerService.updateOwner(ownerDto);
        if (result) {
            return ResponseEntity.ok(ownerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
        ownerService.deleteOwnerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllOwners() {
        ownerService.deleteAllOwners();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        OwnerDto saveOwner = ownerService.createOwner(ownerDto);
        return ResponseEntity.ok(saveOwner);
    }
}