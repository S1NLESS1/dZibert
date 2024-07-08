package ru.dZibert.tgBot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigInteger;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id; // уникальный идентификатор

    @Column(nullable = false)
    private Long externalId; // внешний уникальный идентификатор (идентификатор из телеграмм)

    @Column(nullable = false, length = 255)
    private String fullName; // ФИО или форма обращения

    @Column(nullable = false, length = 15)
    private String phoneNumber; // номер телефона

    @Column(nullable = false, length = 400)
    private String address; // адрес

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
