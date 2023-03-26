package schedule;

import java.util.Arrays;

public enum Schedules {;
    public static Schedule step(Runnable procedure){
        if(procedure == null)
            return EmptyStep.INSTANCE;
        return new Step(procedure);
    }

    public static Schedule list(Schedule...schedules){
        if(schedules.length == 0)
            return EmptyStep.INSTANCE;
        Schedule secondSchedule = list(Arrays.copyOfRange(schedules, 1, schedules.length));
        return new Duo(schedules[0], secondSchedule);
    }

    public static Schedule list(Runnable...runnables){
        Schedule[] schedules =
                Arrays.stream(runnables)
                        .map(Schedules::step)
                        .toArray(Schedule[]::new);
        return list(schedules);
    }
}
