package ru.dZibert.tgBot.Service;
import java.util.ArrayList;
import java.util.Objects;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.dZibert.tgBot.entity.*;
import ru.dZibert.tgBot.repository.ClientOrderRepository;
import ru.dZibert.tgBot.repository.ClientRepository;
import ru.dZibert.tgBot.repository.OrderProductRepository;

import java.util.List;

@Service
public class TelegramBotConnection {

    private final ProductService productService;
    private final ClientService clientService;

    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final OrderProductRepository orderProductRepository;

    private TelegramBot bot;

    public TelegramBotConnection(ProductService productService, ClientService clientService, ClientRepository clientRepository, ClientOrderRepository clientOrderRepository, OrderProductRepository orderProductRepository) {
        this.productService = productService;
        this.clientService = clientService;

        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @PostConstruct
    public void createConnection() {
        bot = new TelegramBot("6731589472:AAHu2DX3MNzb3w8ff5Wq6rT6coP8ERbfyuw");
        bot.setUpdatesListener(new TelegramUpdatesListener());
    }

    private class TelegramUpdatesListener implements UpdatesListener {
        @Override
        public int process(List<Update> updates) {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

        private void processUpdate(Update update) {
            // логика по работе с callback
            if (update.callbackQuery() != null) {

                createClient(update.callbackQuery().from().id(),update.callbackQuery().from().firstName()+" "+update.callbackQuery().from().lastName(),"+79780632047","г. Севастополь, ул. Челнокова, 12/3, кв. 66");
                Long userId = update.callbackQuery().from().id();
                Long clientId = clientService.findClientByExternalId(userId).getId();
                if(productService.getOrderProductWithoutCountByClientId(clientId).size() > 0){ // есть товар без указанного количества
                    bot.execute(new SendMessage(userId,
                            "Выберите количество товара."));
                    return;
                }
                Product product = productService.getProductById(Long.parseLong(update.callbackQuery().data()));
                ClientOrder clientOrder = clientService.findLastByClientId(clientId);
                OrderProduct orderProduct = createOrderProduct(clientOrder,product,0);
                orderProductRepository.save(orderProduct);
                bot.execute(new SendMessage(userId,
                         product.getName() + "\nЦена: " + product.getPrice()+"\nКакое количество хотите заказать?"));
                return ;
            } else if(update.message() != null){
                // логика по работе с сообщениями
                // создаём клиента
                createClient(update.message().from().id(),update.message().from().firstName()+" "+update.message().from().lastName(),"+79780632047","г. Севастополь, ул. Челнокова, 12/3, кв. 66");

                List<KeyboardButton> categories = null;
                ReplyKeyboardMarkup markup = null;
                InlineKeyboardMarkup keyboard_products = null;
                Long userId = update.message().from().id();
                Long clientId = clientService.findClientByExternalId(userId).getId();
                if(productService.getOrderProductWithoutCountByClientId(clientId).size() > 0){ // есть товар без указанного количества
                    if(isNumeric(update.message().text())) {
                        productService.updateOrderProduct(clientId, null, Integer.parseInt(update.message().text()));
                        OrderProduct orderProduct = productService.getLastOrderProductByClientId(clientId);
                        bot.execute(new SendMessage(userId,
                                "Товар добавлен \nНазвание: " + orderProduct.getProduct().getName()+"\nКоличество: "+orderProduct.getCountProduct()));
                    }
                    else {
                        bot.execute(new SendMessage(userId,
                                "Выберите количество товара."));
                        return;
                    }
                }
                boolean main_flag = false; // для вывода "В основное меню"

                switch (update.message().text()) {
                    case "Пицца" ->{
                        keyboard_products = getProductButtons(1L);
                    }
                    case "Роллы" ->{
                        categories = getCategoriesButtons(2L);
                    }
                    case "Бургеры" ->{
                        categories = getCategoriesButtons(3L);
                    }
                    case "Напитки" ->{
                        categories = getCategoriesButtons(4L);
                    }
                    case "Классические роллы" ->{
                        keyboard_products = getProductButtons(5L);
                    }
                    case "Запеченные роллы" ->{
                        keyboard_products = getProductButtons(6L);
                    }
                    case "Сладкие роллы" ->{
                        keyboard_products =  getProductButtons(7L);
                    }
                    case "Наборы" ->{
                        keyboard_products = getProductButtons(8L);
                    }
                    case "Классические бургеры" ->{
                        keyboard_products = getProductButtons(9L);
                    }
                    case "Острые бургеры" ->{
                        keyboard_products = getProductButtons(10L);
                    }
                    case "Газированные напитки" ->{
                        keyboard_products = getProductButtons(11L);
                    }
                    case "Энергетические напитки" ->{
                        keyboard_products = getProductButtons(12L);
                    }
                    case "Соки" ->{
                        keyboard_products = getProductButtons(13L);
                    }
                    case "Другие" ->{
                        keyboard_products = getProductButtons(14L);
                    }
                    case "Оформить заказ"->{
                        List <OrderProduct> list_of_orders_product = productService.getOpenOrdersProductByClientId(clientId);
                        if(list_of_orders_product.size() > 0){
                            String message_about_products = "Ваш заказ:\n";
                            Double total = 0.0;
                            for (OrderProduct orderProduct : list_of_orders_product) {
                                message_about_products += orderProduct.getProduct().getName() +
                                        ": " + orderProduct.getCountProduct() +
                                        "x" + orderProduct.getProduct().getPrice() +
                                        " = "+ orderProduct.getCountProduct() * orderProduct.getProduct().getPrice() + "\n";
                                total += orderProduct.getCountProduct() * orderProduct.getProduct().getPrice();
                            }
                            message_about_products += "Итого: "+ total;
                            markup = new ReplyKeyboardMarkup(new KeyboardButton("Ок. Ждём доставки!"));
                            bot.execute(new SendMessage(userId,message_about_products).replyMarkup(markup));
                            clientService.updateClientOrder(clientId,total);
                            createClientOrder(clientService.getClientById(clientId), 1, 0.0);
                        }
                        else {
                            markup = new ReplyKeyboardMarkup(new KeyboardButton("Понятно"));
                            bot.execute(new SendMessage(userId,"Заказ пуст").replyMarkup(markup));
                        }
                    }
                    default -> {
                        categories = productService.getMainCategories()
                                .stream()
                                .map(category -> new KeyboardButton(category.getName()))
                                .toList();
                        main_flag = true;
                    }
                }

                if(categories != null) {
                    markup = new
                            ReplyKeyboardMarkup(categories.toArray(KeyboardButton[]::new));
                    markup.resizeKeyboard(true);
                    markup.addRow(new KeyboardButton("Оформить заказ"));
                    if(!main_flag) markup.addRow(new KeyboardButton("В основное меню"));
                    bot.execute(new SendMessage(update.message().chat().id(), "Категории").replyMarkup(markup));
                }
                else if(keyboard_products != null){
                    bot.execute(new SendMessage(userId, "Товары").replyMarkup(keyboard_products));
                }
            }
        }
    }

    private List<KeyboardButton> getCategoriesButtons(Long parentId) {
        return productService.getCategoriesByParentId(parentId)
                .stream()
                .map(category -> new KeyboardButton(category.getName()))
                .toList();
    }

    private InlineKeyboardMarkup getProductButtons(Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for(Product product: products){
            InlineKeyboardButton button = new
                    InlineKeyboardButton(String.format("Товар %s. Цена %.2f руб.",
                    product.getName(), product.getPrice()))
                    .callbackData(product.getId().toString());
            markup.addRow(button);
        }
        return markup;
    }

    private void createClient(Long externalId, String fullName, String phoneNumber, String address) {
        if(clientRepository.findFirstClientByExternalId(externalId) == null) {
            Client client = new Client();
            client.setExternalId(externalId);
            client.setFullName(fullName);
            client.setPhoneNumber(phoneNumber);
            client.setAddress(address);
            clientRepository.save(client);

            ClientOrder clientOrder = createClientOrder(client, 1,0.0);
            clientOrderRepository.save(clientOrder);
        }
    }

    ClientOrder createClientOrder(Client client, Integer status, Double total) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrder.setStatus(status);
        clientOrder.setTotal(total);
        return clientOrderRepository.save(clientOrder);
    }

    private OrderProduct createOrderProduct(ClientOrder clientOrder, Product product, Integer countProduct) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setClientOrder(clientOrder);
        orderProduct.setProduct(product);
        orderProduct.setCountProduct(countProduct);
        return orderProduct;
    }

    boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
