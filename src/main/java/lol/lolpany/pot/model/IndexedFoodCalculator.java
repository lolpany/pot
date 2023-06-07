package lol.lolpany.pot.model;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class IndexedFoodCalculator {

    private final double MAX_FOOD_QUANTITY = 1;
    private final int MAX_QUANTITY_MULTIPLIER = 5;
    private final double QUANTITY_STEP = MAX_FOOD_QUANTITY / MAX_QUANTITY_MULTIPLIER;

    public List<FoodAndQuantity> calculate(Person person, FoodTarget foodTarget, List<Food> foods) throws IOException {
        // for performance
        person.age = Year.now().getValue() - person.birthYear;
        List<FoodAndQuantity> result = new ArrayList<>();
        double maxScore = -Double.MAX_VALUE;
        double currentScore;
        List<FoodAndQuantity> foodsAndQuantities = initFoodsQuantities(foods);
        double overallQuantity = 0;
        double numberOfCombinations = Math.pow(MAX_QUANTITY_MULTIPLIER, foods.size());
        List<String> indexLines = FileUtils.readLines(new File("C:\\all\\projects\\pot\\src\\main\\resources\\index-partial.txt"), StandardCharsets.UTF_8);
        for (String indexLine : indexLines) {
            String[] indexLineParts = indexLine.split(";");

            for (int i = 0; i < indexLineParts.length / 2; i = i + 2) {
                foodsAndQuantities.get(Integer.parseInt(indexLineParts[i])).quantity = Double.parseDouble(indexLineParts[i+1]);
            }

//            String indexString = "";
//            int a = 0;
//            for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
//                indexString += a + ";" + foodAndQuantity.quantity + ";";
//                a++;
//            }
//            FileUtils.write(new File("C:\all\projects\pot\src\main\resources\index.txt"), indexString + "\n", StandardCharsets.UTF_8, true);


            currentScore = calculateScore(person, foodTarget, foodsAndQuantities);
            if (currentScore > maxScore) {
                maxScore = currentScore;
                result = copyFoods(foodsAndQuantities);
            }
        }
        return result;
    }

    private List<FoodAndQuantity> initFoodsQuantities(List<Food> foods) {
        List<FoodAndQuantity> result = new ArrayList<>(foods.size());
        for (Food food : foods) {
            result.add(new FoodAndQuantity(food, 0));
        }
        return result;
    }

    // todo
    private double calculateScore(Person person, FoodTarget foodTarget, List<FoodAndQuantity> foodsAndQuantities) {
        double calScore = new CalCalculator().calculate(person, foodTarget.weightTarget, foodTarget.kcal, foodsAndQuantities);
        double priceScore = new PriceCalculator().calculate(foodTarget.price, foodsAndQuantities);
        return foodTarget.calImportance * calScore + foodTarget.priceImportance * priceScore;
    }

    private List<FoodAndQuantity> copyFoods(List<FoodAndQuantity> foodsAndQuantities) {
        List<FoodAndQuantity> result = new ArrayList<>();
        for (FoodAndQuantity foodAndQuantity : foodsAndQuantities) {
            if (foodAndQuantity.quantity > 0) {
                result.add(new FoodAndQuantity(foodAndQuantity.food, foodAndQuantity.quantity));
            }
        }
        return result;
    }

}
