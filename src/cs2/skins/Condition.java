package cs2.skins;

import java.util.EnumMap;
import java.util.Map;

public enum Condition {
    FACTORY_NEW,
    MINIMAL_WEAR,
    FIELD_TESTED,
    WELL_WORN,
    BATTLE_SCARRED;

    private static final Map<Float, Condition> floatMap = Map.of(
            0.07f, FACTORY_NEW,
            0.15f, MINIMAL_WEAR,
            0.37f, FIELD_TESTED,
            0.44f, WELL_WORN,
            1.0f, BATTLE_SCARRED
    );

    public static Condition get(float floatValue) {
        for (float max : floatMap.keySet().stream().sorted().toList()) {
            if (floatValue <= max) {
                return floatMap.get(max);
            }
        }
        return BATTLE_SCARRED;
    }
}
