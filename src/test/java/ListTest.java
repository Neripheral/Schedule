import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Schedule;
import schedule.Schedules;

import static com.google.common.truth.Truth.assertThat;

class ListTest {
    private Schedule list;
    private ScheduleModel model;

    @BeforeEach
    void setUp() {
        list = Schedules.list(
                () -> model.age = 50,
                () -> model.name = "Carlos",
                () -> model.surname = "Lopez",
                () -> model.age = 70,
                () -> model.name = "Kamil",
                () -> model.surname = "Nowak"
        );
        model = new ScheduleModel(20, "John", "Smith");
    }

    @Test
    void listExecutesCorrectly() {
        assertThat(model.age).isEqualTo(20);
        assertThat(model.name).isEqualTo("John");
        assertThat(model.surname).isEqualTo("Smith");
        assertThat(list.proceed()).isTrue();
        assertThat(model.age).isEqualTo(50);
        assertThat(model.name).isEqualTo("John");
        assertThat(model.surname).isEqualTo("Smith");
        assertThat(list.proceed()).isTrue();
        assertThat(model.age).isEqualTo(50);
        assertThat(model.name).isEqualTo("Carlos");
        assertThat(model.surname).isEqualTo("Smith");
        assertThat(list.proceed()).isTrue();
        assertThat(model.age).isEqualTo(50);
        assertThat(model.name).isEqualTo("Carlos");
        assertThat(model.surname).isEqualTo("Lopez");
        assertThat(list.proceed()).isTrue();
        assertThat(model.age).isEqualTo(70);
        assertThat(model.name).isEqualTo("Carlos");
        assertThat(model.surname).isEqualTo("Lopez");
        assertThat(list.proceed()).isTrue();
        assertThat(model.age).isEqualTo(70);
        assertThat(model.name).isEqualTo("Kamil");
        assertThat(model.surname).isEqualTo("Lopez");
        assertThat(list.proceed()).isTrue();
        assertThat(model.age).isEqualTo(70);
        assertThat(model.name).isEqualTo("Kamil");
        assertThat(model.surname).isEqualTo("Nowak");
        assertThat(list.proceed()).isFalse();
    }

    @Test
    void listResetsCorrectly() {
        while(list.proceed());
        list.reset();
        assertThat(list.proceed()).isTrue();
    }
}
