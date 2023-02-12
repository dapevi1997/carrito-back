package com.sofka.carrito.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {

    private String idType;

    private String idClient;

    private String nameClient;

    private List<Products> products;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseDTO that = (PurchaseDTO) o;
        return Objects.equals(idType, that.idType) && Objects.equals(idClient, that.idClient) && Objects.equals(nameClient, that.nameClient) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idType, idClient, nameClient, products);
    }
}
