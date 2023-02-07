package com.sofka.carrito.repositories;

import com.sofka.carrito.collections.Purchase;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PurchaseRepository extends ReactiveCrudRepository<Purchase,String> {
}
