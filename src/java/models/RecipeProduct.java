/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Objects;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MaliszewskiDorian
 */
@Entity
@Table(name = "recipes_products")
@XmlRootElement
public class RecipeProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String unit;
    
    @Column(nullable = false, precision = 1)
    private Float quantity;
    
    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recipe_id", updatable = false, insertable = false, referencedColumnName = "id")
    @JsonbTransient
    private Recipe recipe;
    
    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Product product;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public Float getQuantity() {
        return quantity;
    }

    public RecipeProduct setQuantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeProduct setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public RecipeProduct setProduct(Product product) {
        this.product = product;
        return this;
    }
    

    /*@Override
    public int hashCode() {
        return (int)(recipeId + productId);
    }

    @Override
    public boolean equals(Object object) {
      if (object instanceof RecipeProduct) {
        RecipeProduct otherId = (RecipeProduct) object;
        return (Objects.equals(otherId.recipeId, this.recipeId)) && (Objects.equals(otherId.productId, this.productId));
      }
      return false;
    }


    @Override
    public String toString() {
        return "models.RecipeProductId[ recipeId=" + recipeId + " productId="+ productId +" ]";
    }*/
    
    @Override
    public int hashCode() {
        return (int)(recipe.getId() + product.getId());
    }

    @Override
    public boolean equals(Object object) {
      if (object instanceof RecipeProduct) {
        RecipeProduct other = (RecipeProduct) object;
        return (Objects.equals(other.recipe.getId(), this.recipe.getId())) && (Objects.equals(other.product.getId(), this.product.getId()));
      }
      return false;
    }


    @Override
    public String toString() {
        return "models.RecipeProductId[ recipeId=" + recipe.getId() + " productId="+ product.getId() +" ]";
    }
    
}
