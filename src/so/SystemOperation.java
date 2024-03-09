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
            mm = new MemoryManager(Strategy.FIRST_FIT);
        }
        if (cm == null) {
            cm = new CpuManager();
        }
    }

    public static Process systemCall(SystemCallType type, Process p) {
        if (type.equals((SystemCallType.WRITE))) {
             mm.writeProcess(p);
        } else if (type.equals((SystemCallType.READ))) {
            mm.readProcess(p);
        } else if (type.equals((SystemCallType.CREATE))) {
            startsMmAndCpu();
            Process process = cm.createProcess(p);
            return process;
        } else if (type.equals((SystemCallType.DELETE))) {
            mm.deleteProcess(p);  
        }
        return null;
    }
}
