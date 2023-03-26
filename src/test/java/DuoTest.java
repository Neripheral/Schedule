import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Duo;
import schedule.Schedule;
import schedule.Step;

import static com.google.common.truth.Truth.assertThat;

class DuoTest {
    protected Schedule duo;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        duo = new Duo(new Step(() -> model.age = 50), new Step(() -> model.name = "Carlos"));
        model = getFreshModel();
    }

    public static ScheduleModel getFreshModel(){
        return new ScheduleModel(20, "John", "Smith");
    }

    @Test
    public void duoExecutesCorrectly() {
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
}