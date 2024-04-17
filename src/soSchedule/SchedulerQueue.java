package soSchedule;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

import so.SOProcessListener;
import so.SOProcess;
import so.SubProcess;
import so.SystemCallType;
import so.SystemOperation;
import soCpu.Core;

public abstract class SchedulerQueue extends Scheduler {

    private PriorityQueue<SOProcess> queue;
    private Hashtable<String, List<SubProcess>> subProcess;

    public SchedulerQueue(Comparator<SOProcess> comparator) {
        this.queue = new PriorityQueue<>(comparator);
        this.subProcess = new Hashtable<>();
    }

    @Override
    public void execute(SOProcess p) {
        this.queue.add(p);
        List<SubProcess> sps = SystemOperation.SystemCall(SystemCallType.READ, p);
        this.subProcess.put(p.getId(), sps);
        registerProcess(); // Chama o método de registro após adicionar o processo à fila e obter os
                           // subprocessos
    }


    private void registerProcess() {
        for (Core core : this.getCpu().getCores()) {
            if (core.getSubProcess() == null) {
                this.coreExecuted(core.getId());
            }
        }
    }

    @Override
    public void coreExecuted(int coreId) {
        try {
            //System.out.println("Core executed");
            SOProcess p = this.queue.peek();
            List<SubProcess> sps = this.subProcess.get(p.getId());
            if (this.subProcess.get(p.getId()) == null || this.subProcess.get(p.getId()).isEmpty()) {
                this.queue.poll(); // Remove The first element
                this.subProcess.remove(p.getId());
                p = this.queue.peek();
                sps = this.subProcess.get(p.getId());

            }
            SubProcess actuallySubprocess = sps.remove(0);
            this.getCpu().registerProcess(coreId, actuallySubprocess);

        } catch (Exception e) {
            //System.out.println("Processo interrompido: ERRO!");
        }
    }

    @Override
    public void finish(SOProcess p) {
        // Chama o método registerProcess para registrar novos subprocessos após a
        // finalização de um processo
        registerProcess();

        // Verifica se ainda há subprocessos restantes para o processo principal
        List<SubProcess> sps = this.subProcess.get(p.getId());
        if (sps != null && !sps.isEmpty()) {
            // Adiciona o processo principal de volta à fila para execução dos subprocessos
            // restantes
            execute(p);
        }
    }

    public void clock() {
        // Chama o método registerProcess para registrar novos subprocessos após cada
        // ciclo de clock
        registerProcess();
    }

    public PriorityQueue<SOProcess> getQueue() {
        return queue;
    }
}
