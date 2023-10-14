package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class DuoTest {
    protected Schedule duo;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        duo = new Duo(
                new Step(
                        () -> model.age = 50,
                        "Set age to 50"),
                new Step(
                        () -> model.name = "Carlos",
                "Set name to Carlos"));
        model = ScheduleModel.getFreshModel();
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

    @Test public void toStringReturnsCorrectString(){
        assertThat(duo.toString()).isEqualTo(
                "[ ] Set age to 50\n" +
                        "[ ] Set name to Carlos"
        );
    }
}