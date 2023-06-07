package lol.lolpany.pot.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodCalculatorTest {

    @Test
    public void testFoodCalculatorWithCalories() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 0.8, 0.9, 100, 0, 3600, 0.9, 0,
                NormalVeganVegetarian.NORMAL, 0);
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = new ArrayList<>();
        Reader fileReader = new FileReader("C:\\all\\projects\\pot\\src\\test\\resources\\food.tsv");
        Iterable<CSVRecord> records = CSVFormat.TDF.builder().setHeader().build().parse(fileReader);
        long id = 0;
        for (CSVRecord record : records) {
            Food food = new Food(id++, record.get("name"));
            food.setProteins(Double.parseDouble(record.get("proteins")));
            food.setFats(Double.parseDouble(record.get("fats")));
            food.setCarbohydrates(Double.parseDouble(record.get("carbohydrates")));
            food.setPrice(Double.parseDouble(record.get("price")));
            foods.add(food);
        }

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods);
        assertEquals("chicken breast", foodsAndQuantities.get(0).food.name);
        assertEquals(0.35, foodsAndQuantities.get(0).quantity);
        assertEquals("macaroni", foodsAndQuantities.get(1).food.name);
        assertEquals(0.1, foodsAndQuantities.get(1).quantity);
        assertEquals("bread", foodsAndQuantities.get(2).food.name);
        assertEquals(0.3, foodsAndQuantities.get(2).quantity);
        assertEquals("pizza", foodsAndQuantities.get(3).food.name);
        assertEquals(0.05, foodsAndQuantities.get(3).quantity);
        assertEquals("eggs", foodsAndQuantities.get(4).food.name);
        assertEquals(0.05, foodsAndQuantities.get(4).quantity);
    }

    @Test
    public void testFoodCalculatorWithPrice() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 0.8, 0.9, 100, 0.9, 3600, 0.9, 0,
                NormalVeganVegetarian.NORMAL, 0);
        IndexedFoodCalculator foodCalculator = new IndexedFoodCalculator();

        List<Food> foods = new ArrayList<>();
        Reader fileReader = new FileReader("C:\\all\\projects\\pot\\src\\test\\resources\\food.tsv");
        Iterable<CSVRecord> records = CSVFormat.TDF.builder().setHeader().build().parse(fileReader);
        long id = 0;
        for (CSVRecord record : records) {
            Food food = new Food(id++, record.get("name"));
            food.setProteins(Double.parseDouble(record.get("proteins")));
            food.setFats(Double.parseDouble(record.get("fats")));
            food.setCarbohydrates(Double.parseDouble(record.get("carbohydrates")));
            food.setPrice(Double.parseDouble(record.get("price")));
            foods.add(food);
        }

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods);
        assertEquals("chicken breast", foodsAndQuantities.get(0).food.name);
        assertEquals(0.2, foodsAndQuantities.get(0).quantity);
        assertEquals("macaroni", foodsAndQuantities.get(1).food.name);
        assertEquals(0.05, foodsAndQuantities.get(1).quantity);
        assertEquals("bread", foodsAndQuantities.get(2).food.name);
        assertEquals(0.39999999999999997, foodsAndQuantities.get(2).quantity);
        assertEquals("eggs", foodsAndQuantities.get(3).food.name);
        assertEquals(0.05, foodsAndQuantities.get(3).quantity);
    }

}
