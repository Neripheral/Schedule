package schedule;

class Multiple implements Schedule{
    private int repeatsExecuted = 0;
    private final Schedule scheduleToRepeat;

    public Multiple(int repeatsExpected, Schedule scheduleToRepeat) {
        this.scheduleToRepeat =
                new Repetition(
                        ()->{
                            boolean shouldContinue = repeatsExecuted < repeatsExpected;
                            if(shouldContinue){
                                repeatsExecuted++;
                                return true;
                            }
                            return false;
                        },
                        scheduleToRepeat
                );
    }

    @Override
    public boolean proceed() {
        return scheduleToRepeat.proceed();
    }

    @Override
    public void reset() {
        repeatsExecuted = 0;
        scheduleToRepeat.reset();
    }
}
