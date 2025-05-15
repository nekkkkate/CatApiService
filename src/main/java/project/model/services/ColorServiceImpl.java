package project.model.services;

import project.controller.dto.ColorDto;
import project.model.entities.Color;
import project.model.entities.collections.Colors;
import project.model.repositories.interfaces.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.services.interfaces.ColorService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    public ColorDto createColor(String colorType) {
        try {
            Colors colorEnum = Colors.valueOf(colorType.toUpperCase());
            Color color = new Color();
            color.setType(colorEnum);
            return toDto(colorRepository.save(color));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Incorrect type: " + colorType);
        }
    }

    public ColorDto getColorById(Integer id) {
        return toDto(colorRepository.getById(id));
    }

    public List<ColorDto> getAllColors() {
        return colorRepository.findAll().stream().map(this::toDto).toList();
    }

    public boolean updateColor(ColorDto update) {
        Integer id = update.getId();
        if (!colorRepository.existsById(id)) {
            return false;
        }
        colorRepository.save(toEntity(update));
        return true;
    }

    public boolean deleteColorById(Integer id) {
        if (colorRepository.existsById(id)) {
            colorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllColors() {
        colorRepository.deleteAll();
    }

    public ColorDto toDto(Color color) {
        return new ColorDto(color.getId(), color.getType());
    }

    public Color toEntity(ColorDto colorDto) {
        Color color = new Color();
        color.setId(colorDto.getId());
        color.setType(colorDto.getType());
        return color;
    }
}