package project.model.services;

import lombok.*;
import project.controller.dto.BreedDto;
import project.model.entities.Breed;
import project.model.entities.collections.Breeds;
import project.model.repositories.interfaces.BreedRepository;
import org.springframework.stereotype.Service;
import project.model.services.interfaces.BreedService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;

    public BreedDto createBreed(String breedType) {
        try {
            Breeds breedEnum = Breeds.valueOf(breedType.toUpperCase());
            Breed breed = new Breed();
            breed.setType(breedEnum);
            return toDto(breedRepository.save(breed));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Incorrect type: " + breedType);
        }
    }

    public BreedDto getBreedById(Integer id) {
        return toDto(breedRepository.getById(id));
    }

    public List<BreedDto> getAllBreeds() {
        return breedRepository.findAll().stream().map(this::toDto).toList();
    }

    public boolean updateBreed(BreedDto update) {
        Integer id = update.getId();
        if (!breedRepository.existsById(id)) {
            return false;
        }
        breedRepository.save(toEntity(update));
        return true;
    }

    public boolean deleteBreedById(Integer id) {
        if (breedRepository.existsById(id)) {
            breedRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllBreeds() {
        breedRepository.deleteAll();
    }

    public BreedDto toDto(Breed breed) {
        return new BreedDto(breed.getId(), breed.getType());
    }

    public Breed toEntity(BreedDto breedDto) {
        Breed breed = new Breed();
        breed.setId(breedDto.getId());
        breed.setType(breedDto.getType());
        return breed;
    }
}