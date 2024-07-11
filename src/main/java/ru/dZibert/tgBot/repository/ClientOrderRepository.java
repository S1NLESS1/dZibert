package ru.dZibert.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dZibert.tgBot.entity.ClientOrder;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    @Query("FROM ClientOrder WHERE client.id = :id")
    List<ClientOrder> getClientOrders(Long id);

    @Query("UPDATE ClientOrder SET status = 2, total = :total WHERE client.id = :clientId AND status = 1")
    void updateClientOrder(Long clientId, BigDecimal total);

    ClientOrder getClientOrderByClientId(Long clientId);

    @Query("FROM ClientOrder ORDER BY id DESC LIMIT 1")
    ClientOrder findLastByClientId(Long userId);
}
