package com.sofka.carrito.usecases;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.Products;
import com.sofka.carrito.models.PurchaseDTO;
import com.sofka.carrito.repositories.PurchaseRepository;
import com.sofka.carrito.utilities.MapperUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddPurchaseUseCaseTest {
    @SpyBean
    private AddPurchaseUseCase addPurchaseUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    private PurchaseRepository purchaseRepository;


    @Test
    void addPurchase() {

        Products product = new Products("idProduct",2);
        List<Products> productsList = new ArrayList<>();
        productsList.add(product);

        PurchaseDTO purchaseDTO = new PurchaseDTO("CC","1103119753","Daniel", productsList);
        Purchase purchase = mapperUtils.mappertoPurchase().apply(purchaseDTO);

        PurchaseDTO purchaseDTOMock = Mockito.mock(PurchaseDTO.class);
        Mockito.when(purchaseRepository.save(mapperUtils.mappertoPurchase().apply(purchaseDTOMock))).thenReturn(Mono.just(purchase));

        StepVerifier.create(addPurchaseUseCase.addPurchase(purchaseDTO))
                .equals(purchase);
    }
}