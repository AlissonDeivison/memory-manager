package soSchedule;

import soCpu.CpuManager;
import so.SOProcess;
import so.SOProcessListener;

public abstract class Scheduler implements SOProcessListener{

    private CpuManager cpu;

    public Scheduler() {
        this.cpu = new CpuManager(this);
    }

    public CpuManager getCpu() {
        return cpu;
    }

    public void setCpu(CpuManager cpu) {
        this.cpu = cpu;
    }

    public abstract void execute(SOProcess p);
    public abstract void finish(SOProcess p);

}
