package com.javarush.javarushfinal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "part", schema = "auto_parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String auto;
    private String description;
    private Double price;
    private Integer quantity;
    private Long showCount;
    private String i18nKey;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public Long getId() {
        return id;
    }

    public Part setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Part setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuto() {
        return auto;
    }

    public Part setAuto(String auto) {
        this.auto = auto;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Part setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Part setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Part setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getShowCount() {
        return showCount;
    }

    public Part setShowCount(Long showCount) {
        this.showCount = showCount;
        return this;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public Part setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public Part setImage(byte[] image) {
        this.image = image;
        return this;
    }
}
