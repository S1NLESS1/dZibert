package ru.dZibert.tgBot.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.dZibert.tgBot.entity.Category;
import ru.dZibert.tgBot.entity.OrderProduct;
import ru.dZibert.tgBot.entity.Product;
import ru.dZibert.tgBot.repository.CategoryRepository;
import ru.dZibert.tgBot.repository.OrderProductRepository;
import ru.dZibert.tgBot.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, OrderProductRepository orderProductRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.categoryRepository = categoryRepository;
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

    public List<Category> getCategoriesByParentId(Long parentId){
        return categoryRepository.getCategoriesByParentId(parentId);
    }
    public List<Category> getMainCategories(){
        return categoryRepository.getMainCategories();
    }

    public Product getProductById(Long productId) {
        return productRepository.getProductById(productId);
    }


    public OrderProduct updateOrderProduct(Long clientId, Product product, Integer countProduct) {
        OrderProduct orderProduct = orderProductRepository.getLastOrderProductByClientId(clientId);
        if (orderProduct != null) {
            if(product != null) orderProduct.setProduct(product);
            else orderProduct.setCountProduct(countProduct);
            orderProductRepository.save(orderProduct);
            return orderProduct;
        }
        return null;
    }


    public List<OrderProduct> getOrderProductWithoutCountByClientId(Long clientId) {
        return orderProductRepository.getOrderProductWithoutCountByClientId(clientId);
    }


    public OrderProduct getLastOrderProductByClientId(Long clientId) {
        return orderProductRepository.getLastOrderProductByClientId(clientId);
    }

    public List<OrderProduct> getOpenOrdersProductByClientId(Long clientId) {
        return orderProductRepository.getOpenOrdersProductByClientId(clientId);
    }
}
