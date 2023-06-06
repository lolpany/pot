package lol.lolpany.pot.model;

public class FoodTarget {
    WeightTarget weightTarget;
    Double kcal;
    double calImportance;
    double healthiness;
    double price;
    double priceImportance;
    double preparationTime;
    double preparationTimeImportance;
    double personalRatingImportance;
    NormalVeganVegetarian normalVeganVegetarian;
    double variability;

    public FoodTarget(WeightTarget weightTarget, Double kcal, double calImportance, double healthiness, double price,
                      double priceImportance, double preparationTime, double preparationTimeImportance,
                      double personalRatingImportance, NormalVeganVegetarian normalVeganVegetarian,
                      double variability) {
        this.weightTarget = weightTarget;
        this.kcal = kcal;
        this.calImportance = calImportance;
        this.healthiness = healthiness;
        this.price = price;
        this.priceImportance = priceImportance;
        this.preparationTime = preparationTime;
        this.preparationTimeImportance = preparationTimeImportance;
        this.personalRatingImportance = personalRatingImportance;
        this.normalVeganVegetarian = normalVeganVegetarian;
        this.variability = variability;
    }
}
