package so;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SOProcess {
    private String id;
    private int sizeInMemory;
    private List<String> subProcess;
    private Priority priority;
    private int timeToExecute;

    private static int countIndex = 0;

    public SOProcess(int sizeInMemory) {
        countIndex++;
        this.id = "P" + countIndex;
        this.sizeInMemory = sizeInMemory;
        
        Random rand = new Random();
        List<Priority> priorityList = Arrays.asList(Priority.BAIXA, Priority.MEDIA, Priority.ALTA, Priority.CRITICA);
        this.priority = priorityList.get(rand.nextInt(priorityList.size()));
        
        List<Integer> timeList = Arrays.asList(100, 200, 300, 400, 500, 600, 700, 800, 900, 2000);
        this.timeToExecute = timeList.get(rand.nextInt(timeList.size()));
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

    public int getTimeToExecute() {
        return timeToExecute;
    }

    public Priority getPriority() {
        return priority;
    }

    public List<String> getSubProcess() {
        if (subProcess == null) {
            subProcess = new LinkedList<>();
            for (int i = 0; i < sizeInMemory; i++) {
                this.subProcess.add(id+i);
            }
        }
        return subProcess;
    }


}
