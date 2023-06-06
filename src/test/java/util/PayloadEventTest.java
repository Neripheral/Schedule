package util;

import static com.google.common.truth.Truth.assertThat;
import static util.PayloadEventTest.TestEvent.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayloadEventTest {
    static class TestEvent extends PayloadEvent<Payload>{
        public TestEvent(String name, int age) {
            super(new Payload(name, age));
        }

        public static class Payload{
            public Payload(String name, int age) {
                this.name = name;
                this.age = age;
            }

            public final String name;
            public final int age;
        }
    }

    Event payloadEvent;

    @BeforeEach
    void setUp() {
        payloadEvent = new TestEvent("James", 20);
    }

    @Test
    void answersTrueWhenAskedIfHasPayload() {
        assertThat(payloadEvent.hasPayload()).isTrue();
    }

    @Test
    void returnsCorrectPayloadWhenAskedForPayload() {
        assertThat(((TestEvent) payloadEvent).getPayload().name).isEqualTo("James");
        assertThat(((TestEvent) payloadEvent).getPayload().age).isEqualTo(20);
    }
}