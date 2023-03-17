import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Schedule;
import schedule.Schedules;

import static com.google.common.truth.Truth.assertThat;

public class ScheduleTest {
    private Schedule s;
    private Model model;

    @BeforeEach
    void setUp() {
        s = Schedules.step(()-> model.age = 5);
        model = new Model();
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
        model.age = 0;
        s.reset();
        assertThat(s.proceed()).isTrue();
        while(s.proceed());
        assertThat(s.proceed()).isFalse();
        assertThat(model.age).isEqualTo(5);
    }

    private static class Model{
        public int age = 0;
        public String name = "John";
        public String surname = "Smith";
    }
}
