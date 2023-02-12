package com.sofka.carrito.routers;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.Bike;
import com.sofka.carrito.models.Products;
import com.sofka.carrito.models.PurchaseDTO;
import com.sofka.carrito.models.ValidateModel;
import com.sofka.carrito.usecases.AddPurchaseUseCase;
import com.sofka.carrito.usecases.ListUseCase;
import com.sofka.carrito.utilities.Validations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class PurchaseRouter {
    @Autowired
    Validations validations;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @RouterOperation(
            path = "/addPurchase",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = PurchaseRouter.class,
            beanMethod = "addPurchase",
            operation = @Operation(
                    operationId = "addPurchase",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Purchase.class
                                    ))
                            )
                    },
                    requestBody = @RequestBody(
                            content = @Content(schema = @Schema(
                                    implementation = PurchaseDTO.class
                            ))
                    )
            )
    )
    @Bean
    public RouterFunction<ServerResponse> addPurchase(AddPurchaseUseCase addPurchaseUseCase) {
        Function<PurchaseDTO, Mono<ServerResponse>> executor = purchaseDTO ->
        {
            ValidateModel validateModel = validations.validatePurchaseDTO(purchaseDTO);
            if (!validateModel.getIsValid()) {
                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(validateModel);
            }

            for (Products product : purchaseDTO.getProducts()) {
                String direction = "http://localhost:8080/getBike/" + product.getId();

                Mono<Bike> bike = webClientBuilder.build()
                        .get()
                        .uri(direction)
                        .retrieve()
                        .bodyToMono(Bike.class);
                bike.subscribe(bike1 -> {
                    String direction2 = "http://localhost:8080/updateBike";
                    Integer amount = product.getAmount();
                    bike1.setInInventory(bike1.getInInventory() - amount);




                 Mono<String> result =   webClientBuilder.build()
                            .put()
                            .uri(direction2)
                            .syncBody(bike1)
                            .retrieve()
                            .bodyToMono(String.class)
                            .onErrorResume(e -> {
                                if (e instanceof Exception) {
                                    log.warn("Failed to get myStuff, desired service not present");
                                } else {
                                    log.error("Failed to get myStuff");
                                }
                                return Mono.just("Encountered an exception" + bike1.toString() + e.toString());
                            });

                 result.subscribe(s -> log.info(s.toString()));





                });


            }


            return addPurchaseUseCase.addPurchase(purchaseDTO)
                    .flatMap(result -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(result));
        };


        return route(
                POST("/addPurchase").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(PurchaseDTO.class).flatMap(executor)
        );
    }



    @RouterOperation(
            path = "/getAllPurchases",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = PurchaseRouter.class,
            beanMethod = "listAllPurchases",
            operation = @Operation(
                    operationId = "listAllPurchases",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Purchase.class
                                    ))
                            )
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> listAllPurchases(ListUseCase listUseCase) {
        return route(
                GET("/getAllPurchases"), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.listAllPurchases(), Purchase.class))
        );
    }


}
