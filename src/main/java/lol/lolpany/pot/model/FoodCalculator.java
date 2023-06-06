package lol.lolpany.pot.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class FoodCalculator {

    private final double MAX_FOOD_QUANTITY = 1;
    private final int MAX_QUANTITY_MULTIPLIER = 5;
    private final double QUANTITY_STEP = MAX_FOOD_QUANTITY / MAX_QUANTITY_MULTIPLIER;

    public List<FoodAndQuantity> calculate(Person person, FoodTarget foodTarget, List<Food> foods) {
        // for performance
        person.age = Year.now().getValue() - person.birthYear;
        List<FoodAndQuantity> result = new ArrayList<>();
        double maxScore = -Double.MAX_VALUE;
        double currentScore;
        List<FoodAndQuantity> foodsAndQuantities = initFoodsQuantities(foods);
        double overallQuantity = 0;
        double numberOfCombinations = Math.pow(MAX_QUANTITY_MULTIPLIER, foods.size());
        for (int i = 0; i < numberOfCombinations; i++) {
            int j = 0;
            while (j < foodsAndQuantities.size() && foodsAndQuantities.get(j).quantity >= MAX_FOOD_QUANTITY) {
                j++;
            }
            if (j >= foodsAndQuantities.size()) {
                break;
            }
            for (int k = 0; k < j; k++) {
                overallQuantity -= foodsAndQuantities.get(k).quantity;
                foodsAndQuantities.get(k).quantity = 0;
            }
            foodsAndQuantities.get(j).quantity += QUANTITY_STEP;
            overallQuantity += QUANTITY_STEP;
            if (overallQuantity <= MAX_FOOD_QUANTITY) {
                currentScore = calculateScore(person, foodTarget, foodsAndQuantities);
                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    result = copyFoods(foodsAndQuantities);
                }
            }
        }
        return result;
    }

    private List<FoodAndQuantity> initFoodsQuantities(List<Food> foods) {
        List<FoodAndQuantity> result = new ArrayList<>(foods.size());
        for (Food food : foods) {
            result.add(new FoodAndQuantity(food, 0));
        }
        return result;
    }

    // todo
    private double calculateScore(Person person, FoodTarget foodTarget, List<FoodAndQuantity> foodsAndQuantities) {
        double calScore = new CalCalculator().calculate(person, foodTarget.weightTarget, foodTarget.kcal, foodsAndQuantities);
        double priceScore = new PriceCalculator().calculate(foodTarget.price, foodsAndQuantities);
        return foodTarget.calImportance * calScore + foodTarget.priceImportance * priceScore;
    }

    private List<FoodAndQuantity> copyFoods(List<FoodAndQuantity> foodsAndQuantities) {
        List<FoodAndQuantity> result = new ArrayList<>();
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            if (foodAndQuantity.quantity > 0) {
                result.add(new FoodAndQuantity(foodAndQuantity.food, foodAndQuantity.quantity));
            }
        }
        return result;
    }

}
