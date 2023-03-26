import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Duo;
import schedule.Schedule;
import schedule.Step;

import static com.google.common.truth.Truth.assertThat;

class DuoTest {
    private Schedule duo;
    private ScheduleModel model;

    @BeforeEach
    void setUp() {
        duo = Duo.of(
                new Step(() -> model.age = 50),
                new Step(() -> model.name = "Carlos")
        );
        model = new ScheduleModel(20, "John", "Smith");
    }

    @Test
    void duoExecutesCorrectly() {
        assertThat(model.age).isNotEqualTo(50);
        assertThat(model.name).isNotEqualTo("Carlos");
        assertThat(duo.proceed()).isTrue();
        assertThat(model.age).isEqualTo(50);
        assertThat(model.name).isNotEqualTo("Carlos");
        assertThat(duo.proceed()).isTrue();
        assertThat(model.age).isEqualTo(50);
        assertThat(model.name).isEqualTo("Carlos");
        assertThat(duo.proceed()).isFalse();
    }

    @Test
    void duoResetsCorrectly() {
        while(duo.proceed());
        duo.reset();
        assertThat(duo.proceed()).isTrue();
    }
}