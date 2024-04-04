package soSchedule;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import so.Process;
import so.SubProcess;
import so.SystemCallType;
import so.SystemOperation;
import soCpu.Core;

public class SJS extends Scheduler {

    private PriorityQueue<SubProcess> queue;

    public SJS() {
        Comparator<SubProcess> compare = new Comparator<SubProcess>() {

            @Override
            public int compare(SubProcess sp1, SubProcess sp2) {
                return sp1.getTimeToExecute() >= sp2.getTimeToExecute() ? 1 : -1;
            }
        };
        this.queue = new PriorityQueue<>(compare);
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
