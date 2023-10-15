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
                new Step(() -> model.surname = "Oldman",
                        "Set the name to Oldman"),
                new Duo(
                        new Step(()->model.name = "Brian",
                                "Set the name to Brian"),
                        new Step(() ->model.surname = "Newman",
                                "Set the surname to Newman")
                ),
                "Model's age is at least 18"
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

    @Test public void toStringReturnsCorrectString(){
        assertThat(fork.toString()).isEqualTo(
                """
                        if([ ]Model's age is at least 18):
                        |[ ] Set the name to Oldman
                        else:
                        |[ ] Set the name to Brian
                        |[ ] Set the surname to Newman"""
        );
    }

    @Test public void toStringReturnsCorrectStringWhenPassed(){
        while(fork.proceed());
        assertThat(fork.toString()).isEqualTo(
                """
                        if([T]Model's age is at least 18):
                        |[X] Set the name to Oldman
                        else:
                        |[ ] Set the name to Brian
                        |[ ] Set the surname to Newman"""
        );
    }

    @Test public void toStringReturnsCorrectStringWhenNOTPassed(){
        model.age = 15;
        while(fork.proceed());
        assertThat(fork.toString()).isEqualTo(
                """
                        if([F]Model's age is at least 18):
                        |[ ] Set the name to Oldman
                        else:
                        |[X] Set the name to Brian
                        |[X] Set the surname to Newman"""
        );
    }
}