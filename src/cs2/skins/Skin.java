package cs2.skins;

import java.util.EnumMap;

public record Skin(SkinInfo info, float floatValue) {
    public Condition getCondition() {
        return Condition.get(floatValue);
    }
}
