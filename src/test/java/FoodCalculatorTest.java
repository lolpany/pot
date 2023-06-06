import lol.lolpany.pot.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FoodCalculatorTest {

    @Test
    public void testFoodCalculator() throws IOException {
        Person person = new Person(Sex.MALE, 1986, 0.17, 70, ActivityLevel.SEDENTARY);
        FoodTarget foodTarget = new FoodTarget(WeightTarget.STAY_SAME, 1600.0, 0.8, 0.9, 3000, 0.8, 3600, 0.9, 0,
                NormalVeganVegetarian.NORMAL, 0);
        FoodCalculator foodCalculator = new FoodCalculator();

        List<Food> foods = new ArrayList<>();
        Reader fileReader = new FileReader("O:\\projects\\pot\\src\\test\\resources\\food.tsv");
        Iterable<CSVRecord> records = CSVFormat.TDF.builder().setHeader().build().parse(fileReader);
        long id = 0;
        for (CSVRecord record : records) {
            Food food = new Food(id++, record.get("name"));
            food.setProteins(Double.parseDouble(record.get("proteins")));
            food.setFats(Double.parseDouble(record.get("fats")));
            food.setCarbohydrates(Double.parseDouble(record.get("carbohydrates")));
            foods.add(food);
        }

        List<FoodAndQuantity> foodsAndQuantities = foodCalculator.calculate(person, foodTarget, foods);
    }

}
