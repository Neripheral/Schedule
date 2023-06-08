package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Event;

import static com.google.common.truth.Truth.assertThat;

public class ReceiverlessScheduleTest {
    ReceiverlessSchedule receiverlessSchedule;
    Event receivedEvent;

    @BeforeEach
    protected void setUp() {
        receivedEvent = null;
        receiverlessSchedule = new ReceiverlessSchedule(er->S.event(er, new Event(){}));
    }

    @Test
    public void canSuccessfullyBindReceiver() {
        Schedule receiverfulSchedule = receiverlessSchedule.forReceiver(e->receivedEvent = e);
        receiverfulSchedule.proceed();

        assertThat(receivedEvent).isNotNull();
    }
}
