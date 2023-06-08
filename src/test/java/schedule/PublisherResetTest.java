package schedule;

import org.junit.jupiter.api.BeforeEach;

public class PublisherResetTest extends PublisherTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(publisher.proceed());
        publisher.reset();
        eventList.clear();
    }
}
