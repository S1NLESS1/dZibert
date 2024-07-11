package ru.dZibert.tgBot.repository;

import org.aspectj.weaver.ast.Or;
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

    @Query("UPDATE OrderProduct SET product = :product WHERE clientOrder.client.id = :clientId")
    void updateOrderProduct(Long clientId, Product product);

    @Query("FROM OrderProduct WHERE clientOrder.client.id = :clientId AND countProduct = 0")
    List <OrderProduct> getOrderProductWithoutCountByClientId(Long clientId);

    @Query("FROM OrderProduct ORDER BY id DESC LIMIT 1")
    OrderProduct getLastOrderProductByClientId(Long clientId);

    @Query("FROM OrderProduct WHERE clientOrder.client.id = :clientId AND clientOrder.status = 1")
    List<OrderProduct> getOpenOrdersProductByClientId(Long clientId);
}
