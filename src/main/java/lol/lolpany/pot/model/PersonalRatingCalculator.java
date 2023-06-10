package lol.lolpany.pot.model;

import java.util.List;

public class PersonalRatingCalculator {

    private static final double MAX_PERSONAL_RATING = 10;

    double calculate(List<FoodAndQuantity> foodsAndQuantities) {
        double result = 0;
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            if (foodAndQuantity.food.personalRating == 0 && foodAndQuantity.quantity > 0) {
                return Constants.PROHIBITIVE_SCORE;
            }
            result += foodAndQuantity.food.personalRating / MAX_PERSONAL_RATING * Constants.MAX_ASPECT_SCORE * foodAndQuantity.quantity;
        }
        return result;
    }

}
