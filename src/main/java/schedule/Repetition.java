package schedule;

import java.util.function.BooleanSupplier;

class Repetition implements Schedule {
    private boolean isDone = false;
    private boolean isOngoingSchedule = false;
    private final Schedule schedule;
    private final BooleanSupplier repeatCondition;
    private final String conditionDescription;

    public Repetition(BooleanSupplier repeatCondition, Schedule schedule) {
        this.schedule = schedule;
        this.repeatCondition = repeatCondition;
        this.conditionDescription = "unspecified";
    }

    public Repetition(BooleanSupplier repeatCondition, Schedule schedule, String conditionDescription) {
        this.schedule = schedule;
        this.repeatCondition = repeatCondition;
        this.conditionDescription = conditionDescription;
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

    @Override
    public String toString() {
        String prefix =
                isDone ? "X" : " ";
        String body = "|" + schedule.toString().replace("\n", "\n|");
        return String.format(
                "[%s] while(%s):\n" +
                "%s",
                prefix,
                conditionDescription,
                body);
    }
}
