package soSchedule;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

import so.SOProcess;
import so.SubProcess;
import so.SystemCallType;
import so.SystemOperation;
import soCpu.Core;


public abstract class SchedulerQueue extends Scheduler{

    private PriorityQueue<SOProcess> queue;
    private Hashtable<String, List<SubProcess>> subProcess ;
    
    public SchedulerQueue(Comparator<SOProcess> comparator) {
        this.queue = new PriorityQueue<SOProcess>(comparator);
    }
    
    
    @Override
    public void execute(SOProcess p) {
       this.queue.add(p);
       List<SubProcess> sps =  SystemOperation.SystemCall(SystemCallType.READ, p);
       this.subProcess.put(p.getId(), sps);
       this.registerProcess();
    }

    public void registerProcess(){
        SOProcess p = this.queue.poll();
        List<SubProcess> sps = this.subProcess.get(p.getId());
        Core [] cores = this.getCpu().getCores();
        for (Core core : cores) {
            if(core.getSubProcess() != null){
                SubProcess sp = sps.get(0);
                this.getCpu().registerProcess(core.getId(), sp);
            }
        }
    }

    @Override
    public void finish(SOProcess p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finish'");
    }

    public PriorityQueue<SOProcess> getQueue() {
        return queue;
    }
}
