package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class StepTest {
    protected Schedule s;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        s = new Step(() -> model.age = 5);
        model = ScheduleModel.getFreshModel();
    }

    @Test
    void stepProceedsCorrectly() {
        assertThat(s.proceed()).isTrue();
        assertThat(model.age).isEqualTo(5);
    }

    @Test
    public void stepLifeEndsAfterExecution() {
        assertThat(s.proceed()).isTrue();
        assertThat(s.proceed()).isFalse();
    }

    @Test public void toStringWithoutDescriptionReturnsCorrectString(){
        assertThat(s.toString()).isEqualTo("[ ] unspecified");
    }

    @Test public void toStringWithDescriptionReturnsCorrectString(){
        s = new Step(() -> model.age = 5, "Set age to 5");
        assertThat(s.toString()).isEqualTo("[ ] Set age to 5");
    }

    @Test public void toStringWithMultilineDescriptionReturnsCorrectString(){
        s = new Step(() -> model.age = 5, "Set age\nto 5");
        assertThat(s.toString()).isEqualTo(
                """
                [ ] Set age
                    to 5""");
    }

    @Test public void toStringCompletedShowsCheckmark(){
        s.proceed();
        assertThat(s.toString()).isEqualTo("[X] unspecified");
    }
}
