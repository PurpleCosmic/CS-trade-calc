package cs2.trade_up;

import cs2.SkinDB;
import cs2.skins.*;

import java.util.*;

public class Calculator {
    public final List<Skin> input;
    public List<SkinInfo> output;

    private Grade outputGrade;
    private final boolean isStatTrack;
    private float averageFloat;
    private float inputValue;
    private float outputAverageValue;


    private float maxProfit = -99999999999f;
    private float minProfit = 9999999999999f;
    public float averageProfit;
    public float chanceForProfit;

    private Map<SkinCollection, Integer> inputBallots;
    private Map<SkinCollection, Integer> outputBallots;
    private int totalBallots;


    public Calculator(List<Skin> input) {
        this.input = input;
        isStatTrack = input.getFirst().statTrack();
        calculate();
    }

    public void calculate() {
        outputGrade = Grade.getNextGrade(input.getFirst().info().grade());
        averageFloat = calculateAverageFloat();
        inputBallots = getInputBallots();
        output = getOutput();
        outputBallots = getOutputAmount();
        totalBallots = calculateTotalBallots();

        inputValue = getInputValue();
        outputAverageValue = calculateOutputStats();
    }

    public int calculateTotalBallots() {
        int res = 0;
        for (SkinCollection collection : inputBallots.keySet()) {
            res += getCollectionBallots(collection);
        }
        return res;
    }

    public void displayResults() {
        System.out.println("=========================================================");
        System.out.println("Input: ");
        System.out.println("------------------------------------");
        for (Skin skin : input) {
            System.out.println(STR."Name: \{skin.info().name()}, Collection: \{skin.info().collection()}, Price: \{skin.getValue()}, Grade: \{skin.info().grade()}, Float: \{skin.floatValue()}, Wear: \{skin.getCondition()}");
        }
        System.out.println("------------------------------------");
        System.out.println("Output: ");
        System.out.println("------------------------------------");
        float totalChance = 0;
        for (SkinInfo skin : output) {
            float outputFloat = skin.getOutputFloat(averageFloat);
            Condition cond = Condition.get(outputFloat);
            totalChance += calculateProbability(skin);
            System.out.println(STR."Name: \{skin.name()}, Collection: \{skin.collection()}, Value: \{skin.getValue(cond, isStatTrack)}, Grade: \{skin.grade()} Float: \{outputFloat}, Wear: \{cond}, Profit: \{skin.getValue(cond, isStatTrack) - inputValue}, Chance: \{calculateProbability(skin) * 100}%");
        }
        System.out.println(totalChance);
        System.out.println("------------------------------------");
        System.out.println("Stats: ");
        System.out.println("------------------------------------");
        System.out.println(STR."Average Float: \{averageFloat}");
        System.out.println(STR."Input Value: $\{inputValue}");
        System.out.println();
        System.out.println(STR."Average Ouput Value: $\{outputAverageValue}");
        System.out.println(STR."Max Profit: $\{maxProfit}");
        System.out.println(STR."Min Profit: $\{minProfit}");
        System.out.println(STR."Average Profit: $\{averageProfit}");
        System.out.println(STR."Chance For Profit: \{chanceForProfit * 100}%");
        System.out.println("=================================================");
    }

    private float calculateOutputStats() {
        float res = 0f;
        for (SkinInfo skin : output) {
            float outputFloat = skin.getOutputFloat(averageFloat);
            Condition condition = Condition.get(outputFloat);
            res += calculateProbability(skin) * skin.getValue(condition, isStatTrack);

            float profit = skin.getValue(condition, isStatTrack) - inputValue;
            if (profit > 0) {
                chanceForProfit += calculateProbability(skin);
            }
            if (profit > maxProfit) {
                maxProfit = profit;
            }
            if (profit < minProfit) {
                minProfit = profit;
            }
        }
        averageProfit = res - inputValue;
        return res;
    }

    public float getInputValue() {
        float res = 0;
        for (Skin skin : input) {
            res += skin.getValue();
        }
        return res;
    }

    public Map<SkinCollection, Integer> getInputBallots() {
        Map<SkinCollection, Integer> ballots = new HashMap<>();
        for (Skin skin : input) {
            ballots.putIfAbsent(skin.info().collection(), 0);
            ballots.put(skin.info().collection(), ballots.get(skin.info().collection()) + 1);
        }
        return ballots;
    }

    public List<SkinInfo> getOutput() {
        List<SkinInfo> l = new ArrayList<>();
        for (SkinInfo skin : SkinDB.get().values()) {
            if (inputBallots.containsKey(skin.collection()) && skin.grade() == outputGrade) {
                l.add(skin);
            }
        }
        return l;
    }

    public Map<SkinCollection, Integer> getOutputAmount() {
        Map<SkinCollection, Integer> amounts = new HashMap<>();
        for (SkinInfo skin : output) {
            amounts.putIfAbsent(skin.collection(), 0);
            amounts.put(skin.collection(), amounts.get(skin.collection()) + 1);
        }
        return amounts;
    }

    public int getCollectionBallots(SkinCollection collection) {
        return outputBallots.get(collection) * inputBallots.get(collection);
    }

    public float calculateAverageFloat() {
        OptionalDouble avg = input.stream().mapToDouble(Skin::floatValue).average();
        if (avg.isPresent()) {
            return (float) avg.getAsDouble();
        } else {
            return 0f;
        }
    }

    public float calculateProbability(SkinInfo info) {
        return (inputBallots.get(info.collection())) /(float)totalBallots;
    }
}
