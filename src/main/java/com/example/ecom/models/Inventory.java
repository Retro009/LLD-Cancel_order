package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class Inventory extends BaseModel{
    @OneToOne
    private Product product;
    private int quantity;
}
