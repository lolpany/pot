package lol.lolpany.pot.model;

public class Person {
    long id;
    Sex sex;
    int birthYear;
    int age;
    double height;
    double weight;
    ActivityLevel activityLevel;

    public Person(Sex sex, int birthYear, double height, double weight, ActivityLevel activityLevel) {
        this.sex = sex;
        this.birthYear = birthYear;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
    }
}
