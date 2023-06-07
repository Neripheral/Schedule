package schedule;

import org.junit.jupiter.api.BeforeEach;

public class EventfulResetTest extends EventfulTest{
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(eventful.proceed());
        eventful.reset();
        eventList.clear();
    }
}
