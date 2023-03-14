package schedule;

public enum Schedules {;
    public static Schedule step(Runnable procedure){
        return new Step(procedure);
    }
}
