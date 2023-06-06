package lol.lolpany.pot.model;

import java.time.Year;
import java.util.List;

import static lol.lolpany.pot.model.Constants.MAX_ASPECT_SCORE;
import static lol.lolpany.pot.model.Constants.MIN_ASPECT_SCORE;

public class CalCalculator {

    private final int KILOGRAM_COEFFICIENT = 10;
    private final int KCAL_IN_PROTEIN = 4;
    private final int KCAL_IN_FAT = 9;
    private final int KCAL_IN_CARBOHYDRATE = 4;
    private final int KCAL_IN_ETHANOL = 7;

    public double calculate(Person person, WeightTarget weightTarget, Double kcal,
                            List<FoodAndQuantity> foodsAndQuantities) {
        double kcalNorm = kcalNormDetermination(person);
        double activityCoefficient = activityCoefficientDetermination(person.activityLevel);
        double weightTargetCoefficient = weightTargetCoefficientDetermination(weightTarget);
        double kcalTarget = kcal != null ? kcal : kcalNorm * activityCoefficient * weightTargetCoefficient;
        double kcalInFood = kcalInFoodDetermination(foodsAndQuantities);
        return calculateScore(kcalTarget, kcalInFood);
    }

    // todo
    private double kcalNormDetermination(Person person) {
        double result = 1500;
        int age = Year.now().getValue() - person.birthYear;
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

    private double kcalInFoodDetermination(List<FoodAndQuantity> foodsAndQuantities) {
        double result = 0;
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            result += foodAndQuantity.food.proteins * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity * KCAL_IN_PROTEIN;
            result += foodAndQuantity.food.fats * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity * KCAL_IN_FAT;
            result += foodAndQuantity.food.carbohydrates * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity * KCAL_IN_CARBOHYDRATE;
            result += foodAndQuantity.food.ethanol * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity * KCAL_IN_ETHANOL;
        }
        return result;
    }

    private double calculateScore(double kcalTarget, double kcalInFood) {
        double result;
        if (kcalInFood < kcalTarget) {
            result = MAX_ASPECT_SCORE * kcalInFood / kcalTarget;
        } else {
            result = 2 * MAX_ASPECT_SCORE + MIN_ASPECT_SCORE - kcalInFood * (kcalTarget / -MIN_ASPECT_SCORE);
        }
        return result;
    }

}
