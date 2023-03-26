import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Schedule;
import schedule.Step;

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
    public void stepLifeEndsAfterExecution() {
        assertThat(s.proceed()).isTrue();
        assertThat(s.proceed()).isFalse();
    }
}
