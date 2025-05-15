package project.model.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import project.controller.dto.CatDto;
import project.model.entities.Cat;
import project.model.entities.Owner;
import project.model.entities.collections.Colors;
import project.model.repositories.interfaces.CatRepository;
import project.model.repositories.interfaces.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.services.interfaces.CatService;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;

    public CatDto createCat(CatDto cat) {
        Cat kitten = catRepository.save(toEntity(cat));
        return toDto(kitten);
    }

    public CatDto getCatById(Integer id) {
        return toDto(catRepository.getById(id));
    }

    public List<CatDto> getAllCats() {
        return catRepository.findAll().stream().map(this::toDto).toList();
    }

    public boolean updateCat(CatDto cat) {
        if (catRepository.existsById(cat.getId())) {
            catRepository.save(toEntity(cat));
            return true;
        }
        return false;
    }

    public boolean deleteCatById(Integer id) {
        if (catRepository.existsById(id)) {
            Cat cat = catRepository.getById(id);
            Owner owner = cat.getOwner();
            if (owner != null) {
                owner.removeCat(cat);
                ownerRepository.save(owner);
            }
            catRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllCats() {
        List<Cat> catList = catRepository.findAll();
        for (Cat cat : catList) {
            Owner owner = cat.getOwner();
            owner.removeCat(cat);
            ownerRepository.save(owner);
        }
        catRepository.deleteAll();
    }

    public boolean AddCatFriend(Cat cat, Cat catFriend) {
        if (catRepository.existsById(cat.getId()) && catRepository.existsById(catFriend.getId())) {
            cat.addFriend(catFriend);
            catFriend.addFriend(cat);
            return true;
        }
        return false;
    }

    public boolean DeleteCatFriend(Cat cat, Cat catFriend) {
        if (catRepository.existsById(cat.getId()) && catRepository.existsById(catFriend.getId())) {
            cat.removeFriend(catFriend);
            catFriend.removeFriend(cat);
            return true;
        }
        return false;
    }

    @Override
    public CatDto toDto(Cat cat) {
        return new CatDto(cat.getId(), cat.getName(), cat.getBirthDate(), cat.getOwner(), cat.getBreed(), cat.getColor(), cat.getCatsFriends());
    }

    @Override
    public Cat toEntity(CatDto catDto) {
        Cat cat = new Cat();
        cat.setId(catDto.getId());
        cat.setName(catDto.getName());
        cat.setColor(catDto.getColor());
        cat.setBreed(catDto.getBreed());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setOwner(catDto.getOwner());
        cat.setCatsFriends(catDto.getCatsFriends());
        return cat;
    }

    public List<CatDto> getCatsByColorWithPagination(Colors color, int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        List<Cat> cats = catRepository.findAllByColor(color, pageable).getContent();
        return cats.stream().map(this::toDto).toList();
    }

    public List<CatDto> getCatsByOwnerWithFilterAndPagination(Integer ownerId, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (name != null && !name.isEmpty()) {
            List<Cat> cats = catRepository.findByOwnerIdAndNameContainingIgnoreCase(ownerId, name, pageable).getContent();
            return cats.stream().map(this::toDto).toList();
        } else {
            List<Cat> cats = catRepository.findByOwnerId(ownerId, pageable).getContent();
            return cats.stream().map(this::toDto).toList();
        }
    }
}