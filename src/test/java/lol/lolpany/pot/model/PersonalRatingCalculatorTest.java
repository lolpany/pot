package lol.lolpany.pot.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalRatingCalculatorTest {

    @Test
    public void testMaxScore() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        Food orange = new Food(1L, "orange");
        orange.setPersonalRating(10);
        FoodAndQuantity orangeQuantity = new FoodAndQuantity(orange, 0.3);
        foodsQuantities.add(orangeQuantity);
        Food apple = new Food(1L, "apple");
        apple.setPersonalRating(10);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.3);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(1L, "kiwifruit");
        kiwifruit.setPersonalRating(10);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.3);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(Constants.MAX_ASPECT_SCORE, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));
    }

    @Test
    public void testZeroScore() {

    }

    @Test
    public void testMinScore() {

    }

}
