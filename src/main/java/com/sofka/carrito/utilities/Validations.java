package com.sofka.carrito.utilities;

import com.sofka.carrito.models.Bike;
import com.sofka.carrito.models.Products;
import com.sofka.carrito.models.PurchaseDTO;
import com.sofka.carrito.models.ValidateModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class Validations {
    public Boolean stringNotBlankAndNotNull(String string){
        if (string == null | string == ""){
            return false;
        }

        return true;

    }

    public Boolean isNotNull(Object o){
        if (o == null){
            return false;
        }

        return true;
    }

    public Boolean productsValidate(Products products){

        if(!stringNotBlankAndNotNull(products.getId())){
            return false;
        }

        if(!isNotNull(products.getAmount())){
            return false;
        }

        return true;
    }

    public ValidateModel validateBike(Bike bike){
        if(!stringNotBlankAndNotNull(bike.getId())){
            return new ValidateModel("El id es requerido",false);
        }
        if(!stringNotBlankAndNotNull(bike.getName())){
            return new ValidateModel("El nombre es requerido",false);
        }
        if(!isNotNull(bike.getInInventory())){
            return new ValidateModel("El número en inventario es requerido",false);
        }
        if(!isNotNull(bike.getEnabled())){
            return new ValidateModel("Indique si quiere que la bicicleta esté disponible al público",false);
        }
        if(!isNotNull(bike.getMin())){
            return new ValidateModel("Indique el número mínimo que desea tener en stock",false);
        }
        if(!isNotNull(bike.getMax())){
            return new ValidateModel("Indique el número máximo disponible al público",false);
        }
        if (bike.getMin()<8){
            return new ValidateModel("El número mínimo de unidades debe ser 8",false);
        }
        if (bike.getMax()>200){
            return new ValidateModel("El número máximo de unidades por cliente debe ser máximo 200",false);
        }
        if(!stringNotBlankAndNotNull(bike.getUrlImage())){
            return new ValidateModel("La imágen es requerida",false);
        }
        if(!isNotNull(bike.getPrecio())){
            return new ValidateModel("El precio es requerido",false);
        }
        return new ValidateModel("",true);
    }

    public ValidateModel validatePurchaseDTO(PurchaseDTO purchaseDTO){

        if(!stringNotBlankAndNotNull(purchaseDTO.getIdType())){
            return new ValidateModel("El tipo de documento es requerido",false);
        }
        if(!stringNotBlankAndNotNull(purchaseDTO.getIdClient())){
            return new ValidateModel("El número de documento es requerido",false);
        }
        if(!stringNotBlankAndNotNull(purchaseDTO.getNameClient())){
            return new ValidateModel("El nombre es requerido",false);
        }

        AtomicBoolean flag = new AtomicBoolean(true);

        purchaseDTO.getProducts().forEach(products -> {
            if (!productsValidate(products)){
                 flag.set(false);
            }

        });



        if (!flag.get()){
            return new ValidateModel("Verifique información de los productos",false);
        }



        return new ValidateModel("", true);
    }
}
