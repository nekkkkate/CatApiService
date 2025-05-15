package project.controller.dto;

import lombok.*;
import project.model.entities.collections.Colors;

@Data
@Builder
@AllArgsConstructor
@Value
public class ColorDto {
    Integer id;
    Colors type;
}