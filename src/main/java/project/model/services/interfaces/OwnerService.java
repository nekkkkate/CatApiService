package project.model.services.interfaces;

import project.controller.dto.OwnerDto;
import project.model.entities.Cat;
import project.model.entities.Owner;

import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(OwnerDto owner);

    OwnerDto getOwnerById(Integer id);

    List<OwnerDto> getAllOwners();

    boolean updateOwner(OwnerDto owner);

    boolean deleteOwnerById(Integer id);

    void deleteAllOwners();

    boolean addCat(Owner owner, Cat cat);

    boolean removeCat(Owner owner, Cat cat);

    Owner toEntity(OwnerDto ownerDto);

    OwnerDto toDto(Owner owner);
}