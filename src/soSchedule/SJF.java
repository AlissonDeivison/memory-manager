package soSchedule;

import java.util.Comparator;
import so.SOProcess;


public class SJF extends SchedulerQueue {


    public SJF() {
        super(new Comparator<SOProcess>() {
            @Override
            public int compare(SOProcess sp1, SOProcess sp2) {
                return sp1.getTimeToExecute() < sp2.getTimeToExecute() ? 1 : -1;
            }
        });
    }
}
