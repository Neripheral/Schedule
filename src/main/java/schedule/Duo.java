package schedule;

public class Duo implements Schedule{
    private final Schedule firstSchedule;
    private final Schedule secondSchedule;

    public Duo(Schedule firstSchedule, Schedule secondSchedule) {
        this.firstSchedule = firstSchedule;
        this.secondSchedule = secondSchedule;
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
