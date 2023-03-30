import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Schedule;
import schedule.S;

import static com.google.common.truth.Truth.assertThat;

class ListTest {
    protected Schedule list;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        list = S.list(
                () -> model.age = 50,
                () -> model.name = "Carlos",
                () -> model.surname = "Lopez",
                () -> model.age = 70,
                () -> model.name = "Kamil",
                () -> model.surname = "Nowak"
        );
        model = ScheduleModel.getFreshModel();
    }

    @Test
    public void listExecutesCorrectly() {
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
}
