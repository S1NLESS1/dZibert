package ru.dZibert.tgBot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dZibert.tgBot.Service.ProductService;
import ru.dZibert.tgBot.entity.Category;
import ru.dZibert.tgBot.entity.OrderProduct;
import ru.dZibert.tgBot.entity.Product;
import ru.dZibert.tgBot.repository.OrderProductRepository;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductService productService;
    private final OrderProductRepository orderProductRepository;

    public ProductRestController(ProductService productService, OrderProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.productService = productService;
        this.orderProductRepository = orderProductRepository;
    }

    //Получить список всех товаров определенной категории
    @GetMapping(value ="/rest/products/search", params = "categoryId")
    List<Product> getProductsByCategoryId(@RequestParam Long categoryId){
        return productService.getProductsByCategoryId(categoryId);
    }

    //Получить {limit} самых популярных товаров среди клиентов
    @GetMapping(path = "/rest/products/popular")
    List<Product> getTopPopularProducts(Integer limit){
        return productService.getTopPopularProducts(limit);
    }

    //Получить список товаров по частичному совпадению названия.
    @GetMapping(value = "/rest/products/search", params = "name")
    List<Product> searchProductsByName(@RequestParam (value = "name") String name){
        return productService.searchProductsByName(name);
    }

    @GetMapping(path = "/rest/categories")
    public List<Category> getCategoriesByParentId(@RequestParam Long parentId){
        return productService.getCategoriesByParentId(parentId);
    }

    @GetMapping(path = "/rest/products/orders")
    List<OrderProduct> getOrderProductWithoutCountByClientId(@RequestParam Long clientId) {
        return productService.getOrderProductWithoutCountByClientId(clientId);
    }

    @PutMapping(path = "/rest/products/orders")
    public OrderProduct updateOrderProduct(@RequestParam Long clientId, @RequestParam Product product) {
        return productService.updateOrderProduct(clientId,product, 0);
    }



}
