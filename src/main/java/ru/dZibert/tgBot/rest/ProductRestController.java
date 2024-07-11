package ru.dZibert.tgBot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dZibert.tgBot.Service.ProductService;
import ru.dZibert.tgBot.entity.Product;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
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
}
