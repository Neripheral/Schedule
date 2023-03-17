package schedule;

public enum Schedules {;
    public static Schedule step(Runnable procedure){
        if(procedure == null)
            return EmptyStep.INSTANCE;
        return Step.of(procedure);
    }
}
