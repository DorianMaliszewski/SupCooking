/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author MaliszewskiDorian
 */
@Entity
@Table(name = "Recipes")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String image;
    
    @Column(name = "cooking_time")
    private Integer cookingTime;
    
    private Integer difficulty;
    
    @Column(name = "preparation_time")
    private Integer preparationTime;
    
    @Column(scale = 2)
    private Float mark;
    
    @Column(name = "number_of_mark")
    private Long numberOfMark;
    
    @Column(name = "number_of_view")
    private Long numberOfView;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = User.class)
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeProduct> products;
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Category.class)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        if(!createdBy.getRecipes().contains(this)){
            createdBy.getRecipes().add(this);
        }
    }

    public List<RecipeProduct> getProducts() {
        return products;
    }

    public void addProduct(Product product, Integer quantity, String unit) {
        RecipeProduct association = new RecipeProduct();
        association.setProduct(product);
        association.setRecipe(this);
        association.setProductId(product.getId());
        association.setRecipeId(this.getId());
        association.setQuantity(quantity);
        association.setUnit(unit);
        if(this.products == null)
           this.products = new ArrayList<>();

        this.products.add(association);
            // Also add the association object to the employee.
            product.getRecipes().add(association);
        }
    
    public void setProducts(List<RecipeProduct> products) {
        this.products = products;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public Long getNumberOfMark() {
        return numberOfMark;
    }

    public void setNumberOfMark(Long numberOfMark) {
        this.numberOfMark = numberOfMark;
    }

    public Long getNumberOfView() {
        return numberOfView;
    }

    public void setNumberOfView(Long numberOfView) {
        this.numberOfView = numberOfView;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Recipes[ id=" + id + " ]";
    }
    
}
