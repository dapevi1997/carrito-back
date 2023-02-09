package com.sofka.carrito.routers;

import com.sofka.carrito.collections.Purchase;
import com.sofka.carrito.models.PurchaseDTO;
import com.sofka.carrito.usecases.AddPurchaseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PurchaseRouter {
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
            addPurchaseUseCase.addPurchase(purchaseDTO)
                    .flatMap(result -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(result));
        return route(
                POST("/addPurchase").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(PurchaseDTO.class).flatMap(executor)
        );
    }


}
