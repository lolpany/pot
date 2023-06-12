package lol.lolpany.pot.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodCalculator {

    private final double MAX_FOOD_QUANTITY = 1;
    private final int MAX_QUANTITY_MULTIPLIER = 4;
    private final double QUANTITY_PART_SIZE = MAX_FOOD_QUANTITY / MAX_QUANTITY_MULTIPLIER;

    private final double MAX_FOOD_QUANTITY2 = 0.4;
    private final int MAX_QUANTITY_MULTIPLIER2 = 4;
    private final double QUANTITY_PART_SIZE2 = MAX_FOOD_QUANTITY2 / MAX_QUANTITY_MULTIPLIER2;
    private final double MINIMAL_PART_SIZE = 0.1;

    public List<FoodAndQuantity> calculate(Person person, FoodTarget foodTarget, List<Food> foods,
                                           Set<Long> prohibitedFood) {
        validateParameters(foodTarget);
        // for performance
        person.age = Year.now().getValue() - person.birthYear;

        List<FoodAndQuantity> result = calculate(person, foodTarget, prohibitedFood, initFoodsQuantities(foods), MAX_QUANTITY_MULTIPLIER, QUANTITY_PART_SIZE);
        result = calculate(person, foodTarget, prohibitedFood, result, MAX_QUANTITY_MULTIPLIER2, QUANTITY_PART_SIZE2);
        result = filterFoodQuantities(result);
        double maxQuantity = 0;
        for (FoodAndQuantity foodAndQuantity : result) {
            if (foodAndQuantity.quantity > maxQuantity) {
                maxQuantity = foodAndQuantity.quantity;
                foodAndQuantity.quantity = 0;
            }
        }
        result = filterFoodQuantities(calculate(person, foodTarget, prohibitedFood, result,
                Math.toIntExact(Math.round(maxQuantity / MINIMAL_PART_SIZE)), MINIMAL_PART_SIZE));
        return result;
    }

    public List<FoodAndQuantity> calculate(Person person, FoodTarget foodTarget,
                                           Set<Long> prohibitedFood,
                                           List<FoodAndQuantity> foodsAndQuantities, int maxQuantityMultiplier,
                                           double quantityPartSize) {
        List<FoodAndQuantity> result = new ArrayList<>();
        double maxScore = -Double.MAX_VALUE;
        double currentScore;
        double numberOfCombinations = Math.pow(foodsAndQuantities.size() + 1, maxQuantityMultiplier);
        int[] quantityPartsAllocations = new int[maxQuantityMultiplier];
        for (int i = 0; i < numberOfCombinations; i++) {
            int j = 0;
            while (j < maxQuantityMultiplier && quantityPartsAllocations[j] == foodsAndQuantities.size()) {
                j++;
            }
            if (j >= maxQuantityMultiplier) {
                break;
            }
            for (int k = 0; k < j; k++) {
                foodsAndQuantities.get(quantityPartsAllocations[k] - 1).quantity -= quantityPartSize;
                quantityPartsAllocations[k] = 0;
            }
            quantityPartsAllocations[j]++;
            if (quantityPartsAllocations[j] > 1) {
                foodsAndQuantities.get(quantityPartsAllocations[j] - 2).quantity -= quantityPartSize;
            }
            foodsAndQuantities.get(quantityPartsAllocations[j] - 1).quantity += quantityPartSize;
            currentScore = calculateScore(person, foodTarget, foodsAndQuantities, prohibitedFood);
            if (currentScore > maxScore) {
                maxScore = currentScore;
                result = copyFoods(foodsAndQuantities);
            }
        }
        return result;
    }

    private void validateParameters(FoodTarget foodTarget) {
        if (foodTarget.calImportance < Constants.MIN_ASPECT_COEFFICIENT) {
            throw new IllegalArgumentException();
        }
        if (foodTarget.priceImportance < Constants.MIN_ASPECT_COEFFICIENT) {
            throw new IllegalArgumentException();
        }
        if (foodTarget.personalRatingImportance < Constants.MIN_ASPECT_COEFFICIENT) {
            throw new IllegalArgumentException();
        }
    }

    private List<FoodAndQuantity> initFoodsQuantities(List<Food> foods) {
        List<FoodAndQuantity> result = new ArrayList<>(foods.size());
        for (Food food : foods) {
            result.add(new FoodAndQuantity(food, 0));
        }
        return result;
    }

    // todo
    private double calculateScore(Person person, FoodTarget foodTarget, List<FoodAndQuantity> foodsAndQuantities,
                                  Set<Long> prohibitedFood) {
        double calScore = new CalCalculator().calculate(person, foodTarget.weightTarget, foodTarget.kcal, foodsAndQuantities);
        double priceScore = new PriceCalculator().calculate(foodTarget.price, foodsAndQuantities);
        double personalRatingScore = new PersonalRatingCalculator().calculate(foodsAndQuantities, prohibitedFood);
        return foodTarget.calImportance * calScore + foodTarget.priceImportance * priceScore +
                foodTarget.personalRatingImportance * personalRatingScore;
    }

    private List<FoodAndQuantity> copyFoods(List<FoodAndQuantity> foodsAndQuantities) {
        List<FoodAndQuantity> result = new ArrayList<>();
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            double quantity = Math.round(foodAndQuantity.quantity * 100.0) / 100.0;
            result.add(new FoodAndQuantity(foodAndQuantity.food, quantity));
        }
        return result;
    }

    private List<FoodAndQuantity> filterFoodQuantities(List<FoodAndQuantity> foodsQuantities) {
        List<FoodAndQuantity> result = new ArrayList<>();
        for (FoodAndQuantity foodAndQuantity : foodsQuantities) {
            if (foodAndQuantity.quantity > 0) {
                result.add(foodAndQuantity);
            }
        }
        return result;
    }
}
