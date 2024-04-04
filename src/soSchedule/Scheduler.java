package soSchedule;

import soCpu.CpuManager;
import so.Process;

public abstract class Scheduler {

    private CpuManager cpu;

    public Scheduler() {
        this.cpu = new CpuManager();
    }

    public CpuManager getCpu() {
        return cpu;
    }

    public void setCpu(CpuManager cpu) {
        this.cpu = cpu;
    }

    public abstract void execute(Process p);
    public abstract void finish(Process p);

}
