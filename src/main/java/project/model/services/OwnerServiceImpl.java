package project.model.services;

import project.controller.dto.OwnerDto;
import project.model.entities.Cat;
import project.model.entities.Owner;
import project.model.repositories.interfaces.CatRepository;
import project.model.repositories.interfaces.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.services.interfaces.OwnerService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final CatRepository catRepository;

    public OwnerDto createOwner(OwnerDto ownerDto) {
        Owner owner = ownerRepository.save(toEntity(ownerDto));
        return toDto(owner);
    }

    public OwnerDto getOwnerById(Integer id) {
        return toDto(ownerRepository.getById(id));
    }

    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream().map(this::toDto).toList();
    }

    public boolean updateOwner(OwnerDto owner) {
        if (!ownerRepository.existsById(owner.getId())) {
            return false;
        }
        ownerRepository.save(toEntity(owner));
        return true;
    }

    public boolean deleteOwnerById(Integer id) {
        if (!ownerRepository.existsById(id)) {
            return false;
        }
        List<Cat> cats = catRepository.findAll().stream().toList();
        for (Cat cat : cats) {
            if (cat.getOwner() != null && cat.getOwner().getId().equals(id)) {
                cat.setOwner(null);
            }
        }

        ownerRepository.deleteById(id);
        return true;
    }

    public void deleteAllOwners() {
        List<Cat> cats = catRepository.findAll().stream().toList();
        for (Cat cat : cats) {
            cat.setOwner(null);
        }
        ownerRepository.deleteAll();
    }

    public boolean addCat(Owner owner, Cat cat) {
        if (ownerRepository.existsById(owner.getId()) && catRepository.existsById(cat.getId())) {
            owner.addCat(cat);
            cat.setOwner(owner);
            ownerRepository.save(owner);
            catRepository.save(cat);
            return true;
        }
        return false;
    }

    public boolean removeCat(Owner owner, Cat cat) {
        if (ownerRepository.existsById(owner.getId()) && catRepository.existsById(cat.getId())) {
            owner.removeCat(cat);
            cat.setOwner(null);
            ownerRepository.save(owner);
            catRepository.save(cat);
            return true;
        }
        return false;
    }

    public Owner toEntity(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setBirthdate(ownerDto.getBirthdate());
        owner.setName(ownerDto.getName());
        return owner;
    }

    public OwnerDto toDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getBirthdate());
    }
}