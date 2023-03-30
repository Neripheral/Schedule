package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class ForkTest {
    protected Schedule fork;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        fork = new Fork(
                () -> model.age >= 18,
                new Step(() -> model.surname = "Oldman"),
                new Duo(
                        new Step(()->model.name = "Brian"),
                        new Step(() ->model.surname = "Newman")
                )
        );
        model = ScheduleModel.getFreshModel();
    }

    @Test
    public void forkRedirectsCorrectlyOnTrue() {
        assertThat(fork.proceed()).isTrue();
        assertThat(model.surname).isEqualTo("Oldman");
        assertThat(fork.proceed()).isFalse();
    }

    @Test
    public void forkRedirectsCorrectlyOnFalse() {
        model.age = 15;
        assertThat(fork.proceed()).isTrue();
        assertThat(model.name).isEqualTo("Brian");
        assertThat(model.surname).isEqualTo("Smith");
        assertThat(fork.proceed()).isTrue();
        assertThat(model.name).isEqualTo("Brian");
        assertThat(model.surname).isEqualTo("Newman");
        assertThat(fork.proceed()).isFalse();
    }

    @Test
    public void forkRemembersConditionCheckOnTrue() {
        assertThat(fork.proceed()).isTrue();
        model.age = 15;
        assertThat(fork.proceed()).isFalse();
    }


    @Test
    public void forkRemembersConditionCheckOnFalse() {
        model.age = 15;
        assertThat(fork.proceed()).isTrue();
        model.age = 20;
        assertThat(fork.proceed()).isTrue();
        assertThat(fork.proceed()).isFalse();
    }
}