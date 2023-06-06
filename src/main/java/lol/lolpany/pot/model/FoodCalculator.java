package lol.lolpany.pot.model;

import java.util.ArrayList;
import java.util.List;

public class FoodCalculator {

    private final double QUANTITY_STEP = 0.1;
    private final int MAX_QUANTITY_MULTIPLIER = 100;

    public List<FoodAndQuantity> calculate(Person person, FoodTarget foodTarget, List<Food> foods) {
        List<FoodAndQuantity> result = new ArrayList<>();
        double maxScore = -Double.MAX_VALUE;
        double currentScore;
        List<FoodAndQuantity> foodsAndQuantities = initFoodsQuantities(foods);
        for (int i = 0; i < foods.size() * MAX_QUANTITY_MULTIPLIER; i++) {
            int j = 0;
            while (j < foodsAndQuantities.size() && foodsAndQuantities.get(j).quantity >= QUANTITY_STEP * MAX_QUANTITY_MULTIPLIER) {
                j++;
            }
            if (j >= foodsAndQuantities.size()) {
                break;
            }
            for (int k = 0; k < j; k++) {
                foodsAndQuantities.get(k).quantity = 0;
            }
            foodsAndQuantities.get(j).quantity += QUANTITY_STEP;
            currentScore = calculateScore(person, foodTarget, foodsAndQuantities);
            if (currentScore > maxScore) {
                maxScore = currentScore;
                result = copyFoods(foodsAndQuantities);
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

    private double calculateScore(Person person, FoodTarget foodTarget, List<FoodAndQuantity> currentFoods) {
        return 0;
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
