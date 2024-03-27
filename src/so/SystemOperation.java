package so;

import soCpu.CpuManager;
import soMemory.MemoryManager;
import soSchedule.Schedule;

public class SystemOperation {
    private static MemoryManager mm;
    private static CpuManager cm;
    private static Schedule sc;

    private static void startsMmAndCpu() {

        if (mm == null) {
            mm = new MemoryManager(4, 256);
        }
        if (cm == null) {
            cm = new CpuManager(4);
        }
    }

    public static Process systemCall(SystemCallType type, Process p, int n) {
        if (type.equals((SystemCallType.WRITE))) {
            Process process = mm.write(p);
            return process;
        } else if (type.equals((SystemCallType.READ))) {
            // mm.readProcess(p);
        } else if (type.equals((SystemCallType.CREATE))) {
            startsMmAndCpu();
        } else if (type.equals((SystemCallType.DELETE))) {
            String processId = p.getId().toString();
            // MemoryManager.deleteProcess(processId);
        }
        return null;
    }
}
