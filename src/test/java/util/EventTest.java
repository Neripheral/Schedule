package util;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {
    public static class TestEvent implements Event {}
    public static class DifferentEvent implements Event {}

    Event event;

    @BeforeEach
    void setUp() {
        event = new TestEvent();
    }

    @Test
    void answersTrueWhenAskedToCompareTypeOnSameClass() {
        assertThat(event.isOfType(TestEvent.class)).isTrue();
    }

    @Test
    void answersFalseWhenAskedToCompareTypeOnDifferentClass() {
        assertThat(event.isOfType(DifferentEvent.class)).isFalse();
    }

    @Test
    void answersFalseWhenAskedToCompareTypeOnNull() {
        assertThat(event.isOfType(null)).isFalse();
    }

    @Test
    void answersFalseIfAskedIfHasPayload() {
        assertThat(event.hasPayload()).isFalse();
    }

    @Test
    void throwsIfAskedForPayload() {
        assertThrows(UnsupportedOperationException.class, ()->event.getPayload());
    }
}