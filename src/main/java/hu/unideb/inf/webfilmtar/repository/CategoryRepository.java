package hu.unideb.inf.webfilmtar.repository;

import hu.unideb.inf.webfilmtar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}