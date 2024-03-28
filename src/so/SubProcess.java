package so;

public class SubProcess {
    private String id;
    private String processId;
    private int instructions;
    public static int count;

    public SubProcess(String processId, int instructions) {
        this.processId = processId;
        this.id = processId + "[" + count + "]";
        this.instructions = instructions;
        count++;
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

    public String getProcessId() {
        return processId;
    }


}