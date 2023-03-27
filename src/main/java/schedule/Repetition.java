package schedule;

import java.util.function.BooleanSupplier;

public class Repetition implements Schedule {
    private boolean isDone = false;
    private final Schedule schedule;
    private final BooleanSupplier repeatCondition;

    public Repetition(BooleanSupplier repeatCondition, Schedule schedule) {
        this.schedule = schedule;
        this.repeatCondition = repeatCondition;
    }

    @Override
    public boolean proceed() {
        if(isDone)
            return false;
        if(repeatCondition.getAsBoolean())
            return schedule.proceed();
        isDone = true;
        return false;
    }

    @Override
    public void reset() {
        isDone = false;
        schedule.reset();
    }
}
