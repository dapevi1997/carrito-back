package com.sofka.carrito.usecases;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.PurchaseDTO;
import com.sofka.carrito.repositories.PurchaseRepository;
import com.sofka.carrito.utilities.MapperUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
public class AddPurchaseUseCase {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private MapperUtils mapperUtils;

    public Mono<Purchase> addPurchase(PurchaseDTO purchaseDTO){
        Purchase purchase = mapperUtils.mappertoPurchase().apply(purchaseDTO);
        return purchaseRepository.save(purchase);
    }
}
