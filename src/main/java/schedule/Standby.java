package schedule;

import java.util.function.BooleanSupplier;

class Standby implements Schedule {
    private final Schedule schedule;
    private final String conditionDescription;
    private boolean isBlocking = false;
    private boolean isDone = false;

    public Standby(BooleanSupplier waitingFor) {
        this(waitingFor, "unspecified");
    }

    public Standby(BooleanSupplier waitingFor, String conditionDescription) {
        schedule =
                new Repetition(
                        ()-> !waitingFor.getAsBoolean(),
                        new Step(()->{})
                );
        this.conditionDescription = conditionDescription;
    }

    @Override
    public boolean proceed() {
        isBlocking = schedule.proceed();
        isDone = !isBlocking;
        return isBlocking;
    }

    @Override
    public void reset() {
        isBlocking = false;
        isDone = false;
        schedule.reset();
    }

    @Override
    public String toString() {
        String doneMarker = " ";
        if(isBlocking)
            doneMarker = "!";
        if(isDone)
            doneMarker = "X";
        return String.format(
               """
               [%s] Wait until %s""",
                doneMarker,
                conditionDescription
                );
    }
}
