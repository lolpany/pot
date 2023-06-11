package lol.lolpany.pot.model;

import java.util.List;
import java.util.Set;

public class PersonalRatingCalculator {

    private static final double MAX_PERSONAL_RATING = 10;

    double calculate(List<FoodAndQuantity> foodsAndQuantities, Set<Long> prohibitedFood) {
        double result = 0;
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            if (prohibitedFood.contains(foodAndQuantity.food.id) && foodAndQuantity.quantity > 0) {
                return Constants.PROHIBITIVE_SCORE;
            }
            result += foodAndQuantity.food.personalRating / MAX_PERSONAL_RATING * Constants.MAX_ASPECT_SCORE * foodAndQuantity.quantity;
        }
        return result;
    }

}
