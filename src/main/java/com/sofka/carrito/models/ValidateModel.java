package com.sofka.carrito.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateModel {

    private String message;
    private Boolean isValid;


}
