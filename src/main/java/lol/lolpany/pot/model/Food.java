package lol.lolpany.pot.model;

import java.util.Objects;

public class Food {
    long id;
    String name;
    double proteins;
    double fats;
    double carbohydrates;
    double ethanol;
    double vitaminA;
    double vitaminB1;
    double vitaminB3;
    double vitaminB5;
    double vitaminB6;
    double vitaminB7;
    double vitaminB9;
    double vitaminB12;
    double vitaminC;
    double vitaminD;
    double vitaminE;
    double vitaminK;
    double potassium;
    double chlorine;
    double sodium;
    double calcium;
    double phosphorus;
    double magnesium;
    double iron;
    double zinc;
    double manganese;
    double copper;
    double iodine;
    double chromium;
    double molybdenum;
    double selenium;
    double cobalt;
    double healthiness;
    double price;
    double preparationTime;
    double personalRating;
    NormalVeganVegetarian normalVeganVegetarian;

    public Food(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id == food.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
