package soSchedule;

import java.util.Comparator;
import so.SOProcess;

public class Priority extends SchedulerQueue {

    public Priority() {
        super(new Comparator<SOProcess>() {

            @Override
            public int compare(SOProcess p1, SOProcess p2) {
                return p1.getPriority().getPriorityNumber() >= p2.getPriority().getPriorityNumber() ? 1 : -1;
            }
        });
    }

}
