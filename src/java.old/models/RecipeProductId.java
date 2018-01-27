/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author dorian
 */
public class RecipeProductId implements Serializable{

    private Integer recipeId;

    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int hashCode() {
        return (int)(recipeId + productId);
    }

    @Override
    public boolean equals(Object object) {
      if (object instanceof RecipeProductId) {
        RecipeProductId otherId = (RecipeProductId) object;
        return (Objects.equals(otherId.recipeId, this.recipeId)) && (Objects.equals(otherId.productId, this.productId));
      }
      return false;
    }


    @Override
    public String toString() {
        return "models.RecipeProductId[ recipeId=" + recipeId + " productId="+ productId +" ]";
    }
    
}
