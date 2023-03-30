package schedule;

import java.util.Objects;
import java.util.function.BooleanSupplier;

class Fork implements Schedule{
    public Fork(BooleanSupplier condition, Schedule scheduleIfPassed, Schedule scheduleIfFailed){
        this.condition = Objects.requireNonNull(condition);
        this.scheduleIfPassed = Objects.requireNonNull(scheduleIfPassed);
        this.scheduleIfFailed = Objects.requireNonNull(scheduleIfFailed);
    }

    @Override
    public boolean proceed() {
        if(!isConditionChecked) {
            isRequirementPassed = condition.getAsBoolean();
            isConditionChecked = true;
        }
        if(isRequirementPassed)
            return scheduleIfPassed.proceed();
        else
            return scheduleIfFailed.proceed();
    }

    @Override
    public void reset() {
        scheduleIfPassed.reset();
        scheduleIfFailed.reset();
        isConditionChecked = false;
    }

    private boolean isConditionChecked = false;
    private boolean isRequirementPassed = false;
    private final BooleanSupplier condition;
    private final Schedule scheduleIfPassed;
    private final Schedule scheduleIfFailed;
}
