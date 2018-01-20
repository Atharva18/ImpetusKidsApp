package com.example.parij.myschoolcomm;

/**
 * Created by Ankush on 15-01-2018.
 */

public class Constants {
    public static final int FATHER = 1;
    public static final int MOTHER = 2;
    public static final int GUARDIAN = 3;

    //Programs
    public static final int ALLPROGRAMS = 18;
    public static final int DAYCARE = 4;
    public static final int SEEDING = 5;
    public static final int BUDDING = 6;
    public static final int BLOSSOMING = 7;
    public static final int FLOURISHING = 8;

    //Batchs
    public static final int MORNING = 9;
    public static final int AFTERNOON = 10;

    //Blood group
    public static final int APositive = 11;
    public static final int BPositive = 12;
    public static final int AB = 13;
    public static final int ANegative = 14;
    public static final int BNegative = 15;
    public static final int OPositive = 16;
    public static final int ONegative = 17;

    //FB DB
    public static final String FBDB = "newDb";

    //Gender
    public static final int MALE = 18;
    public static final int FEMALE = 19;

    static String getProgramName(int program) {
        switch (program) {
            case 4:
                return "Day-Care";
            case 5:
                return "Seeding";
            case 6:
                return "Budding";
            case 7:
                return "Blossoming";
            case 8:
                return "Flourishing";
        }
        return "";
    }
}
