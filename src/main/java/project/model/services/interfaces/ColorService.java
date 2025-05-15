package project.model.services.interfaces;

import project.controller.dto.ColorDto;
import project.model.entities.Color;

import java.util.List;

public interface ColorService {
    ColorDto createColor(String colorType);

    ColorDto getColorById(Integer id);

    List<ColorDto> getAllColors();

    boolean updateColor(ColorDto update);

    boolean deleteColorById(Integer id);

    void deleteAllColors();

    ColorDto toDto(Color color);

    Color toEntity(ColorDto colorDto);
}