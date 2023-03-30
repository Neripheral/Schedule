package schedule;

import java.util.Objects;

class Duo implements Schedule{
    private final Schedule firstSchedule;
    private final Schedule secondSchedule;

    public Duo(Schedule firstSchedule, Schedule secondSchedule) {
        this.firstSchedule = Objects.requireNonNull(firstSchedule);
        this.secondSchedule = Objects.requireNonNull(secondSchedule);
    }

    @Override
    public boolean proceed() {
        return firstSchedule.proceed() || secondSchedule.proceed();
    }

    @Override
    public void reset() {
        firstSchedule.reset();
        secondSchedule.reset();
    }
}
