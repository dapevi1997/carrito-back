package com.sofka.carrito.utilities;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.PurchaseDTO;

import java.util.function.Function;

public class MapperUtils {

    public Function<PurchaseDTO, Purchase> mappertoPurchase(){
        return purchaseDTO -> {
            Purchase purchase = new Purchase();
            purchase.setDateTime(purchaseDTO.getDateTime());
            purchase.setIdType(purchaseDTO.getIdType());
            purchase.setIdClient(purchaseDTO.getIdClient());
            purchase.setNameClient(purchaseDTO.getNameClient());
            purchase.setProducts(purchaseDTO.getProducts());
            return purchase;
        };
    }
}
