package ru.dZibert.tgBot;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dZibert.tgBot.entity.*;
import ru.dZibert.tgBot.repository.*;

import java.math.BigDecimal;

@SpringBootTest
public class FillingTests {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    ClientOrderRepository clientOrderRepository;

    @Test
    void createCategoriesAndProducts() {
        Client client1 = createClient(111111L,"Логинов Борис","+79782014211","г. Севастополь");
        Client client2 = createClient(222222L,"Коваленко Иван","+79781112211","г. Севастополь");
        // Основная категория
        Category pizza_category = createCategory("Пицца",null);
        Category rolls_category = createCategory("Роллы",null);
        Category burgers_category = createCategory("Бургеры",null);
        Category drinks_category = createCategory("Напитки",null);

        // Роллы
        Category classic_rolls = createCategory("Классические роллы",rolls_category);
        Category baked_rolls = createCategory("Запеченные роллы",rolls_category);
        Category sweet_rolls = createCategory("Сладкие роллы",rolls_category);
        Category sets_rolls = createCategory("Наборы",rolls_category);

        // Бургеры
        Category classic_burgers = createCategory("Классические бургеры",burgers_category);
        Category spicy_burgers = createCategory("Острые бургеры",burgers_category);

        // Напитки
        Category soda_drinks = createCategory("Газированные напитки",drinks_category);
        Category energy_drinks = createCategory("Энергетические напитки",drinks_category);
        Category juice_drinks = createCategory("Соки",drinks_category);
        Category other_drinks = createCategory("Другие",drinks_category);

        // Создаём товары

        // Пицца
        Product product_pizza_1 = createProduct(pizza_category,"Жульен","Пицца 30 см","350.0");
        Product product_pizza_2 = createProduct(pizza_category,"Пепперони","Пицца 25 см", "450.0");
        Product product_pizza_3 = createProduct(pizza_category,"Гавайская","Пицца 30 см", "450.0");

        // Роллы
        Product product_roll_1 = createProduct(classic_rolls,"Филадельфия","Роллы 8 шт", "300.0");
        Product product_roll_2 = createProduct(classic_rolls,"Юми","Роллы 8 шт", "320.0");
        Product product_roll_3 = createProduct(classic_rolls,"Вегетарианский","Роллы 8 шт", "250.0");

        Product product_roll_4 = createProduct(baked_rolls,"Эби","Роллы 8 шт", "500.0");
        Product product_roll_5 = createProduct(baked_rolls,"Терияки фиш","Роллы 8 шт", "500.0");
        Product product_roll_6 = createProduct(baked_rolls,"С угрем","Роллы 8 шт", "500.0");

       Product product_roll_7 = createProduct(sweet_rolls,"Калифорния","Роллы 8 шт", "320.0");
        Product product_roll_8 = createProduct(sweet_rolls,"Аляска","Роллы 8 шт", "320.0");
        Product product_roll_9 = createProduct(sweet_rolls,"Сэнсей","Роллы 8 шт", "320.0");

        Product product_roll_10 = createProduct(sets_rolls,"Пикник","Роллы 799 г", "999.0");
        Product product_roll_11 = createProduct(sets_rolls,"Жара","Роллы 907 г", "1099.0");
        Product product_roll_12 = createProduct(sets_rolls,"Большой куш","Роллы 1747 г", "2199.0");

        // Бургеры
        Product product_burger_1 = createProduct(classic_burgers,"Чизбургер","Бургер  215 г","475.0");
        Product product_burger_2 = createProduct(classic_burgers,"Чикен бургер","Бургер  230 г", "335.0");
        Product product_burger_3 = createProduct(classic_burgers,"Beef бургер","Бургер  250 г", "499.0");

        Product product_burger_4 = createProduct(spicy_burgers,"Сэм","Бургер  215 г", "465.0");
        Product product_burger_5 = createProduct(spicy_burgers,"Острота","Бургер  225 г", "500.0");
        Product product_burger_6 = createProduct(spicy_burgers,"Салют","Бургер 230г", "480.0");

        // Напитки
        Product product_drinks_1 = createProduct(soda_drinks,"Кола","Напиток 600 мл", "180.0");
        Product product_drinks_2 = createProduct(soda_drinks,"Фанта","Напиток 600 мл", "180.0");
       Product product_drinks_3 = createProduct(soda_drinks,"Спрайт","Напиток 600 мл","180.0");

        Product product_drinks_4 = createProduct(energy_drinks,"Бёрн","Напиток ж/б 300 мл", "250.0");
        Product product_drinks_5 = createProduct(energy_drinks,"Редбул","Напиток ж/б 300 мл", "250.0");
        Product product_drinks_6 = createProduct(energy_drinks,"Флэш","Напиток ж/б 300 мл", "250.0");

        Product product_drinks_7 = createProduct(juice_drinks,"Сок яблочный","Напиток 1000 мл", "200.0");
        Product product_drinks_8 = createProduct(juice_drinks,"Сок ананасовый","Напиток 1000 300 мл", "200.0");
        Product product_drinks_9 = createProduct(juice_drinks,"Сок мультифрукт","Напиток 1000 300 мл", "200.0");

        Product product_drinks_10 = createProduct(other_drinks,"Вода","Напиток 500 мл", "50.0");
        Product product_drinks_11 = createProduct(other_drinks,"Вода газированная","Напиток 500 мл", "50.0");
        Product product_drinks_12 = createProduct(other_drinks,"Морс","Напиток 300 мл", "120.0");

        ClientOrder clientOrder1 = createClientOrder(client1,1,"1050.0");
        OrderProduct orderProduct1 = createOrderProduct(clientOrder1, product_burger_1,3);

        ClientOrder clientOrder2 = createClientOrder(client2,1,"4398.0");
        OrderProduct orderProduct2 = createOrderProduct(clientOrder2, product_roll_12,2);
    }

    ClientOrder createClientOrder(Client client, Integer status, String total) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrder.setStatus(status);
        BigDecimal totalPrice = new BigDecimal(total);
        clientOrder.setTotal(totalPrice);
        return clientOrderRepository.save(clientOrder);
    }

    private OrderProduct createOrderProduct(ClientOrder clientOrder, Product product, Integer countProduct) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setClientOrder(clientOrder);
        orderProduct.setProduct(product);
        orderProduct.setCountProduct(countProduct);
        return orderProductRepository.save(orderProduct);
    }

    private Client createClient(Long externalId, String fullName, String phoneNumber, String address) {
        Client client = new Client();
        client.setExternalId(externalId);
        client.setFullName(fullName);
        client.setPhoneNumber(phoneNumber);
        client.setAddress(address);
        return clientRepository.save(client);
    }

    private Product createProduct(Category category, String name, String description, String price) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        BigDecimal a = new BigDecimal(price);
        product.setPrice(a);
        return productRepository.save(product);
    }

    private Category createCategory(String name,Category parent) {
        Category category = new Category();
        category.setName(name);
        category.setParent(parent);
        return categoryRepository.save(category);
    }

}
