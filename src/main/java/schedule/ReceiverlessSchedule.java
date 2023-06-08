package schedule;

import java.util.function.Function;

class ReceiverlessSchedule {
    private final Function<S.EventReceiver, Schedule> builder;

    public ReceiverlessSchedule(Function<S.EventReceiver, Schedule> builder) {
        this.builder = builder;
    }

    public Schedule forReceiver(S.EventReceiver eventReceiver){
        return builder.apply(eventReceiver);
    }
}
