package so;

import java.util.List;
import java.util.Set;


import soMemory.MemoryManager;
import soSchedule.FCFS;
import soSchedule.Scheduler;

public class SystemOperation {
    private static MemoryManager mm;
    private static Scheduler sc;

    public static Process SystemCall(SystemCallType type, int processSize) {
        if (type.equals(SystemCallType.CREATE)) {
            if (mm == null) {
                mm = new MemoryManager(4, 256);
            }

            if(sc == null){
                sc = new FCFS(); //Aqui é onde o modificamos a estratégia de escalonamento
            }
        }
        return new Process(processSize);
    }

    public static List<SubProcess> SystemCall(SystemCallType type, Process p) {
        if (type.equals(SystemCallType.WRITE)) {
            mm.write(p);
        } else if (type.equals(SystemCallType.READ)) {
            return mm.read(p);
        } else if (type.equals(SystemCallType.DELETE)) {
            String processId = p.getId().toString();
            mm.deleteProcess(processId);
        } 
        return null;
    }

    public static Set<String> getUniqueProcesses() {
        if (mm == null) {
            return null;
        } else {
            return mm.getUniqueProcesses();
        }
    }

    public static void removeProcessFromMemory(String processId) {
        mm.removeProcessFromMemory(processId);
    }

    public static String statusMemory() {
        return mm.printStatusMemoryAsString();
    }
}
