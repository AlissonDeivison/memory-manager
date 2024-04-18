package so;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SOProcess {
    private String id;
    private int sizeInMemory;
    private List<String> subProcess;
    private Priority priority;
    private int timeToExecute;

    private static int countIndex = 0;

    public SOProcess(int sizeInMemory, int priority) {
        countIndex++;
        this.id = "P" + countIndex;
        this.sizeInMemory = sizeInMemory;

        Random rand = new Random();
        List<Priority> priorityList = Arrays.asList(Priority.CRITICA, Priority.ALTA, Priority.MEDIA, Priority.BAIXA);
        this.priority = priorityList.get(priority);

        List<Integer> timeList = Arrays.asList(100, 200, 300, 400, 500, 600, 700, 800, 900, 2000);
        this.timeToExecute = timeList.get(rand.nextInt(timeList.size()));

    }

    public String getId() {
        return id;
    }

    public int getSizeInMemory() {
        return sizeInMemory;
    }

    public int getTimeToExecute() {
        return timeToExecute;
    }

    public Priority getPriority() {
        return priority;
    }

    public List<String> getSubProcess() {
        if (subProcess == null) {
            initializeSubProcess();
        }
        return subProcess;
    }

    private void initializeSubProcess() {
        subProcess = new ArrayList<>(sizeInMemory);
        for (int i = 0; i < sizeInMemory; i++) {
            subProcess.add(this.id + i);
        }
    }
}
