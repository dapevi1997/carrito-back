package com.sofka.carrito.models;

import lombok.Data;

import java.util.Objects;

@Data
public class Bike {
    private String id;
    private String name;

    private Long inInventory;

    private Boolean enabled;

    private Long min;

    private Long max;

    private String urlImage;

    private Boolean state;

    private Double precio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Objects.equals(id, bike.id) && Objects.equals(name, bike.name) && Objects.equals(inInventory, bike.inInventory) && Objects.equals(enabled, bike.enabled) && Objects.equals(min, bike.min) && Objects.equals(max, bike.max) && Objects.equals(urlImage, bike.urlImage) && Objects.equals(state, bike.state) && Objects.equals(precio, bike.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, inInventory, enabled, min, max, urlImage, state, precio);
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inInventory=" + inInventory +
                ", enabled=" + enabled +
                ", min=" + min +
                ", max=" + max +
                ", urlImage='" + urlImage + '\'' +
                ", state=" + state +
                ", precio=" + precio +
                '}';
    }
}
