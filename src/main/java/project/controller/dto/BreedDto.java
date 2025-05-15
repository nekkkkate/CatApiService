package project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import project.model.entities.collections.Breeds;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Value
public class BreedDto implements Serializable {
    Integer id;
    Breeds type;
}