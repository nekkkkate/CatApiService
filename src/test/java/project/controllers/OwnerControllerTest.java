package project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.controller.controllers.OwnerController;
import project.controller.dto.OwnerDto;
import project.model.services.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerServiceImpl ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    private OwnerDto ownerDto;

    @BeforeEach
    void setUp() {
        ownerDto = new OwnerDto(1, "Tom", LocalDate.of(2000, 1, 1));
    }

    @Test
    void testGetAllOwners() throws Exception {
        when(ownerService.getAllOwners()).thenReturn(List.of(ownerDto));

        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"));
    }

    @Test
    void testGetOwnerById() throws Exception {
        when(ownerService.getOwnerById(1)).thenReturn(ownerDto);

        mockMvc.perform(get("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"));
    }

    @Test
    void testCreateOwner() throws Exception {
        when(ownerService.createOwner(any())).thenReturn(ownerDto);

        mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"));
    }

    @Test
    void testUpdateOwner_Success() throws Exception {
        when(ownerService.updateOwner(any())).thenReturn(true);

        mockMvc.perform(put("/api/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"));
    }

    @Test
    void testUpdateOwner_IdMismatch() throws Exception {
        OwnerDto otherId = new OwnerDto(2, "Tom", LocalDate.of(2000, 1, 1));

        mockMvc.perform(put("/api/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(otherId)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateOwner_NotFound() throws Exception {
        when(ownerService.updateOwner(any())).thenReturn(false);

        mockMvc.perform(put("/api/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOwner() throws Exception {
        mockMvc.perform(delete("/api/owners/1"))
                .andExpect(status().isNoContent());

        verify(ownerService, times(1)).deleteOwnerById(1);
    }

    @Test
    void testDeleteAllOwners() throws Exception {
        mockMvc.perform(delete("/api/owners"))
                .andExpect(status().isNoContent());

        verify(ownerService, times(1)).deleteAllOwners();
    }
}