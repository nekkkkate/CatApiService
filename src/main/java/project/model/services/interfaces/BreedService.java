package project.model.services.interfaces;

import project.controller.dto.BreedDto;
import project.model.entities.Breed;

import java.util.List;

public interface BreedService {
    BreedDto createBreed(String breedType);

    BreedDto getBreedById(Integer id);

    List<BreedDto> getAllBreeds();

    boolean updateBreed(BreedDto update);

    boolean deleteBreedById(Integer id);

    void deleteAllBreeds();

    BreedDto toDto(Breed breed);

    Breed toEntity(BreedDto breedDto);
}