package project.model.services.interfaces;

import project.controller.dto.CatDto;
import project.model.entities.Cat;
import project.model.entities.collections.Colors;

import java.util.List;

public interface CatService {
    CatDto createCat(CatDto cat);

    CatDto getCatById(Integer id);

    List<CatDto> getAllCats();

    boolean updateCat(CatDto cat);

    boolean deleteCatById(Integer id);

    void deleteAllCats();

    boolean AddCatFriend(Cat cat, Cat catFriend);

    boolean DeleteCatFriend(Cat cat, Cat catFriend);

    CatDto toDto(Cat cat);

    Cat toEntity(CatDto catDto);

    List<CatDto> getCatsByColorWithPagination(Colors color, int page, int size);

    List<CatDto> getCatsByOwnerWithFilterAndPagination(Integer ownerId, String name, int page, int size);
}