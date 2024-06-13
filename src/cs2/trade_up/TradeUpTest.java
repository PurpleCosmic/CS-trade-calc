package cs2.trade_up;

import cs2.SkinDB;
import cs2.skins.Skin;
import cs2.skins.SkinInfo;

import java.util.List;
import java.util.Map;

public class TradeUpTest {
    public static void main(String[] args) {
        Map<String, SkinInfo> db = SkinDB.get();
        List<Skin> input = List.of(
                new Skin(db.get("XM1014 | Oxide Blaze"), 0.035f),
                new Skin(db.get("XM1014 | Oxide Blaze"), 0.035f),
                new Skin(db.get("XM1014 | Oxide Blaze"), 0.035f),
                new Skin(db.get("XM1014 | Oxide Blaze"), 0.035f),
                new Skin(db.get("Dual Berettas | Shred"), 0.09f),
                new Skin(db.get("Dual Berettas | Shred"), 0.09f),
                new Skin(db.get("Dual Berettas | Shred"), 0.09f),
                new Skin(db.get("Dual Berettas | Shred"), 0.09f),
                new Skin(db.get("Dual Berettas | Shred"), 0.09f),
                new Skin(db.get("Dual Berettas | Shred"), 0.09f)
        );

        System.out.println(input);
        Calculator calc = new Calculator(input);
        System.out.println(calc.getInputBallots());
        System.out.println(calc.getOutputAmount());
        calc.displayResults();
    }
}
