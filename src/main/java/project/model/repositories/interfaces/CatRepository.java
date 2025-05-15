package project.model.repositories.interfaces;

import org.springframework.data.domain.Page;
import project.model.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import project.model.entities.collections.Colors;
import org.springframework.data.domain.Pageable;


public interface CatRepository extends JpaRepository<Cat, Integer> {
    Page<Cat> findAllByColor(Colors colorEnum, Pageable pageable);

    Page<Cat> findByOwnerId(Integer ownerId, Pageable pageable);

    Page<Cat> findByOwnerIdAndNameContainingIgnoreCase(Integer ownerId, String name, Pageable pageable);
}