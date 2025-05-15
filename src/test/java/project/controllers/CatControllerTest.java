package project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.controller.controllers.CatController;
import project.controller.dto.CatDto;
import project.model.entities.collections.Colors;
import project.model.services.CatServiceImpl;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(CatController.class)
public class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatServiceImpl catService;

    @Autowired
    private ObjectMapper objectMapper;

    private CatDto cat1;
    private CatDto cat2;

    @BeforeEach
    void setUp() {
        cat1 = new CatDto(1, "Барсик", LocalDate.of(2020, 5, 1), null, null, null, null);
        cat2 = new CatDto(2, "Мурка", LocalDate.of(2021, 8, 10), null, null, null, null);
    }

    @Test
    void getAllCats_shouldReturnListOfCats() throws Exception {
        when(catService.getAllCats()).thenReturn(List.of(cat1, cat2));

        mockMvc.perform(get("/api/cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getCatById_shouldReturnCat() throws Exception {
        when(catService.getCatById(1)).thenReturn(cat1);

        mockMvc.perform(get("/api/cats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Барсик"));
    }

    @Test
    void createCat_shouldReturnCreatedCat() throws Exception {
        when(catService.createCat(any(CatDto.class))).thenReturn(cat1);

        mockMvc.perform(post("/api/cats")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Барсик"));
    }

    @Test
    void updateCat_shouldReturnUpdatedCat_whenIdsMatch() throws Exception {
        when(catService.updateCat(cat1)).thenReturn(true);

        mockMvc.perform(put("/api/cats/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Барсик"));
    }

    @Test
    void updateCat_shouldReturnNotFound_whenIdsDoNotMatch() throws Exception {
        CatDto invalidCat = new CatDto(99, "Барсик", LocalDate.of(2020, 5, 1), null, null, null, null);

        mockMvc.perform(put("/api/cats/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCat)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAllCats_shouldReturnNoContent() throws Exception {
        doNothing().when(catService).deleteAllCats();

        mockMvc.perform(delete("/api/cats"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCatsByColorWithPagination_shouldReturnCats() throws Exception {
        when(catService.getCatsByColorWithPagination(Colors.BLACK, 0, 10)).thenReturn(List.of(cat1));

        mockMvc.perform(get("/api/cats/search")
                        .param("color", "black")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getCatsByOwnerWithFilterAndPagination_shouldReturnCats() throws Exception {
        when(catService.getCatsByOwnerWithFilterAndPagination(1, null, 0, 10))
                .thenReturn(List.of(cat1, cat2));

        mockMvc.perform(get("/api/cats/owner/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getCatsByOwnerWithFilterAndPagination_withNameFilter_shouldReturnFilteredCats() throws Exception {
        when(catService.getCatsByOwnerWithFilterAndPagination(1, "Барсик", 0, 10))
                .thenReturn(List.of(cat1));

        mockMvc.perform(get("/api/cats/owner/1")
                        .param("name", "Барсик")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
