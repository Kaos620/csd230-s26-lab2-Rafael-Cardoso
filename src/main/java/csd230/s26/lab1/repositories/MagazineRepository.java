package csd230.s26.lab1.repositories;

import csd230.s26.lab1.entities.BookEntity;
import csd230.s26.lab1.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<MagazineEntity, Long> {
    List<BookEntity> findByCurrentIssue(String currentIssue);
    List<BookEntity> findByTitleContaining(String title);
}