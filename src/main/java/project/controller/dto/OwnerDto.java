package project.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import project.model.entities.Cat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Value
public class OwnerDto implements Serializable {
    Integer id;
    @NotNull(message = "name can't be null ")
    @NotEmpty(message = "name can't be empty")
    String name;
    @NotNull(message = "if you haven't been born yet you can't use this program!!")
    LocalDate birthdate;
}