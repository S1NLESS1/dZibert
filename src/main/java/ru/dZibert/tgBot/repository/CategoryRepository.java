package ru.dZibert.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dZibert.tgBot.entity.Category;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("FROM Category WHERE parent.id = :parentId")
    List<Category> getCategoriesByParentId(Long parentId);

    @Query("FROM Category WHERE parent.id is NULL")
    List<Category> getMainCategories();
}
