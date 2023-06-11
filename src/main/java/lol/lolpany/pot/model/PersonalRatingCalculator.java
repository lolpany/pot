package lol.lolpany.pot.model;

import java.util.List;
import java.util.Set;

import static lol.lolpany.pot.model.Constants.NUMBER_OF_ASPECTS;

public class PersonalRatingCalculator {

    static final double MIN_PERSONAL_RATING = 0;
    static final double MAX_PERSONAL_RATING = 10;
    static final double MIN_POSITIVE_PERSONAL_RATING = 0;

    double calculate(List<FoodAndQuantity> foodsAndQuantities, Set<Long> prohibitedFood) {
        double totalQuantity = 0;
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            if (prohibitedFood.contains(foodAndQuantity.food.id) && foodAndQuantity.quantity > 0) {
                return Constants.PROHIBITIVE_SCORE;
            }
            totalQuantity += foodAndQuantity.quantity;
        }
        double result = 0;
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            if (foodAndQuantity.food.personalRating >= MIN_POSITIVE_PERSONAL_RATING) {
                result += (foodAndQuantity.food.personalRating - MIN_POSITIVE_PERSONAL_RATING)
                        / (MAX_PERSONAL_RATING - MIN_POSITIVE_PERSONAL_RATING)
                        * Constants.MAX_ASPECT_SCORE * foodAndQuantity.quantity / totalQuantity;
            } else {
                result += (foodAndQuantity.food.personalRating - MAX_PERSONAL_RATING)
                        / (MAX_PERSONAL_RATING - MIN_POSITIVE_PERSONAL_RATING)
                        * Constants.MAX_ASPECT_SCORE * foodAndQuantity.quantity / totalQuantity
                        * NUMBER_OF_ASPECTS;
            }
        }
        return result;
    }

}
