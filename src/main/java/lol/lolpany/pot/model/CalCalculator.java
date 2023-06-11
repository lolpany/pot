package lol.lolpany.pot.model;

import java.util.List;

import static lol.lolpany.pot.model.Constants.*;

public class CalCalculator {

    private final int KCAL_IN_PROTEIN = 4;
    private final int KCAL_IN_FAT = 9;
    private final int KCAL_IN_CARBOHYDRATE = 4;
    private final int KCAL_IN_ETHANOL = 7;
    private final double PROTEINS_PERCENT = 0.25;
    private final double FATS_PERCENT = 0.15;
    private final double CARBOHYDRATES_PERCENT = 0.6;
    private static final int NUMBER_OF_NUTRIENTS = 3;
    private static final double MAX_NUTRIENT_SCORE = MAX_ASPECT_SCORE / NUMBER_OF_NUTRIENTS;
    private static final double MIN_NUTRIENT_SCORE = MIN_ASPECT_SCORE / NUMBER_OF_NUTRIENTS;

    public double calculate(Person person, WeightTarget weightTarget, Double kcal,
                            List<FoodAndQuantity> foodsAndQuantities) {
        double kcalNorm = kcalNormDetermination(person);
        double activityCoefficient = activityCoefficientDetermination(person.activityLevel);
        double weightTargetCoefficient = weightTargetCoefficientDetermination(weightTarget);
        double kcalTarget = kcal != null ? kcal : kcalNorm * activityCoefficient * weightTargetCoefficient;
        ProteinsFatsCarbohydrates nutrientsTarget = determineNutrientsTarget(kcalTarget);
        ProteinsFatsCarbohydrates nutrientsInFood = determineNutrientsInFood(foodsAndQuantities);
        return calculateScore(nutrientsTarget, nutrientsInFood);
    }


    // todo
    private double kcalNormDetermination(Person person) {
        double result = 1500;
        switch (person.sex) {
            case MALE -> {

            }
            case FEMALE -> {

            }
            default -> {
            }
        }
        return result;
    }

    private double activityCoefficientDetermination(ActivityLevel activityLevel) {
        double result = 1;
        if (activityLevel != null) {
            switch (activityLevel) {
                case SEDENTARY -> {
                    result = 0.6;
                }
                case SLIGHTLY_ACTIVE -> {
                    result = 1;
                }
                case VERY_ACTIVE -> {
                    result = 1.3;
                }
            }
        }
        return result;
    }

    private double weightTargetCoefficientDetermination(WeightTarget weightTarget) {
        double result = 1;
        if (weightTarget != null) {
            switch (weightTarget) {
                case LOST_WEIGHT -> {
                    result = 0.8;
                }
                case STAY_SAME -> {
                }
                case GET_MUSCLES -> {
                    result = 1.5;
                }
            }
        }
        return result;
    }

    private ProteinsFatsCarbohydrates determineNutrientsTarget(double kcalTarget) {
        double proteinsTarget = PROTEINS_PERCENT * kcalTarget / KCAL_IN_PROTEIN;
        double fatsTarget = FATS_PERCENT * kcalTarget / KCAL_IN_FAT;
        double carbohydratesTarget = CARBOHYDRATES_PERCENT * kcalTarget / KCAL_IN_CARBOHYDRATE;
        return new ProteinsFatsCarbohydrates(proteinsTarget, fatsTarget, carbohydratesTarget);
    }

    private ProteinsFatsCarbohydrates determineNutrientsInFood(List<FoodAndQuantity> foodsAndQuantities) {
        ProteinsFatsCarbohydrates result = new ProteinsFatsCarbohydrates(0, 0, 0);
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            result.proteins += foodAndQuantity.food.proteins * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity;
            result.fats += foodAndQuantity.food.fats * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity;
            result.carbohydrates += foodAndQuantity.food.carbohydrates * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity;
        }
        return result;
    }

    private double calculateScore(ProteinsFatsCarbohydrates nutrientsTarget,
                                  ProteinsFatsCarbohydrates nutrientsInFood) {
        return calculateScore(nutrientsTarget.proteins, nutrientsInFood.proteins)
                + calculateScore(nutrientsTarget.fats, nutrientsInFood.fats)
                + calculateScore(nutrientsTarget.carbohydrates, nutrientsInFood.carbohydrates);
    }

    private double calculateScore(double nutrientTarget, double nutrientInFood) {
        double result;
        if (nutrientInFood < 0.5 * nutrientTarget) {
            result = MIN_NUTRIENT_SCORE;
        } else if (nutrientInFood >= 0.5 * nutrientTarget && nutrientInFood < nutrientTarget) {
            result = MAX_NUTRIENT_SCORE * nutrientInFood / nutrientTarget;
        } else {
            // todo
            result = Math.max(2 * MAX_NUTRIENT_SCORE + MIN_NUTRIENT_SCORE
                    - nutrientInFood * (-MIN_NUTRIENT_SCORE + MAX_NUTRIENT_SCORE / nutrientTarget), MIN_NUTRIENT_SCORE);
        }
        return result;
    }

}
