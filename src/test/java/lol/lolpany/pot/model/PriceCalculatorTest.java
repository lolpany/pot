package lol.lolpany.pot.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculatorTest {

    @Test
    public void testMaxScore() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        Food orange = new Food(1L, "orange");
        final double orangePrice = 15;
        orange.setPrice(orangePrice);
        FoodAndQuantity orangeQuantity = new FoodAndQuantity(orange, 0.1);
        foodsQuantities.add(orangeQuantity);
        Food apple = new Food(1L, "apple");
        final double applePrice = 10;
        apple.setPrice(applePrice);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.1);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(1L, "kiwifruit");
        final double kiwifruitPrice = 20;
        kiwifruit.setPrice(kiwifruitPrice);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.1);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(Constants.MAX_ASPECT_SCORE, new PriceCalculator().calculate(orangePrice + applePrice + kiwifruitPrice, foodsQuantities));

        assertEquals(Constants.MAX_ASPECT_SCORE, new PriceCalculator().calculate(orangePrice + applePrice + kiwifruitPrice + 100, foodsQuantities));
    }

    @Test
    public void testMinScore() {
        List<FoodAndQuantity> foodsQuantities = new ArrayList<>();
        Food orange = new Food(1L, "orange");
        final double orangePrice = 15;
        orange.setPrice(orangePrice);
        FoodAndQuantity orangeQuantity = new FoodAndQuantity(orange, 0.1);
        foodsQuantities.add(orangeQuantity);
        Food apple = new Food(1L, "apple");
        final double applePrice = 10;
        apple.setPrice(applePrice);
        FoodAndQuantity appleQuantity = new FoodAndQuantity(apple, 0.1);
        foodsQuantities.add(appleQuantity);
        Food kiwifruit = new Food(1L, "kiwifruit");
        final double kiwifruitPrice = 20;
        kiwifruit.setPrice(kiwifruitPrice);
        FoodAndQuantity kiwifruitQuantity = new FoodAndQuantity(kiwifruit, 0.1);
        foodsQuantities.add(kiwifruitQuantity);
        assertEquals(Constants.MIN_ASPECT_SCORE, new PriceCalculator().calculate((orangePrice + applePrice + kiwifruitPrice) / 2, foodsQuantities));

        assertEquals(Constants.MIN_ASPECT_SCORE, new PriceCalculator().calculate((orangePrice + applePrice + kiwifruitPrice - 5) / 2, foodsQuantities));
    }

}
