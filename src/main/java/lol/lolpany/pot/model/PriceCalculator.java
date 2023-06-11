package lol.lolpany.pot.model;

import java.util.List;

import static lol.lolpany.pot.model.Constants.*;

public class PriceCalculator {


    public double calculate(double priceTarget, List<FoodAndQuantity> foodsAndQuantities) {
        return calculateScore(priceTarget, determineFoodPrice(foodsAndQuantities));
    }

    private double determineFoodPrice(List<FoodAndQuantity> foodsAndQuantities) {
        double result = 0;
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            result += foodAndQuantity.food.price * KILOGRAM_COEFFICIENT * foodAndQuantity.quantity;
        }
        return result;
    }

    private double calculateScore(double priceTarget, double foodPrice) {
        double result;
        if (foodPrice < priceTarget) {
            result = MAX_ASPECT_SCORE;
        } else {
            // todo
            result = Math.max(2 * MAX_ASPECT_SCORE + MIN_ASPECT_SCORE - foodPrice * (-MIN_ASPECT_SCORE / priceTarget), MIN_ASPECT_SCORE);
        }
        return result;
    }

}
