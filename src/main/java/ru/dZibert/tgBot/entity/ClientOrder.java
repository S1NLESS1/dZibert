package ru.dZibert.tgBot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ClientOrder {
    @Id
    @GeneratedValue
    private Long id; //  уникальный идентификатор

    @ManyToOne
    private Client client; // клиент сделавший заказ

    @Column(nullable = false)
    private Integer status; // статус заказа

    @Column(nullable = false,precision = 15, scale = 2)
    private BigDecimal total; // сумма по заказу

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
