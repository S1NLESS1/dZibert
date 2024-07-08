package ru.dZibert.tgBot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id; //  уникальный идентификатор
    @ManyToOne
    private Category category; // категория товара

    @Column(nullable = false, length = 50, unique = true)
    private String name; // название

    @Column(nullable = false, length = 400)
    private String description; // описание

    @Column(nullable = false, length = 15,precision = 2)
    public BigDecimal price; // стоимость

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
