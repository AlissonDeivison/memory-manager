package so;

import soCpu.CpuManager;
import soMemory.MemoryManager;
import soMemory.Strategy;
import so.Process;
import so.SystemCallType;

public class SystemOperation {
    private static MemoryManager mm;
    private static CpuManager cm;

    private static void createProcess() {
        if (mm == null) {
            mm = new MemoryManager(Strategy.BEST_FIT);
        }
        if (cm == null) {
            cm = new CpuManager();
        }
    }

    public static void SystemCall(SystemCallType type, Process p) {
        switch (type) {
            case DELETE:
                mm.deleteProcess(p);
                break;
            case READ:
                mm.readProcess(p);
                break;
            case WRITE:
                mm.writeProcess(p);
                break;
            default:
                break;
        }
    }
}
