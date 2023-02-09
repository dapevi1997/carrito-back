package com.sofka.carrito.models;

import lombok.Data;

import java.util.Objects;

@Data
public class Products {

    private String id;
    private Integer amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(id, products.id) && Objects.equals(amount, products.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "Products{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                '}';
    }
}
