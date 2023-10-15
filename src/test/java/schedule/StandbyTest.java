package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class StandbyTest {
    protected Schedule standby;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        standby = new Duo(
                new Standby(()->model.age < 18, "model's age is less than 18"),
                new Duo(
                        new Step(()->model.name = "Luke", "Set name to Luke"),
                        new Step(()->model.surname = "Stirling", "Set surname to Stirling")
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

    @Test public void toStringReturnsCorrectString(){
        assertThat(standby.toString()).isEqualTo(
                """
                [ ] Wait until model's age is less than 18
                [ ] Set name to Luke
                [ ] Set surname to Stirling""");
    }

    @Test public void toStringReturnsCorrectStringWhenBlocked(){
        standby.proceed();
        assertThat(standby.toString()).isEqualTo(
                """
                [!] Wait until model's age is less than 18
                [ ] Set name to Luke
                [ ] Set surname to Stirling""");
    }

    @Test public void toStringReturnsCorrectStringWhenFinished(){
        standby.proceed();
        model.age = 17;
        while(standby.proceed());
        assertThat(standby.toString()).isEqualTo(
                """
                [X] Wait until model's age is less than 18
                [X] Set name to Luke
                [X] Set surname to Stirling""");
    }
}