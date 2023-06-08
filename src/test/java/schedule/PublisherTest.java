package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Event;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class PublisherTest {
    Schedule publisher;
    Event myEvent;
    List<Event> eventList;

    public void receiveEvent(Event event){
        eventList.add(event);
    }

    @BeforeEach
    protected void setUp() {
        eventList = new ArrayList<>();
        myEvent = new Event() {};
        publisher = new Publisher(this::receiveEvent, myEvent);
    }

    @Test
    public void proceedSendsEvent() {
        publisher.proceed();
        assertThat(eventList).containsExactly(myEvent);
    }

    @Test
    public void proceedingTwiceDoesNotSendTwoEvents() {
        publisher.proceed();
        assertThat(publisher.proceed()).isFalse();
        assertThat(eventList).hasSize(1);
    }
}