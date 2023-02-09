package com.sofka.carrito.usecases;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.repositories.PurchaseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
public class AddBikeUseCase {
    @Autowired
    private PurchaseRepository purchaseRepository;

 /*   public Mono<Purchase> addPurchase(){

    }*/
}
