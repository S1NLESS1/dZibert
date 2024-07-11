package ru.dZibert.tgBot.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.dZibert.tgBot.entity.Product;
import ru.dZibert.tgBot.repository.OrderProductRepository;
import ru.dZibert.tgBot.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public ProductService(ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

  // Получить продукты по категории
    public List<Product> getProductsByCategoryId(Long categoryId){
        return productRepository.getProductsByCategoryId(categoryId);
    }

    /**
     * Получить указанное кол-во самых популярных (наибольшее
     * количество штук в заказах) товаров среди клиентов
     * @param limit максимальное кол-во товаров
     */
    public List<Product> getTopPopularProducts(Integer limit){
        return orderProductRepository.getTopPopularProducts(limit);
    }

    /**
     * Найти все продукты по подстроке названия
     * @param name подстрока названия продукта
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.getProductsByName(name);
    }
}
