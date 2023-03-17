package schedule;

public enum Schedules {;
    public static Schedule step(Runnable procedure){
        return Step.of(procedure);
    }
}
