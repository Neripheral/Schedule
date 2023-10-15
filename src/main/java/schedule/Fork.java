package schedule;

import java.util.Objects;
import java.util.function.BooleanSupplier;

class Fork implements Schedule{
    private boolean isConditionChecked = false;
    private boolean isRequirementPassed = false;
    private final BooleanSupplier condition;
    private final Schedule scheduleIfPassed;
    private final Schedule scheduleIfFailed;
    private final String conditionDescription;

    public Fork(BooleanSupplier condition,
                Schedule scheduleIfPassed,
                Schedule scheduleIfFailed){
        this.condition = Objects.requireNonNull(condition);
        this.scheduleIfPassed = Objects.requireNonNull(scheduleIfPassed);
        this.scheduleIfFailed = Objects.requireNonNull(scheduleIfFailed);
        this.conditionDescription = "unspecified";
    }

    public Fork(BooleanSupplier condition,
                Schedule scheduleIfPassed,
                Schedule scheduleIfFailed,
                String conditionDescription){
        this.condition = Objects.requireNonNull(condition);
        this.scheduleIfPassed = Objects.requireNonNull(scheduleIfPassed);
        this.scheduleIfFailed = Objects.requireNonNull(scheduleIfFailed);
        this.conditionDescription = conditionDescription;
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
        isRequirementPassed = false;
    }

    @Override
    public String toString() {
        String checkmark = " ";
        if(isConditionChecked)
            checkmark = "F";
        if(isRequirementPassed)
            checkmark = "T";
        return String.format(
                "if([%s]%s):\n" +
                "|%s\n" +
                "else:\n" +
                "|%s",
                checkmark,
                conditionDescription,
                scheduleIfPassed.toString().replace("\n", "\n|"),
                scheduleIfFailed.toString().replace("\n", "\n|")
        );
    }
}
