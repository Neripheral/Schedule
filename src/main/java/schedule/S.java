package schedule;

import util.Event;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public enum S {;
    public static Schedule perform(Runnable functionToPerform){
        return new Step(Objects.requireNonNull(functionToPerform));
    }

    public static Schedule list(Schedule...schedules){
        if(schedules.length == 0)
            throw new IllegalArgumentException("Array of arguments expected. None supplied.");

        else if(schedules.length == 1)
            return schedules[0];

        Schedule remainingScheduleTree = list(Arrays.copyOfRange(schedules, 1, schedules.length));
        return new Duo(schedules[0], remainingScheduleTree);
    }

    public static Schedule list(Runnable...runnables){
        Schedule[] schedules =
                Arrays.stream(runnables)
                        .map(Step::new)
                        .toArray(Schedule[]::new);
        return list(schedules);
    }

    public static Schedule performIf(BooleanSupplier condition, Schedule scheduleToPerformIfTrue){
        Objects.requireNonNull(condition);
        Objects.requireNonNull(scheduleToPerformIfTrue);

        return performIf(condition, scheduleToPerformIfTrue, EmptyStep.INSTANCE);
    }

    public static Schedule performIf(BooleanSupplier condition, Schedule scheduleToPerformIfTrue, Schedule scheduleToPerformIfFalse){
        Objects.requireNonNull(condition);
        Objects.requireNonNull(scheduleToPerformIfTrue);
        Objects.requireNonNull(scheduleToPerformIfFalse);

        return new Fork(condition, scheduleToPerformIfTrue, scheduleToPerformIfFalse);
    }

    public static Schedule repeatFor(int i, Schedule scheduleToRepeat){
        Objects.requireNonNull(scheduleToRepeat);
        if(i < 0) {
            throw new IllegalArgumentException("Expected i to be positive, got: " + i);
        }

        return new Multiple(i, scheduleToRepeat);
    }

    public static Schedule repeatIfTrue(BooleanSupplier condition, Schedule scheduleToRepeat){
        Objects.requireNonNull(condition);
        Objects.requireNonNull(scheduleToRepeat);

        return new Repetition(condition, scheduleToRepeat);
    }

    public static Schedule waitFor(BooleanSupplier condition){
        Objects.requireNonNull(condition);

        return new Standby(condition);
    }

    public interface EventReceiver extends Eventful.EventReceiver{}

    public static Schedule event(EventReceiver receiver, Event event){
        return new Eventful(receiver, event);
    }

    public static ReceiverlessSchedule receiverlessSchedule(Function<EventReceiver, Schedule> rawSchedule){
        return new ReceiverlessSchedule(rawSchedule);
    }
}
