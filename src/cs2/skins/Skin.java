package cs2.skins;

import java.util.EnumMap;

public record Skin(SkinInfo info, float floatValue, boolean statTrack) {
    public Condition getCondition() {
        return Condition.get(floatValue);
    }

    public float getValue() {
        return statTrack ? info().stattrackValues().get(getCondition()) : info.values().get(getCondition());
    }

    @Override
    public String toString() {
        return "Skin[name="+info.name()+", StatTrack="+statTrack+", Condition="+getCondition()+", floatValue="+floatValue+"]";
    }
}
