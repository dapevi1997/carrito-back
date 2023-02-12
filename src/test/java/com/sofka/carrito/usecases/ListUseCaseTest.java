package com.sofka.carrito.usecases;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.Products;
import com.sofka.carrito.repositories.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListUseCaseTest {
    @SpyBean
    private ListUseCase listUseCase;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Test
    void listAllPurchases() {
        Products product = new Products("idProduct",2);
        List<Products> productsList = new ArrayList<>();
        productsList.add(product);

        Purchase purchase = new Purchase("idPurchase", LocalDateTime.now(),"CC","1103119753","Daniel",productsList);

        Mockito.when(purchaseRepository.findAll()).thenReturn(Flux.just(purchase));

        StepVerifier.create(listUseCase.listAllPurchases())
                .expectNextMatches(purchase1 -> {
                    assert purchase1.getId().equals(purchase.getId());
                    return true;
                });
    }
}