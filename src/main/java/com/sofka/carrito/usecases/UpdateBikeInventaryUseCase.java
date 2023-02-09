package com.sofka.carrito.usecases;

import com.sofka.carrito.models.Bike;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Data
public class UpdateBikeInventaryUseCase {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<String> updateBikeInventary(Bike bike){

        String direction = "http://localhost:8080/updateBike";

         return webClientBuilder.build().put()
                .uri(direction)
                .syncBody(bike)
                .retrieve()
                .bodyToMono(String.class);


    }






}
