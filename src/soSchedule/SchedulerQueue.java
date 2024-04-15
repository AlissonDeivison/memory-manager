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

public abstract class SchedulerQueue extends Scheduler {

    private PriorityQueue<SOProcess> queue;
    private Hashtable<String, List<SubProcess>> subProcess;
    private boolean canRegisterNewSubprocess = true; // Variável para controlar o registro de novos subprocessos

    public SchedulerQueue(Comparator<SOProcess> comparator) {
        this.queue = new PriorityQueue<>(comparator);
        this.subProcess = new Hashtable<>();
    }

    @Override
    public void execute(SOProcess p) {
        this.queue.add(p);
        List<SubProcess> sps = SystemOperation.SystemCall(SystemCallType.READ, p);
        this.subProcess.put(p.getId(), sps);
        registerProcess(); // Chama o método de registro após adicionar o processo à fila e obter os subprocessos
    }

    public void registerProcess() {
        if (!canRegisterNewSubprocess) return;
    
        SOProcess p = this.queue.poll();
        if (p != null) {
            List<SubProcess> sps = this.subProcess.get(p.getId());
            if (sps != null) {
                Core[] cores = this.getCpu().getCores();
                for (Core core : cores) {
                    if (sps.isEmpty()) break; // Se não houver mais subprocessos a serem registrados, saia do loop
                    SubProcess sp = sps.remove(0);
                    this.getCpu().registerProcess(core.getId(), sp);
                }
    
                // Se ainda houver subprocessos na lista, permite registrar um novo subprocesso no próximo ciclo de clock
                if (!sps.isEmpty()) {
                    canRegisterNewSubprocess = true;
                }
            }
        } else {
            // Se não houver mais processos para registrar, permite registrar novos subprocessos no próximo ciclo de clock
            canRegisterNewSubprocess = true;
        }
    }

    @Override
    public void finish(SOProcess p) {
        // Chama o método registerProcess para registrar novos subprocessos após a finalização de um processo
        registerProcess();

        // Verifica se ainda há subprocessos restantes para o processo principal
        List<SubProcess> sps = this.subProcess.get(p.getId());
        if (sps != null && !sps.isEmpty()) {
            // Adiciona o processo principal de volta à fila para execução dos subprocessos restantes
            execute(p);
        }
    }

    public void clock() {
        // Chama o método registerProcess para registrar novos subprocessos após cada ciclo de clock
        registerProcess();
        
        // Após o término do ciclo de clock, permite o registro de um novo subprocesso
        canRegisterNewSubprocess = true;
    }

    public PriorityQueue<SOProcess> getQueue() {
        return queue;
    }
}
