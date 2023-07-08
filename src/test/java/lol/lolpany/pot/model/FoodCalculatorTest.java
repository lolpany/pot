package lol.lolpany.pot.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodCalculatorTest {

    private Person createPerson() {
        return new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
    }

    private List<Food> readFoods() throws IOException {
        List<Food> result = new ArrayList<>();
        Reader fileReader = new FileReader("C:\\all\\projects\\pot\\src\\test\\resources\\food14.tsv");
        Iterable<CSVRecord> records = CSVFormat.TDF.builder().setHeader().build().parse(fileReader);
        long id = 0;
        for (CSVRecord record : records) {
            Food food = new Food(id++, record.get("name"));
            food.setProteins(Double.parseDouble(record.get("proteins")));
            food.setFats(Double.parseDouble(record.get("fats")));
            food.setCarbohydrates(Double.parseDouble(record.get("carbohydrates")));
            food.setPrice(Double.parseDouble(record.get("price")));
            food.setPersonalRating(Double.parseDouble(record.get("personalRating")));
            result.add(food);
        }
        return result;
    }

    @Test
    public void testCaloriesCalculation() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 1, 0.01, 0.9, 1, 0.01, 3600, 0.9, 0.01,
                NormalVeganVegetarian.NORMAL, 0);
        FoodCalculator foodCalculator = new FoodCalculator();
        List<Food> foods = readFoods();
        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods, Collections.emptySet());
        assertEquals("chicken breast, braised", foodsAndQuantities.get(0).food.name);
        assertEquals(0.3, foodsAndQuantities.get(0).quantity);
        assertEquals("rice, white, raw", foodsAndQuantities.get(1).food.name);
        assertEquals(0.25, foodsAndQuantities.get(1).quantity);
        assertEquals("milk, 3.25% milkfat", foodsAndQuantities.get(2).food.name);
        assertEquals(0.1, foodsAndQuantities.get(2).quantity);
        assertEquals("oranges, navels", foodsAndQuantities.get(3).food.name);
        assertEquals(0.2, foodsAndQuantities.get(3).quantity);
        assertEquals("spinach, raw", foodsAndQuantities.get(4).food.name);
        assertEquals(0.1, foodsAndQuantities.get(4).quantity);
    }

    @Test
    public void testPriceCalculation() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 0.01, 0.01, 0.9, 50, 1, 3600, 0.9, 0.01,
                NormalVeganVegetarian.NORMAL, 0);
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = readFoods();

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods, Collections.emptySet());
        assertEquals("pasta, dry", foodsAndQuantities.get(0).food.name);
        assertEquals(0.3, foodsAndQuantities.get(0).quantity);
        assertEquals("milk, 3.25% milkfat", foodsAndQuantities.get(1).food.name);
        assertEquals(0.3, foodsAndQuantities.get(1).quantity);
    }

    @Test
    public void testCaloriesAndPriceCalculation() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 1, 0.01, 0.9, 50, 1, 3600, 0.9, 0.01,
                NormalVeganVegetarian.NORMAL, 0);
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = readFoods();

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods, Collections.emptySet());
        assertEquals("rice, white, raw", foodsAndQuantities.get(0).food.name);
        assertEquals(0.2, foodsAndQuantities.get(0).quantity);
        assertEquals("milk, 3.25% milkfat", foodsAndQuantities.get(1).food.name);
        assertEquals(0.3, foodsAndQuantities.get(1).quantity);
        assertEquals("salt, iodized", foodsAndQuantities.get(2).food.name);
        assertEquals(0.1, foodsAndQuantities.get(2).quantity);
    }

    @Test
    public void testPersonalRatingCalculation() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 0.01, 0.01, 0.9, 100, 0.01, 3600, 0.9, 1,
                NormalVeganVegetarian.NORMAL, 0);
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = readFoods();

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods, Collections.emptySet());
        assertEquals("chicken egg", foodsAndQuantities.get(0).food.name);
        assertEquals(0.7, foodsAndQuantities.get(0).quantity);
    }

    private FoodTarget createPersonalRatingAndCaloriesAndPriceFoodTarget() {
        return new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 1, 0.01, 0.9, 500, 1, 3600, 0.9, 1,
                NormalVeganVegetarian.NORMAL, 0);
    }

    @Test
    public void testPersonalRatingAndCaloriesAndPriceCalculation() throws IOException {
        Person person = createPerson();
        FoodTarget foodTarget = createPersonalRatingAndCaloriesAndPriceFoodTarget();
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = readFoods();

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods, Collections.emptySet());
        assertEquals("duck, meat only, raw", foodsAndQuantities.get(0).food.name);
        assertEquals(0.3, foodsAndQuantities.get(0).quantity);
        assertEquals("pasta, dry", foodsAndQuantities.get(1).food.name);
        assertEquals(0.25, foodsAndQuantities.get(1).quantity);
        assertEquals("milk, 3.25% milkfat", foodsAndQuantities.get(2).food.name);
        assertEquals(0.25, foodsAndQuantities.get(2).quantity);
        assertEquals("potato", foodsAndQuantities.get(3).food.name);
        assertEquals(0.25, foodsAndQuantities.get(3).quantity);
        assertEquals("salt, iodized", foodsAndQuantities.get(4).food.name);
        assertEquals(0.1, foodsAndQuantities.get(4).quantity);
    }


    @Test
    public void testProhibitedFoodCalculation() throws IOException {
        Person person = createPerson();
        FoodTarget foodTarget = createPersonalRatingAndCaloriesAndPriceFoodTarget();
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = readFoods();
        long potatoId = -1;
        for (Food food : foods) {
            if ("potato".equals(food.name)) {
                food.id = potatoId;
            }
        }
        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods, Collections.singleton(potatoId));
        assertEquals("chicken breast, braised", foodsAndQuantities.get(0).food.name);
        assertEquals(0.2, foodsAndQuantities.get(0).quantity);
        assertEquals("bread, white", foodsAndQuantities.get(1).food.name);
        assertEquals(0.35, foodsAndQuantities.get(1).quantity);
        assertEquals("milk, 3.25% milkfat", foodsAndQuantities.get(2).food.name);
        assertEquals(0.25, foodsAndQuantities.get(2).quantity);
        assertEquals("banana", foodsAndQuantities.get(3).food.name);
        assertEquals(0.2, foodsAndQuantities.get(3).quantity);
    }
}
