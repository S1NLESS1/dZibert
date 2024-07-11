package ru.dZibert.tgBot.Service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.dZibert.tgBot.entity.Client;
import ru.dZibert.tgBot.entity.ClientOrder;
import ru.dZibert.tgBot.entity.Product;
import ru.dZibert.tgBot.repository.ClientOrderRepository;
import ru.dZibert.tgBot.repository.ClientRepository;
import ru.dZibert.tgBot.repository.OrderProductRepository;

import java.util.List;
@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final OrderProductRepository orderProductRepository;
    private final ClientOrderRepository clientOrderRepository;

    public ClientService(ClientRepository clientRepository, OrderProductRepository orderProductRepository, ClientOrderRepository clientOrderRepository) {
        this.clientRepository = clientRepository;
        this.orderProductRepository = orderProductRepository;
        this.clientOrderRepository = clientOrderRepository;
    }

    /**
     * Получить список всех товаров во всех заказах клиента
     * @param id идентификатор клиента
     */
    public List<Product> getClientProducts(Long id){
        return orderProductRepository.getClientProducts(id);
    }

    /**
     * Получить список заказов клиента
     * @param id идентификатор клиента
     */
    public List<ClientOrder> getClientOrders(Long id){
        return clientOrderRepository.getClientOrders(id);
    }

    /**
     * Найти всех клиентов по подстроке имени
     * @param name подстрока имени клиента
     */
    public List<Client> searchClientsByName(String name) {
        return clientRepository.searchClientsByName(name);
    }
}
