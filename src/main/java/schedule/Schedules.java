package schedule;

public enum Schedules {;
    public static Schedule perform(Runnable procedure){
        return new Step(procedure);
    }
}
