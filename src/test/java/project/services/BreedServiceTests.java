package project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.controller.dto.BreedDto;
import project.model.entities.Breed;
import project.model.entities.collections.Breeds;
import project.model.repositories.interfaces.BreedRepository;
import project.model.services.BreedServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BreedServiceTests {

    @Mock
    private BreedRepository breedRepository;

    @InjectMocks
    private BreedServiceImpl breedService;

    @Test
    void createBreed_ValidType_ReturnsBreedDto() {
        String type = "SIAMESE";
        Breed breed = new Breed();
        breed.setId(1);
        breed.setType(Breeds.SIAMESE);

        Mockito.when(breedRepository.save(any(Breed.class))).thenReturn(breed);

        BreedDto result = breedService.createBreed(type);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(Breeds.SIAMESE, result.getType());
        Mockito.verify(breedRepository).save(any());
    }

    @Test
    void createBreed_InvalidType_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> breedService.createBreed("invalid"));
    }

    @Test
    void getBreedById_ReturnsBreedDto() {
        Breed breed = new Breed();
        breed.setId(1);
        breed.setType(Breeds.BENGAL);

        Mockito.when(breedRepository.getById(1)).thenReturn(breed);

        BreedDto result = breedService.getBreedById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(Breeds.BENGAL, result.getType());
    }

    @Test
    void getAllBreeds_ReturnsListOfBreedDto() {
        Breed breed1 = new Breed();
        breed1.setId(1);
        breed1.setType(Breeds.SIAMESE);

        Breed breed2 = new Breed();
        breed2.setId(2);
        breed2.setType(Breeds.BENGAL);

        Mockito.when(breedRepository.findAll()).thenReturn(Arrays.asList(breed1, breed2));

        List<BreedDto> result = breedService.getAllBreeds();

        assertEquals(2, result.size());
        assertEquals(Breeds.SIAMESE, result.get(0).getType());
        assertEquals(Breeds.BENGAL, result.get(1).getType());
    }

    @Test
    void updateBreed_WhenExists_ReturnsTrue() {
        BreedDto dto = new BreedDto(1, Breeds.SIAMESE);

        Mockito.when(breedRepository.existsById(1)).thenReturn(true);

        boolean updated = breedService.updateBreed(dto);

        assertTrue(updated);
        Mockito.verify(breedRepository).save(Mockito.any(Breed.class));
    }

    @Test
    void updateBreed_WhenNotExists_ReturnsFalse() {
        BreedDto dto = new BreedDto(99, Breeds.BENGAL);

        Mockito.when(breedRepository.existsById(99)).thenReturn(false);

        boolean updated = breedService.updateBreed(dto);

        assertFalse(updated);
        Mockito.verify(breedRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteBreedById_WhenExists_ReturnsTrue() {
        Mockito.when(breedRepository.existsById(1)).thenReturn(true);

        boolean result = breedService.deleteBreedById(1);

        assertTrue(result);
        Mockito.verify(breedRepository).deleteById(1);
    }

    @Test
    void deleteBreedById_WhenNotExists_ReturnsFalse() {
        Mockito.when(breedRepository.existsById(1)).thenReturn(false);

        boolean result = breedService.deleteBreedById(1);

        assertFalse(result);
        Mockito.verify(breedRepository, Mockito.never()).deleteById(1);
    }

    @Test
    void deleteAllBreeds_ExecutesCorrectly() {
        breedService.deleteAllBreeds();
        Mockito.verify(breedRepository).deleteAll();
    }
}