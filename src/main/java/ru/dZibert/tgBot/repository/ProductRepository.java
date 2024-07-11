package ru.dZibert.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dZibert.tgBot.entity.Category;
import ru.dZibert.tgBot.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("FROM Product WHERE category.id = :categoryId")
    List<Product> getProductsByCategoryId(Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("FROM Product WHERE name ILIKE %:name%")
    List<Product> getProductsByName(String name);

    List<Product> findByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String name);

    Product getProductById(Long productId);
}
