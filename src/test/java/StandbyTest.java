import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.Duo;
import schedule.Schedule;
import schedule.Standby;
import schedule.Step;

import static com.google.common.truth.Truth.assertThat;

class StandbyTest {
    protected Schedule standby;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        standby = new Duo(
                new Standby(()->model.age < 18),
                new Duo(
                        new Step(()->model.name = "Luke"),
                        new Step(()->model.surname = "Stirling")
                )
        );
        model = ScheduleModel.getFreshModel();
    }

    @Test
    public void standbyFreezesScheduleForAtLeast200Proceeds() {
        for (int i = 0; i < 200; i++) {
            assertThat(standby.proceed()).isTrue();
        }
        assertThat(model.name).isNotEqualTo("Luke");
        assertThat(model.surname).isNotEqualTo("Stirling");
    }

    @Test
    public void standbyLetsSchedulePassItIfConditionMetOnFirstCheck(){
        model.age = 10;
        assertThat(standby.proceed()).isTrue();
        assertThat(model.name).isEqualTo("Luke");
    }

    @Test
    public void standbyUnfreezesAfterAFewTriesAfterConditionMet() {
        for (int i = 0; i < 10; i++) {
            assertThat(standby.proceed()).isTrue();
        }
        model.age = 10;
        assertThat(standby.proceed()).isTrue();
        assertThat(model.name).isEqualTo("Luke");
        assertThat(model.surname).isNotEqualTo("Stirling");
        assertThat(standby.proceed()).isTrue();
        assertThat(model.surname).isEqualTo("Stirling");
        assertThat(standby.proceed()).isFalse();
    }

    @Test
    public void standbyStaysUnfrozenEvenThoughConditionIsNoLongerMet() {
        assertThat(standby.proceed()).isTrue();
        model.age = 10;
        assertThat(standby.proceed()).isTrue();
        model.age = 20;
        assertThat(standby.proceed()).isTrue();
        assertThat(model.name).isEqualTo("Luke");
        assertThat(model.surname).isEqualTo("Stirling");
        assertThat(standby.proceed()).isFalse();
    }
}