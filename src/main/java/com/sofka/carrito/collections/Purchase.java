package com.sofka.carrito.collections;

import com.sofka.carrito.models.Bike;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;
import java.util.List;

@Document("purchase")
public class Purchase {
    @Id
    private String id;
    @Field
    private LocalDateTime dateTime;
    @Field
    private String idType;
    @Field
    private String idClient;
    @Field
    private String nameClient;
    @Field
    private List<Bike> products;
}
