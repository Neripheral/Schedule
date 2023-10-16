package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static com.google.common.truth.Truth.assertThat;
import static schedule.ScheduleIntegrationTest.RockPaperScissors.Sign.*;

public class ScheduleIntegrationTest {
    Schedule schedule;
    RockPaperScissors model;

    static class RockPaperScissors {
        List<Round> roundsRegistry = new ArrayList<>();
        List<Integer> points = Arrays.asList(0,0);
        Round currentRound = new Round();

        public void declareSign(int playerId, Sign sign){
            if (currentRound.isFinished()) {
                roundsRegistry.add(currentRound);
                currentRound = new Round();
            }
            currentRound.playersSigns.set(playerId, sign);
        }

        static class Round{
            List<Sign> playersSigns;

            public Round() {
                playersSigns = new ArrayList<>();
                playersSigns.add(null);
                playersSigns.add(null);
            }

            public boolean isFinished() {
                return playersSigns.get(0) != null
                        && playersSigns.get(1) != null;
            }
        }

        enum Sign{
            ROCK,
            PAPER,
            SCISSORS;

            static{
                ROCK.beats = SCISSORS;
                PAPER.beats = ROCK;
                SCISSORS.beats = PAPER;
            }

            Sign beats;

            public boolean isBeating(Sign otherSign){
                return otherSign == beats;
            }
        }

        public Sign getPredeterminedPick(int playerId){
            Stack<Sign> firstPlayerSigns = new Stack<>();
            firstPlayerSigns.addAll(List.of(PAPER, PAPER, SCISSORS));
            Stack<Sign> secondPlayerSigns = new Stack<>();
            secondPlayerSigns.addAll(List.of(SCISSORS, PAPER, ROCK));
            List<Stack<Sign>> playerSigns = List.of(firstPlayerSigns, secondPlayerSigns);

            return playerSigns.get(playerId).pop();
        }
    }

    @BeforeEach
    protected void setUp() {
        schedule =
                S.repeatFor(3,
                        S.list(
                                S.perform(
                                    ()->model.declareSign(0, model.getPredeterminedPick(0)),
                                    "First player picks sign"),
                                S.perform(
                                    ()->model.declareSign(1, model.getPredeterminedPick(1)),
                                    "Second player picks sign"),
                                S.performIf(
                                        ()->model.currentRound.playersSigns.get(0)
                                                .isBeating(model.currentRound.playersSigns.get(1)),
                                        S.perform(
                                                ()->model.points.set(0, model.points.get(0)+1),
                                                "Increment player 1 points"),
                                        "player 1 beats player 2"
                                ),
                                S.performIf(
                                        ()->model.currentRound.playersSigns.get(1)
                                                .isBeating(model.currentRound.playersSigns.get(0)),
                                        S.perform(
                                                ()->model.points.set(1, model.points.get(1)+1),
                                                "Increment player 2 points"),
                                        "player 2 beats player 1"
                                )
                        )
                );
        model = new RockPaperScissors();
    }

    @Test
    public void toStringStressTest(){
        assertThat(schedule.toString()).isEqualTo(
                """
                        [ ] (0/3):
                        |[ ] First player picks sign
                        |[ ] Second player picks sign
                        |if([ ]player 1 beats player 2):
                        ||[ ] Increment player 1 points
                        |else:
                        ||-Stub-
                        |if([ ]player 2 beats player 1):
                        ||[ ] Increment player 2 points
                        |else:
                        ||-Stub-"""
        );
    }

    @Test
    public void toStringStressTestCompleted(){
        while(schedule.proceed());

        // Po powrocie zrób test na toString i sprawdź, czemu nie działa

    }
}
