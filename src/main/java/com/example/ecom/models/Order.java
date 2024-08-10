package com.example.ecom.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode
public class Order extends BaseModel{
    @ManyToOne
    private User user;
    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;
    @Enumerated
    private OrderStatus orderStatus;
}
