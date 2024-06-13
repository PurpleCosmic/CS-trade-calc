package cs2.trade_up;

import cs2.SkinDB;
import cs2.skins.*;

import java.util.*;

public class TradeUpOptimizer {
    private final List<Calculator> optimalCalcs;
    private final List<Map<Skin, Integer>> checkedLists;
    private static final Map<Condition, Float> reasonableFloats = Map.of(
            Condition.FACTORY_NEW, 0.03f,
            Condition.MINIMAL_WEAR, 0.10f,
            Condition.FIELD_TESTED, 0.20f,
            Condition.WELL_WORN, 0.40f,
            Condition.BATTLE_SCARRED, 0.55f
    );


    public TradeUpOptimizer() {
        optimalCalcs = new ArrayList<>();
        checkedLists = new ArrayList<>();
    }

    public void getAllOptimal(List<SkinCollection> collections, Grade grade, boolean statTrack) {
        getAllOptimal(collections, grade, statTrack, new ArrayList<>());
    }

    private HashMap<Skin, Integer> convertToMultiset(List<Skin> skins) {
        HashMap<Skin, Integer> map = new HashMap<>();
        for (Skin skin : skins) {
            map.put(skin, 0);
        }
        for (Skin skin : skins) {
            map.put(skin, map.get(skin)+1);
        }
        return map;
    }

    private boolean hasBeenChecked(List<Skin> skins) {
        Map<Skin, Integer> multiset = convertToMultiset(skins);
        for (Map<Skin, Integer> checkedList : checkedLists) {
            if (checkedList.equals(multiset)) {
                return true;
            }
        }
        return false;
    }

    private void getAllOptimal(List<SkinCollection> collections, Grade grade, boolean statTrack, List<Skin> selection) {
        if (selection.size() >= 10) {
            Calculator calc = new Calculator(new ArrayList<>(selection));
            if (calc.averageProfit >= 0 || calc.chanceForProfit >= 60) {
                optimalFound(calc);
            }
        } else if (! hasBeenChecked(selection)) {
            for (SkinCollection collection : collections) {
                for (Condition condition : Condition.values()) {
                    Optional<SkinInfo> cheapestOfCondition = SkinDB.getCheapestOption(collection, grade, condition, statTrack);
                    if (cheapestOfCondition.isPresent()) {
                        float floatValue = getFloat(cheapestOfCondition.get(), condition);
                        Skin skin = new Skin(cheapestOfCondition.get(), floatValue, statTrack);

                        selection.add(skin);
                        getAllOptimal(collections, grade, statTrack, selection);
                        checkedLists.add(convertToMultiset(selection));
                        selection.remove(skin);
                    }
                }
            }
        }
    }

    public float getFloat(SkinInfo skin, Condition condition) {
        return Math.clamp(reasonableFloats.get(condition), skin.minFloat(), skin.maxFloat());
    }

    public void optimalFound(Calculator calculator) {
        calculator.displayResults();
        optimalCalcs.add(calculator);
    }

    public static void main(String[] args) {
        TradeUpOptimizer optimizer = new TradeUpOptimizer();
        optimizer.getAllOptimal(List.of(SkinCollection.values()), Grade.CONSUMER, false);
    }
}
