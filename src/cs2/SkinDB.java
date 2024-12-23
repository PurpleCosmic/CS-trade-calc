package cs2;

import cs2.skins.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SkinDB {
    private static final Path path = Path.of("src/cs2/skinsdb.csv");

    private static final Map<String, SkinInfo> skinDB = init();

    private static SkinInfo convertToInfo(String line) {
        List<String> values = Arrays.asList(line.split(","));
        String name = values.get(0);
        float minFloat = Float.parseFloat(values.get(1));
        float maxFloat = Float.parseFloat(values.get(2));
        SkinCollection collection = SkinCollection.valueOf(values.get(3));
        Grade grade = Grade.valueOf(values.get(4));
        EnumMap<Condition, Float> prices = new EnumMap<>(Condition.class);
        prices.put(Condition.FACTORY_NEW, Float.parseFloat(values.get(5)));
        prices.put(Condition.MINIMAL_WEAR, Float.parseFloat(values.get(6)));
        prices.put(Condition.FIELD_TESTED, Float.parseFloat(values.get(7)));
        prices.put(Condition.WELL_WORN, Float.parseFloat(values.get(8)));
        prices.put(Condition.BATTLE_SCARRED, Float.parseFloat(values.get(9)));

        EnumMap<Condition, Float> statPrices = new EnumMap<>(Condition.class);
        statPrices.put(Condition.FACTORY_NEW, Float.parseFloat(values.get(10)));
        statPrices.put(Condition.MINIMAL_WEAR, Float.parseFloat(values.get(11)));
        statPrices.put(Condition.FIELD_TESTED, Float.parseFloat(values.get(12)));
        statPrices.put(Condition.WELL_WORN, Float.parseFloat(values.get(13)));
        statPrices.put(Condition.BATTLE_SCARRED, Float.parseFloat(values.get(14)));

        boolean isAvailable = false;
        for (float price : prices.values()) {
            if (price != 0) {
               isAvailable = true;
               break;
           }
        }

        if (isAvailable) {
            return new SkinInfo(name, minFloat, maxFloat, collection, grade, prices, statPrices);
        } else {
            return null;
        }
    }

    private static Map<String, SkinInfo> init() {
        Map<String, SkinInfo> map = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                SkinInfo info = convertToInfo(line);
                if (info != null) {
                    map.put(info.name(), info);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static Map<String, SkinInfo> get() {
        return skinDB;
    }

    public static Optional<SkinInfo> getCheapestOption(SkinCollection collection, Grade grade, Condition condition, boolean statTrack) {
        float cheapestPrice = 99999999999999999999999999999999999f;
        Optional<SkinInfo> cheapestSkin = Optional.empty();
        for (SkinInfo info : get().values()) {
            if (info.collection() == collection && info.grade() == grade) {
                float value = statTrack ? info.stattrackValues().get(condition) : info.values().get(condition);
                if (cheapestSkin.isEmpty() || cheapestPrice > value) {
                    cheapestPrice = value;
                    cheapestSkin = Optional.of(info);
                }
            }
        }
        return cheapestSkin;
    }

    public static void main(String[] args) {
        System.out.println(get());
    }
}
