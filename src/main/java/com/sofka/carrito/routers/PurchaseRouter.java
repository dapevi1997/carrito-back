package com.sofka.carrito.routers;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.Bike;
import com.sofka.carrito.models.PurchaseDTO;
import com.sofka.carrito.models.ValidateModel;
import com.sofka.carrito.usecases.AddPurchaseUseCase;
import com.sofka.carrito.usecases.ListUseCase;
import com.sofka.carrito.usecases.UpdateBikeInventaryUseCase;
import com.sofka.carrito.utilities.Validations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PurchaseRouter {
    @Autowired
    Validations validations;
    @RouterOperation(
            path = "/addPurchase",
            produces ={
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
    public RouterFunction<ServerResponse> addPurchase(AddPurchaseUseCase addPurchaseUseCase){
        Function<PurchaseDTO, Mono<ServerResponse>> executor = purchaseDTO ->
        {
            ValidateModel validateModel = validations.validatePurchaseDTO(purchaseDTO);
            if (!validateModel.getIsValid()){
                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(validateModel);
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
            path = "/updateBikeInventary",
            produces ={
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.PUT,
            beanClass = PurchaseRouter.class,
            beanMethod = "updateBikeInventary",
            operation = @Operation(
                    operationId = "updateBikeInventary",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = String.class
                                    ))
                            )
                    },
                    requestBody = @RequestBody(
                            content = @Content(schema = @Schema(
                                    implementation = Bike.class
                            ))
                    )
            )
    )
    @Bean
    public RouterFunction<ServerResponse> updateBikeInventary(UpdateBikeInventaryUseCase updateBikeInventaryUseCase){
        Function<Bike, Mono<ServerResponse>> executor = bike -> {
            ValidateModel validateModel = validations.validateBike(bike);
            if(!validateModel.getIsValid()){
                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(validateModel);

            }
            return updateBikeInventaryUseCase.updateBikeInventary(bike)
                    .flatMap(result -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(result));
        };

        return route(
                PUT("/updateBikeInventary").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Bike.class).flatMap(executor)
        );

    }

    @RouterOperation(
            path = "/getAllPurchases",
            produces ={
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
    public RouterFunction<ServerResponse> listAllPurchases(ListUseCase listUseCase){
        return route(
                GET("/getAllPurchases"), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.listAllPurchases(), Purchase.class))
        );
    }


}
