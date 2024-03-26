package so;

public class SubProcess {
    private String id;
    private int instructions;
    private int timeToExecute;
    public static int count;

    public SubProcess(String processId, int instructions, int timeToExecute) {
        count++;
        this.id = processId + count;
        this.instructions = instructions;
        this.timeToExecute = timeToExecute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInstructions() {
        return instructions;
    }

    public void setInstructions(int instructions) {
        this.instructions = instructions;
    }

    public int getTimeToExecute() {
        return timeToExecute;
    }

    public void setTimeToExecute(int timeToExecute) {
        this.timeToExecute = timeToExecute;
    }
}