package cs2.trade_up;

import cs2.SkinDB;
import cs2.skins.*;

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
        float floatValue;
        boolean isValid = false;
        do {
            do {
                info = SkinDB.get().values().stream().toList().get(new Random().nextInt(SkinDB.get().size()));
            } while (info.grade() != grade || ! hasOutput(Grade.getNextGrade(grade), info.collection()));
            floatValue = getRandomFloat(info);
//            System.out.println(info);
            for (int i = 0; i < 50 && !isValid; i++) {
                if (info.getValue(Condition.get(floatValue)) != 0) {
                    isValid = true;
                }
                floatValue = getRandomFloat(info);
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
        for (int i = 0; i < 200; i++) {
            Calculator calc = new Calculator(getRandomSkins());
            if (calc.averageProfit > 0 || calc.chanceForProfit > 0.8 ) {
                calc.displayResults();
            } else {
                System.out.println("ungood");
            }
        }
    }
}
