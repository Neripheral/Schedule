package schedule;

import java.util.Arrays;

public enum Schedules {;
    public static Schedule step(Runnable procedure){
        if(procedure == null)
            return EmptyStep.INSTANCE;
        return Step.of(procedure);
    }

    public static Schedule list(Schedule...schedules){
        if(schedules.length == 0)
            return EmptyStep.INSTANCE;
        return Duo.of(schedules[0],
                list(Arrays.copyOfRange(schedules, 1, schedules.length)));
    }
}
