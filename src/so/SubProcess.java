package so;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SubProcess {
    private String id;
    private String processId;
    private int instructions;
    public static int count;
    private int timeToExecute;
    private Priority priority;


    public SubProcess(String processId, int instructions) {
        this.processId = processId;
        this.id = processId + "[" + count + "]";
        this.instructions = instructions;
        Random rand = new Random();
        List<Integer> givenList =  Arrays.asList(100,200,300,400,500,600,700,800,900,2000);
        this.timeToExecute = givenList.get(rand.nextInt(givenList.size()));
        List<Priority> priorityList = Arrays.asList(Priority.BAIXA, Priority.MEDIA, Priority.ALTA, Priority.CRITICA);
        this.priority = priorityList.get(rand.nextInt(priorityList.size()));
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

    public int getTimeToExecute() {
        return timeToExecute;
    }

    public Priority getPriority() {
        return priority;
    }

}