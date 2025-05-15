package project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.model.repositories.interfaces.ColorRepository;
import project.model.services.ColorServiceImpl;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ColorServiceTests {

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorServiceImpl colorService;


    @Test
    void deleteColorById_WhenExists_Deletes() {
        Mockito.when(colorRepository.existsById(1)).thenReturn(true);

        assertTrue(colorService.deleteColorById(1));
        Mockito.verify(colorRepository).deleteById(1);
    }
}
