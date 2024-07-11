package ru.dZibert.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dZibert.tgBot.entity.OrderProduct;
import ru.dZibert.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("SELECT product FROM OrderProduct WHERE clientOrder.client.id = :id")
    List<Product> getClientProducts(Long id);

    @Query("SELECT product FROM OrderProduct GROUP BY (product) ORDER BY SUM(countProduct) DESC LIMIT :limit")
    List<Product> getTopPopularProducts(Integer limit);
}
