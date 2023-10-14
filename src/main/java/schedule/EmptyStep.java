package schedule;

class EmptyStep implements Schedule{
    public final static EmptyStep INSTANCE = new EmptyStep();

    private EmptyStep(){}

    @Override
    public boolean proceed() {
        return false;
    }

    @Override
    public void reset() {}

    @Override
    public String toString() {
        return "-Stub-";
    }
}
