package ru.dZibert.tgBot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dZibert.tgBot.Service.ClientService;
import ru.dZibert.tgBot.entity.Client;
import ru.dZibert.tgBot.entity.ClientOrder;
import ru.dZibert.tgBot.entity.Product;

import java.util.List;

@RestController
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
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

}
