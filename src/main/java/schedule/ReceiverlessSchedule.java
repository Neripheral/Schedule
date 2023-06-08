package schedule;

import java.util.function.Function;

class ReceiverlessSchedule {
    private final Function<EventReceiver, Schedule> builder;

    public ReceiverlessSchedule(Function<EventReceiver, Schedule> builder) {
        this.builder = builder;
    }

    public Schedule forReceiver(EventReceiver eventReceiver){
        return builder.apply(eventReceiver);
    }
}
