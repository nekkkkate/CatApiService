package project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.controller.controllers.ColorController;
import project.controller.dto.ColorDto;
import project.model.entities.collections.Colors;
import project.model.services.ColorServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ColorController.class)
public class ColorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColorServiceImpl colorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllColors_ReturnsList() throws Exception {
        List<ColorDto> colors = List.of(
                new ColorDto(1, Colors.WHITE),
                new ColorDto(2, Colors.BLACK)
        );

        Mockito.when(colorService.getAllColors()).thenReturn(colors);

        mockMvc.perform(get("/api/colors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(colors.size()))
                .andExpect(jsonPath("$[0].type").value("WHITE"));
    }

    @Test
    void getColorById_ReturnsColor() throws Exception {
        ColorDto dto = new ColorDto(1, Colors.GRAY);
        Mockito.when(colorService.getColorById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/colors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("GRAY"));
    }

    @Test
    void createColor_ReturnsCreated() throws Exception {
        ColorDto dto = new ColorDto(1, Colors.ORANGE);
        Mockito.when(colorService.createColor("ORANGE")).thenReturn(dto);

        mockMvc.perform(post("/api/colors")
                        .content("ORANGE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("ORANGE"));
    }

    @Test
    void updateColor_WhenIdMatches_ReturnsOk() throws Exception {
        ColorDto dto = new ColorDto(1, Colors.BROWN);
        Mockito.when(colorService.updateColor(any())).thenReturn(true);

        mockMvc.perform(put("/api/colors/1")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("BROWN"));
    }

    @Test
    void updateColor_WhenIdMismatch_Returns404() throws Exception {
        ColorDto dto = new ColorDto(2, Colors.BROWN);

        mockMvc.perform(put("/api/colors/1")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateColor_WhenNotFound_Returns404() throws Exception {
        ColorDto dto = new ColorDto(1, Colors.BROWN);
        Mockito.when(colorService.updateColor(any())).thenReturn(false);

        mockMvc.perform(put("/api/colors/1")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteColorById_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/colors/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(colorService).deleteColorById(1);
    }

    @Test
    void deleteAllColors_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/colors"))
                .andExpect(status().isNoContent());

        Mockito.verify(colorService).deleteAllColors();
    }
}