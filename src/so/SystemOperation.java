package so;

import soCpu.CpuManager;
import soMemory.MemoryManager;
import soMemory.Strategy;
import soSchedule.Schedule;

public class SystemOperation {
    private static MemoryManager mm;
    private static CpuManager cm;
    private static Schedule sc;

    private static void startsMmAndCpu() {
        if (mm == null) {
            mm = new MemoryManager(Strategy.WORST_FIT);
        }
        if (cm == null) {
            cm = new CpuManager();
        }
    }

    public static Process systemCall(SystemCallType type, Process p, int n) {
        if (type.equals((SystemCallType.WRITE))) {
             mm.writeProcess(p);
        } else if (type.equals((SystemCallType.READ))) {
            mm.readProcess(p);
        } else if (type.equals((SystemCallType.CREATE))) {
            startsMmAndCpu();
            Process process = cm.createProcess(p, n);
            return process;
        } else if (type.equals((SystemCallType.DELETE))) {
            String processId = p.getId().toString();
            MemoryManager.deleteProcess(processId);  
        }
        return null;
    }
}
