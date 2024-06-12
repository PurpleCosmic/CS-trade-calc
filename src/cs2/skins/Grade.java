package cs2.skins;

import java.util.List;
import java.util.Map;
import java.util.Random;

public enum Grade {
    COVERT,
    CLASSIFIED,
    RESTRICTED,
    MIL_SPEC,
    INDUSTRIAL,
    CONSUMER,
    CONTRABAND;

    public static final List<Grade> gradeOrder = List.of(
            CONSUMER, INDUSTRIAL, MIL_SPEC, RESTRICTED, CLASSIFIED, COVERT

    );

    public static Grade getNextGrade(Grade grade) {
        int index = gradeOrder.indexOf(grade)+1;
        return gradeOrder.get(index);
    }

    public static Grade getRandomUpgradable() {
        return gradeOrder.get(new Random().nextInt(gradeOrder.size()-1));
    }
}
