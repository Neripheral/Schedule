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
}
