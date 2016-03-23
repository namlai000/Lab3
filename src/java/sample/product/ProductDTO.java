/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sample.product;

import java.io.Serializable;

/**
 *
 * @author Frost
 */
public class ProductDTO implements Serializable {
    private int id;
    private String name;
    private String quantityPerUnit;
    private double price;
    
    private int quantity;

    public ProductDTO(int id, String name, String quantityPerUnit, double price) {
        this.id = id;
        this.name = name;
        this.quantityPerUnit = quantityPerUnit;
        this.price = price;
    }
    
    public ProductDTO(int id, String name, String quantityPerUnit, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.quantityPerUnit = quantityPerUnit;
        this.price = price;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal() {
        return quantity * (price * 1.0);
    }
}
