package so;

public class Process {
    private String id;
    private int sizeInMemory;

    private static int countIndex = 0;

    public Process(int sizeInMemory, int timeToExecute, Priority priority) {
        countIndex++;
        this.id = "P" + countIndex;
        this.sizeInMemory = sizeInMemory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSizeInMemory() {
        return sizeInMemory;
    }

    public void setSizeInMemory(int sizeInMemory) {
        this.sizeInMemory = sizeInMemory;
    }

}
