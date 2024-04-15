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
        this.subProcess = new Hashtable<String, List<SubProcess>>();
    }
    
    
    @Override
    public void execute(SOProcess p) {
        this.queue.add(p);
        List<SubProcess> sps = SystemOperation.SystemCall(SystemCallType.READ, p);
        this.subProcess.put(p.getId(), sps);
        registerProcess(); // Chama o método de registro após adicionar o processo à fila e obter os subprocessos
    }
    
    public void registerProcess() {
        // Remove um processo da fila para registro
        SOProcess p = this.queue.poll();
        if (p != null) {
            // Obtém os subprocessos correspondentes ao processo
            List<SubProcess> sps = this.subProcess.get(p.getId());
            if (sps != null) {
                // Obtém a lista de núcleos da CPU
                Core[] cores = this.getCpu().getCores();
                // Itera sobre os núcleos e tenta registrar o processo em um núcleo disponível
                for (Core core : cores) {
                    // Verifica se o núcleo não possui um subprocesso em execução e se há subprocessos a serem registrados
                    if (core.getSubProcess() == null && !sps.isEmpty()) {
                        // Remove o primeiro subprocesso da lista e registra no núcleo
                        SubProcess sp = sps.remove(0);
                        this.getCpu().registerProcess(core.getId(), sp);
                    }
                }
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
