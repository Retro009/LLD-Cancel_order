package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class OrderDetail extends BaseModel{

    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
}
