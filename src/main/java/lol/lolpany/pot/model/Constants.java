package lol.lolpany.pot.model;

public class Constants {
    public static final int KILOGRAM_COEFFICIENT = 10;
    public static final int NUMBER_OF_ASPECTS = 6;
    public static final int MAX_ASPECT_SCORE = 10;
    public static final int MIN_ASPECT_SCORE = NUMBER_OF_ASPECTS * -MAX_ASPECT_SCORE * 2;
    /**
     * For {@link Constants#PROHIBITIVE_SCORE} to work.
     */
    public static final double MIN_ASPECT_COEFFICIENT = 0.01;
    /**
     * To exclude certain food combinations.
     */
    public static final double PROHIBITIVE_SCORE = -Double.MAX_VALUE;

}
