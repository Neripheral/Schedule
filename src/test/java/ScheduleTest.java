import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Schedule;
import schedule.Schedules;

import java.util.List;

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


    private static class Model{
        public int age = 0;
        public String name = "John";
        public String surname = "Smith";
    }
}
