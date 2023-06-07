package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Event;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class EventfulTest {
    Schedule eventful;
    Event myEvent;
    List<Event> eventList;

    public void receiveEvent(Event event){
        eventList.add(event);
    }

    @BeforeEach
    protected void setUp() {
        eventList = new ArrayList<>();
        myEvent = new Event() {};
        eventful = new Eventful(this::receiveEvent, myEvent);
    }

    @Test
    public void proceedSendsEvent() {
        eventful.proceed();
        assertThat(eventList).containsExactly(myEvent);
    }
}