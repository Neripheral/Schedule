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
        model = new Model();
    }

    @Test
    public void singleStepScheduleExecutesCorrectly() {
        assertThat(model.age).isNotEqualTo(5);
        s = Schedules.step(()-> model.age = 5);
        assertThat(s.proceed()).isTrue();
        assertThat(model.age).isEqualTo(5);
        assertThat(s.proceed()).isFalse();
    }



    private static class Model{
        public int age = 0;
        public String name = "John";
        public String surname = "Smith";
    }
}
