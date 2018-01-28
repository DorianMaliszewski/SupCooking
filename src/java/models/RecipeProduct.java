/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Proxy;

/**
 *
 * @author MaliszewskiDorian
 */
@Entity
@Table(name = "recipes_products")
@IdClass(RecipeProductId.class)
@XmlRootElement
public class RecipeProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "recipe_id")
    private Integer recipeId;
    
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private String unit;
    
    @Column(nullable = false, precision = 1)
    private Float quantity;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Recipe recipe;
    
    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Product product;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    

    @Override
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
    }
    
}
