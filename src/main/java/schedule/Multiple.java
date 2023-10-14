package schedule;

class Multiple implements Schedule{
    private int repeatsExecuted = 0;
    private final int expectedRepeats;
    private final Schedule scheduleToRepeat;

    public Multiple(int expectedRepeats, Schedule scheduleToRepeat) {
        this.expectedRepeats = expectedRepeats;
        this.scheduleToRepeat =
                new Repetition(
                        ()->{
                            boolean shouldContinue = repeatsExecuted < expectedRepeats;
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

    @Override
    public String toString() {
        String body = scheduleToRepeat.toString();
        body = body.substring(body.indexOf("\n")+1);
        return String.format(
                """
                (%d/%d):
                %s""",
                repeatsExecuted,
                expectedRepeats,
                body
        );
    }
}
