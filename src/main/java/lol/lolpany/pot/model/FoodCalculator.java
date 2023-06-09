package lol.lolpany.pot.model;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodCalculator {

    private final double MAX_FOOD_QUANTITY = 1;
    private final int MAX_QUANTITY_MULTIPLIER = 4;
    private final double QUANTITY_PART_SIZE = MAX_FOOD_QUANTITY / MAX_QUANTITY_MULTIPLIER;

    public List<FoodAndQuantity> calculate(Person person, FoodTarget foodTarget, List<Food> foods,
                                           Set<Long> prohibitedFood) throws IOException {
        validateParameters(foodTarget);
        // for performance
        person.age = Year.now().getValue() - person.birthYear;
        List<FoodAndQuantity> result = new ArrayList<>();
        double maxScore = -Double.MAX_VALUE;
        double currentScore;
        List<FoodAndQuantity> foodsAndQuantities = initFoodsQuantities(foods);
        double numberOfCombinations = Math.pow(foods.size() + 1, MAX_QUANTITY_MULTIPLIER);
        int[] quantityPartsAllocations = new int[MAX_QUANTITY_MULTIPLIER];
        for (int i = 0; i < numberOfCombinations; i++) {
            int j = 0;
            while (j < MAX_QUANTITY_MULTIPLIER && quantityPartsAllocations[j] == foods.size()) {
                j++;
            }
            if (j >= MAX_QUANTITY_MULTIPLIER) {
                break;
            }
            for (int k = 0; k < j; k++) {
                foodsAndQuantities.get(quantityPartsAllocations[k] - 1).quantity -= QUANTITY_PART_SIZE;
                quantityPartsAllocations[k] = 0;
            }
            quantityPartsAllocations[j]++;
            if (quantityPartsAllocations[j] > 1) {
                foodsAndQuantities.get(quantityPartsAllocations[j] - 2).quantity -= QUANTITY_PART_SIZE;
            }
            foodsAndQuantities.get(quantityPartsAllocations[j] - 1).quantity += QUANTITY_PART_SIZE;
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
            if (quantity > 0) {
                result.add(new FoodAndQuantity(foodAndQuantity.food, quantity));
            }
        }
        return result;
    }

}
