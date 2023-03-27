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
        if(isDone) // end if repetition was halted
            return false;
        if(schedule.proceed()) // proceed with schedule if said schedule hasn't been finished yet
            return true;
        if(repeatCondition.getAsBoolean()) { // on schedule finished check if it should be repeated
            schedule.reset();
            return schedule.proceed();
        }
        // otherwise halt the repetition
        isDone = true;
        return false;
    }

    @Override
    public void reset() {
        isDone = false;
        schedule.reset();
    }
}
