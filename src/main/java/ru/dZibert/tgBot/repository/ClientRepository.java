package ru.dZibert.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dZibert.tgBot.entity.Client;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("FROM Client WHERE fullName ILIKE %:name%")
    List<Client> searchClientsByName(String name);

    void deleteClientsByExternalId(Long externalId);

    Client findFirstClientByExternalId(Long externalId);

    Client getClientByExternalId(Long userId);

    Client getClientById(Long clientId);
}
