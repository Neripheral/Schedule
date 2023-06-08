package schedule;

import util.Event;

class Publisher implements Schedule{
    private final Schedule schedule;

    public Publisher(EventReceiver receiver, Event event){
        schedule = new Step(()->receiver.sendEvent(event));
    }

    @Override
    public boolean proceed() {
        return schedule.proceed();
    }

    @Override
    public void reset() {
        schedule.reset();
    }

    public interface EventReceiver{
        void sendEvent(Event event);
    }
}
