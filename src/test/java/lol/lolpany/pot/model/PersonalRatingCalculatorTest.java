package lol.lolpany.pot.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lol.lolpany.pot.model.PersonalRatingCalculator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalRatingCalculatorTest {

    @Test
    public void testMaxScore() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        Food orange = new Food(1L, "orange");
        orange.setPersonalRating(MAX_PERSONAL_RATING);
        FoodAndQuantity orangeQuantity = new FoodAndQuantity(orange, 0.3);
        foodsQuantities.add(orangeQuantity);
        Food apple = new Food(1L, "apple");
        apple.setPersonalRating(MAX_PERSONAL_RATING);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.3);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(1L, "kiwifruit");
        kiwifruit.setPersonalRating(MAX_PERSONAL_RATING);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.3);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(Constants.MAX_ASPECT_SCORE, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));

        foodsQuantities.clear();
        foodsQuantities.add(orangeQuantity);
        assertEquals(Constants.MAX_ASPECT_SCORE, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));
    }

    @Test
    public void testZeroScore() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        Food orange = new Food(1L, "orange");
        orange.setPersonalRating(MIN_POSITIVE_PERSONAL_RATING);
        FoodAndQuantity orangeQuantity = new FoodAndQuantity(orange, 0.3);
        foodsQuantities.add(orangeQuantity);
        Food apple = new Food(1L, "apple");
        apple.setPersonalRating(MIN_POSITIVE_PERSONAL_RATING);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.3);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(1L, "kiwifruit");
        kiwifruit.setPersonalRating(MIN_POSITIVE_PERSONAL_RATING);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.3);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(0, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));

        foodsQuantities.clear();
        foodsQuantities.add(orangeQuantity);
        assertEquals(0, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));
    }

    @Test
    public void testMinScore() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        Food orange = new Food(1L, "orange");
        orange.setPersonalRating(MIN_PERSONAL_RATING);
        FoodAndQuantity orangeQuantity = new FoodAndQuantity(orange, 0.3);
        foodsQuantities.add(orangeQuantity);
        Food apple = new Food(1L, "apple");
        apple.setPersonalRating(MIN_PERSONAL_RATING);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.3);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(1L, "kiwifruit");
        kiwifruit.setPersonalRating(MIN_PERSONAL_RATING);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.3);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(Constants.MIN_ASPECT_SCORE, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));

        foodsQuantities.clear();
        foodsQuantities.add(orangeQuantity);
        assertEquals(Constants.MIN_ASPECT_SCORE, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.emptySet()));
    }

    @Test
    public void testProhibitedFood() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        final long riceId = 1L;
        Food rice = new Food(riceId, "rice");
        rice.setPersonalRating(MIN_PERSONAL_RATING);
        FoodAndQuantity riceQuantity = new FoodAndQuantity(rice, 0.3);
        foodsQuantities.add(riceQuantity);
        Food apple = new Food(2L, "apple");
        apple.setPersonalRating(MIN_PERSONAL_RATING);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.3);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(3L, "kiwifruit");
        kiwifruit.setPersonalRating(MIN_PERSONAL_RATING);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.3);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(Constants.PROHIBITIVE_SCORE, new PersonalRatingCalculator().calculate(foodsQuantities, Collections.singleton(riceId)));
    }

}
