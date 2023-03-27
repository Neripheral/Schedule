package schedule;

import java.util.function.BooleanSupplier;

public class Repetition implements Schedule {
    private boolean isDone = false;
    private boolean isOngoingSchedule = false;
    private final Schedule schedule;
    private final BooleanSupplier repeatCondition;

    public Repetition(BooleanSupplier repeatCondition, Schedule schedule) {
        this.schedule = schedule;
        this.repeatCondition = repeatCondition;
    }

    @Override
    public boolean proceed() {
        // if repetition is already halted then return false
        if(isDone)
            return false;

        // repeat the schedule if finished or halt depending on the condition
        if(!isOngoingSchedule) {
            if (repeatCondition.getAsBoolean()) {
                schedule.reset();
                isOngoingSchedule = true;
            } else {
                isDone = true;
                return false;
            }
        }

        // proceed with ongoing schedule
        if (schedule.proceed())
            return true;
        else {
            isOngoingSchedule = false;
            return this.proceed();
        }
    }

    @Override
    public void reset() {
        isDone = false;
        schedule.reset();
    }
}
