package project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.controller.dto.OwnerDto;
import project.model.entities.Cat;
import project.model.entities.Owner;
import project.model.repositories.interfaces.CatRepository;
import project.model.repositories.interfaces.OwnerRepository;
import project.model.services.OwnerServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTests {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void createOwner_SavesOwnerDto() {
        OwnerDto dto = new OwnerDto(1, "Anna", LocalDate.of(1980, 5, 5));
        Owner entity = ownerService.toEntity(dto);

        when(ownerRepository.save(any(Owner.class))).thenReturn(entity);

        OwnerDto result = ownerService.createOwner(dto);

        assertEquals(dto.getName(), result.getName());
        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void deleteOwnerById_WhenExists_DeletesAndNullsCats() {
        Owner owner = new Owner();
        owner.setId(1);
        Cat cat = new Cat();
        cat.setOwner(owner);

        when(ownerRepository.existsById(1)).thenReturn(true);
        when(catRepository.findAll()).thenReturn(List.of(cat));

        boolean result = ownerService.deleteOwnerById(1);

        assertTrue(result);
        assertNull(cat.getOwner());
        verify(ownerRepository).deleteById(1);
    }

    @Test
    void addCat_WhenBothExist_AddsCatToOwner() {
        Owner owner = new Owner();
        owner.setId(1);
        Cat cat = new Cat();
        cat.setId(2);

        when(ownerRepository.existsById(1)).thenReturn(true);
        when(catRepository.existsById(2)).thenReturn(true);

        boolean result = ownerService.addCat(owner, cat);

        assertTrue(result);
        assertEquals(owner, cat.getOwner());
        verify(ownerRepository).save(owner);
        verify(catRepository).save(cat);
    }
}