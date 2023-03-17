import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Schedule;
import schedule.Schedules;

import static com.google.common.truth.Truth.assertThat;

public class StepTest {
    private Schedule s;
    private ScheduleModel model;

    @BeforeEach
    void setUp() {
        s = Schedules.step(()-> model.age = 5);
        model = new ScheduleModel(20, "John", "Smith");
    }

    @Test
    void stepExecutesCorrectly() {
        while(s.proceed());
        assertThat(model.age).isEqualTo(5);
    }

    @Test
    void stepLifeEndsAfterExecution() {
        assertThat(s.proceed()).isTrue();
        assertThat(s.proceed()).isFalse();
    }

    @Test
    void stepIsUsableAgainAfterResetMethodIsCalled() {
        while(s.proceed());
        assertThat(s.proceed()).isFalse();
        model.age = 20;
        s.reset();
        assertThat(s.proceed()).isTrue();
        while(s.proceed());
        assertThat(s.proceed()).isFalse();
        assertThat(model.age).isEqualTo(5);
    }

}
