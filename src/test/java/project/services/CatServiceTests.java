package project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.controller.dto.CatDto;
import project.model.entities.Breed;
import project.model.entities.Cat;
import project.model.entities.Owner;
import project.model.entities.collections.Colors;
import project.model.repositories.interfaces.CatRepository;
import project.model.repositories.interfaces.OwnerRepository;
import project.model.services.CatServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatServiceTests {

    @Mock
    private CatRepository catRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private CatServiceImpl catService;

    @Test
    void createCat_SavesAndReturnsDto() {
        CatDto dto = new CatDto(1, "Barsik", LocalDate.of(2020, 1, 1), null, null, null,null);
        Cat cat = catService.toEntity(dto);

        when(catRepository.save(any(Cat.class))).thenReturn(cat);

        CatDto result = catService.createCat(dto);

        assertEquals(dto.getName(), result.getName());
        verify(catRepository).save(any(Cat.class));
    }

    @Test
    void getCatById_ReturnsDto() {
        Cat cat = new Cat();
        cat.setId(1);
        cat.setName("Murka");
        cat.setBirthDate(LocalDate.of(2019, 3, 15));
        cat.setColor(null);

        when(catRepository.getById(1)).thenReturn(cat);

        CatDto dto = catService.getCatById(1);

        assertEquals(cat.getId(), dto.getId());
        assertEquals(cat.getName(), dto.getName());
    }

    @Test
    void getAllCats_ReturnsListOfDto() {
        Cat cat1 = new Cat();
        cat1.setId(1);
        cat1.setName("Cat1");
        Cat cat2 = new Cat();
        cat2.setId(2);
        cat2.setName("Cat2");

        when(catRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        List<CatDto> result = catService.getAllCats();

        assertEquals(2, result.size());
        assertEquals("Cat1", result.get(0).getName());
        assertEquals("Cat2", result.get(1).getName());
    }

    @Test
    void updateCat_WhenExists_UpdatesAndReturnsTrue() {
        CatDto dto = new CatDto(1, "UpdatedCat", LocalDate.now(), null, new Breed(), null, null);

        when(catRepository.existsById(1)).thenReturn(true);

        boolean result = catService.updateCat(dto);

        assertTrue(result);
        verify(catRepository).save(any(Cat.class));
    }

    @Test
    void updateCat_WhenNotExists_ReturnsFalse() {
        CatDto dto = new CatDto(99, "GhostCat", LocalDate.now(), null, null, null, null);

        when(catRepository.existsById(99)).thenReturn(false);

        boolean result = catService.updateCat(dto);

        assertFalse(result);
        verify(catRepository, never()).save(any());
    }

    @Test
    void deleteCatById_WithOwner_RemovesAndDeletes() {
        Owner owner = new Owner();
        Cat cat = new Cat();
        cat.setId(1);
        cat.setOwner(owner);

        when(catRepository.existsById(1)).thenReturn(true);
        when(catRepository.getById(1)).thenReturn(cat);

        boolean result = catService.deleteCatById(1);

        assertTrue(result);
        verify(ownerRepository).save(owner);
        verify(catRepository).deleteById(1);
    }

    @Test
    void deleteCatById_WhenNotExists_ReturnsFalse() {
        when(catRepository.existsById(1)).thenReturn(false);

        boolean result = catService.deleteCatById(1);

        assertFalse(result);
        verify(catRepository, never()).deleteById(1);
    }

    @Test
    void deleteAllCats_RemovesAllFromOwnersAndDeletes() {
        Owner owner = new Owner();
        Cat cat1 = new Cat();
        cat1.setId(1);
        cat1.setOwner(owner);
        Cat cat2 = new Cat();
        cat2.setId(2);
        cat2.setOwner(owner);

        when(catRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        catService.deleteAllCats();

        verify(ownerRepository, times(2)).save(owner);
        verify(catRepository).deleteAll();
    }

    @Test
    void AddCatFriend_WhenBothExist_AddsEachOther() {
        Cat cat1 = new Cat();
        cat1.setId(1);
        Cat cat2 = new Cat();
        cat2.setId(2);

        when(catRepository.existsById(1)).thenReturn(true);
        when(catRepository.existsById(2)).thenReturn(true);

        boolean result = catService.AddCatFriend(cat1, cat2);

        assertTrue(result);
        assertTrue(cat1.getCatsFriends().contains(cat2));
        assertTrue(cat2.getCatsFriends().contains(cat1));
    }

    @Test
    void DeleteCatFriend_WhenBothExist_RemovesEachOther() {
        Cat cat1 = new Cat();
        cat1.setId(1);
        Cat cat2 = new Cat();
        cat2.setId(2);
        cat1.addFriend(cat2);
        cat2.addFriend(cat1);

        when(catRepository.existsById(1)).thenReturn(true);
        when(catRepository.existsById(2)).thenReturn(true);

        boolean result = catService.DeleteCatFriend(cat1, cat2);

        assertTrue(result);
        assertFalse(cat1.getCatsFriends().contains(cat2));
        assertFalse(cat2.getCatsFriends().contains(cat1));
    }
}