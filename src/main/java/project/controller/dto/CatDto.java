package project.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import project.model.entities.Breed;
import project.model.entities.Cat;
import project.model.entities.Color;
import project.model.entities.Owner;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@Value
public class CatDto implements Serializable {
    Integer id;
    @NotNull(message = "name can't be null ")
    @NotEmpty(message = "name can't be empty")
    String name;
    @NotNull(message = "cat has to have birthday!!! you're devil")
    LocalDate birthDate;
    Owner owner;
    Breed breed;
    Color color;
    List<Cat> catsFriends;
}