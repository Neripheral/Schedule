package schedule;

class Step implements Schedule{
    public Step(Runnable procedure){
        this.procedure = procedure;
    }

    @Override
    public boolean proceed() {
        if(procedure != null){
            procedure.run();
            procedure = null;
            return true;
        }
        return false;
    }

    private Runnable procedure;
}
