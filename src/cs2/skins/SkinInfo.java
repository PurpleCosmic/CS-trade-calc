package cs2.skins;

import java.util.EnumMap;
import java.util.Objects;

public class SkinInfo {
    private final String name;
    private final float minFloat;
    private final float maxFloat;
    private final SkinCollection collection;
    private final Grade grade;
    private final EnumMap<Condition, Float> values;
    private final EnumMap<Condition, Float> stattrackValues;

    public SkinInfo(
            String name,
            float minFloat,
            float maxFloat,
            SkinCollection collection,
            Grade grade,
            EnumMap<Condition, Float> values,
            EnumMap<Condition, Float> stattrackValues
    ) {
        this.name = name;
        this.minFloat = minFloat;
        this.maxFloat = maxFloat;
        this.collection = collection;
        this.grade = grade;
        this.values = values;
        this.stattrackValues = stattrackValues;
    }

    public float getOutputFloat(float average) {
        return (maxFloat - minFloat) * average + minFloat;
    }

    public String name() {
        return name;
    }

    public float minFloat() {
        return minFloat;
    }

    public float maxFloat() {
        return maxFloat;
    }

    public SkinCollection collection() {
        return collection;
    }

    public Grade grade() {
        return grade;
    }

    public EnumMap<Condition, Float> values() {
        return values;
    }

    public EnumMap<Condition, Float> stattrackValues() {
        return stattrackValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SkinInfo) obj;
        return Objects.equals(this.name, that.name) &&
                Float.floatToIntBits(this.minFloat) == Float.floatToIntBits(that.minFloat) &&
                Float.floatToIntBits(this.maxFloat) == Float.floatToIntBits(that.maxFloat) &&
                Objects.equals(this.collection, that.collection) &&
                Objects.equals(this.grade, that.grade) &&
                Objects.equals(this.values, that.values) &&
                Objects.equals(this.stattrackValues, that.stattrackValues);
    }

    public float getValue(Condition condition) {
        return values().get(condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minFloat, maxFloat, collection, grade, values, stattrackValues);
    }
    @Override
    public String toString() {
        return "Skin[" +
                "name=" + name + ", " +
                "minFloat=" + minFloat + ", " +
                "maxFloat=" + maxFloat + ", " +
                "collection=" + collection + ", " +
                "grade=" + grade + ", " +
                "values=" + values + ", " +
                "stattrackValues=" + stattrackValues + ']';
    }

}
