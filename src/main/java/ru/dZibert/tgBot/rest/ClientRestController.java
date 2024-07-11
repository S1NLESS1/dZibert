package ru.dZibert.tgBot.rest;

import org.springframework.web.bind.annotation.*;
import ru.dZibert.tgBot.Service.ClientService;
import ru.dZibert.tgBot.entity.Client;
import ru.dZibert.tgBot.entity.ClientOrder;
import ru.dZibert.tgBot.entity.Product;
import ru.dZibert.tgBot.repository.ClientOrderRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService, ClientOrderRepository clientOrderRepository) {
        this.clientService = clientService;
    }

    //Получить список всех заказов клиента
    @GetMapping(path = "/rest/clients/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id){
        return clientService.getClientOrders(id);
    }

    //Получить список всех товаров во всех заказах клиента по идентификатору клиента
    @GetMapping(path = "/rest/clients/{id}/products")
    public List<Product> getClientProducts(@PathVariable Long id){
        return clientService.getClientProducts(id);
    }

    //Получить список клиентов по частичному совпадению имени.
    @GetMapping(path = "/rest/clients/search")
    List<Client> searchClientsByName(@RequestParam String name){
        return clientService.searchClientsByName(name);
    }

    @PutMapping(path = "/rest/clients/orders")
    public ClientOrder updateClientOrder( @RequestParam Long clientId,@RequestParam Double total){
        return clientService.updateClientOrder(clientId,total);
    }

}
