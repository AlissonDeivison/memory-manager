package so;

public class SubProcess {
    private String id;
    private String processId;
    private int instructions;
    private static int nextId = 0; 

    public SubProcess(String processId, int instructions) {
        this.processId = processId;
        this.id = processId + "[" + nextId + "]";
        this.instructions = instructions;
        
        nextId++;
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
