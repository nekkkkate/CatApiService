package project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.controller.controllers.BreedController;
import project.controller.dto.BreedDto;
import project.model.entities.collections.Breeds;
import project.model.services.BreedServiceImpl;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BreedController.class)
public class BreedControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BreedServiceImpl breedService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBreeds_ShouldReturnListOfBreeds() throws Exception {
        List<BreedDto> breeds = List.of(
                new BreedDto(1, Breeds.SIAMESE),
                new BreedDto(2, Breeds.BENGAL)
        );

        when(breedService.getAllBreeds()).thenReturn(breeds);

        mockMvc.perform(get("/api/breeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].type", is("SIAMESE")))
                .andExpect(jsonPath("$[1].type", is("BENGAL")));
    }

    @Test
    void getBreedById_ShouldReturnBreed() throws Exception {
        BreedDto breedDto = new BreedDto(1, Breeds.SPHYNX);

        when(breedService.getBreedById(1)).thenReturn(breedDto);

        mockMvc.perform(get("/api/breeds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("SPHYNX")));
    }

    @Test
    void updateBreed_WhenIdMatches_ShouldReturnUpdatedBreed() throws Exception {
        BreedDto breedDto = new BreedDto(1, Breeds.SIAMESE);

        when(breedService.updateBreed(breedDto)).thenReturn(true);

        mockMvc.perform(put("/api/breeds/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(breedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("SIAMESE")));
    }

    @Test
    void updateBreed_WhenNotFound_ShouldReturn404() throws Exception {
        BreedDto breedDto = new BreedDto(1, Breeds.SIAMESE);

        when(breedService.updateBreed(breedDto)).thenReturn(false);

        mockMvc.perform(put("/api/breeds/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(breedDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBreedById_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/breeds/1"))
                .andExpect(status().isNoContent());

        verify(breedService).deleteBreedById(1);
    }

    @Test
    void deleteAllBreeds_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/breeds"))
                .andExpect(status().isNoContent());

        verify(breedService).deleteAllBreeds();
    }
}
