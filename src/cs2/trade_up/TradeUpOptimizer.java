package cs2.trade_up;

import cs2.SkinDB;
import cs2.skins.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeUpOptimizer {
    public static List<Skin> getRandomSkins() {
        List<Skin> skins = new ArrayList<>();
        Grade grade = Grade.getRandomUpgradable();

        for (int i = 0; i < 10; i++) {
            skins.add(getRandomSkin(grade));
        }
        return skins;
    }

    private static float getRandomFloat(SkinInfo info) {
        float min = info.minFloat();
        float max = info.maxFloat();

        float rand = new Random().nextFloat();

        return (max - min) * rand + min;
    }

    public static Skin getRandomSkin(Grade grade) {
        SkinInfo info;
        float floatValue = 0;
        boolean isValid = false;
        do {
            do {
                info = SkinDB.get().values().stream().toList().get(new Random().nextInt(SkinDB.get().size()));
            } while (info.grade() != grade || ! hasOutput(Grade.getNextGrade(grade), info.collection()));
            for (int i = 0; i < 10 && !isValid; i++) {
                floatValue = getRandomFloat(info);
                if (info.getValue(Condition.get(floatValue)) != 0) {
                    isValid = true;
                }
            }
        } while (! isValid);

        return new Skin(info, floatValue);
    }

    public static boolean hasOutput(Grade grade, SkinCollection collection) {
        for (SkinInfo skin : SkinDB.get().values()) {
            if (skin.grade() == grade && skin.collection() == collection) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(SkinDB.get());
        boolean gotProfitable = false;

        float maxProf = -999999999;
        Calculator maxProfCalc = null;
        float maxChance = -1;
        Calculator maxChanceCalc = null;

        for (int i = 0; i < 100000; i++) {
            Calculator calc = new Calculator(getRandomSkins());
            if (calc.averageProfit > maxProf) {
                maxProf = calc.averageProfit;
                maxProfCalc = calc;
            }
            if (calc.chanceForProfit > maxChance) {
                maxChance = calc.chanceForProfit;
                maxChanceCalc = calc;
            }

            if (calc.averageProfit >= 0 || calc.chanceForProfit > 0.6 ) {
                gotProfitable = true;
                calc.displayResults();
            }
        }
        System.out.println(maxProf + " | " + maxChance);

        if (maxProfCalc != null) {
            maxProfCalc.displayResults();
        }
        if (maxChanceCalc != null) {
            maxChanceCalc.displayResults();
        }

    }
}
