package soSchedule;

import java.util.List;
import java.util.PriorityQueue;

import so.SubProcess;
import so.SystemCallType;
import so.SystemOperation;
import soCpu.Core;
import so.Process;

public class FCFS extends Scheduler {

    private PriorityQueue<SubProcess> queue;

    public FCFS() {
        this.queue = new PriorityQueue<>();
    }

    @Override
    public void execute(Process p) {
        List<SubProcess> sps = SystemOperation.SystemCall(SystemCallType.READ, p);
        for (SubProcess sp : sps) {
            this.queue.add(sp);
        }
        while (!this.queue.isEmpty()) {
            for (Core core : this.getCpu().getCores()) {
                if (core.getSubProcess() == null) {
                    this.getCpu().registerProcess(core.getId(), this.queue.poll());
                }
            }
        }
    }

    @Override
    public void finish(Process p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finish'");
    }

}
