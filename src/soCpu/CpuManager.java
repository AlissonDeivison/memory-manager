package soCpu;

import java.util.Timer;
import java.util.TimerTask;

import so.SOProcessListener;
import so.SubProcess;

public class CpuManager {
    private Core[] cores;
    public static int NUM_OF_INSTRUCTIONS_PER_CLOCK = 7;
    public static int CLOCK_TIME = 1000;
    public static int NUM_OF_CORES = 4;
    public CpuManager(SOProcessListener listener) {

        this.cores = new Core[NUM_OF_CORES];
        for (int i = 0; i < this.cores.length; i++) {
            this.cores[i] = new Core(i, NUM_OF_INSTRUCTIONS_PER_CLOCK, listener);
        }
        this.clock();
    }

    public void registerProcess(int coreId, SubProcess p) {
        this.cores[coreId].setSubProcess(p);
        //System.out.println("Processo " + p.getId() + " registrado no core " + coreId);
    }

    private void clock() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                executeProcesses();
            }
        }, 0, CLOCK_TIME);
    }

    private void executeProcesses() {
        boolean hasSubProcessRunning = false;
        // Verifica se há subprocessos em execução
        for (int i = 0; i < this.cores.length; i++) {
            if (this.cores[i].getSubProcess() != null) {
                hasSubProcessRunning = true;
                break;
            }
        }

        // Se houver subprocessos em execução, executa o ciclo de clock e exibe os
        // prints
        if (hasSubProcessRunning) {
            boolean continueExecution = true;
            while (continueExecution) {
                continueExecution = false; // Assume que não há mais subprocessos para executar
                System.out.println("\n----- Início do ciclo de clock -----");
                for (int i = 0; i < this.cores.length; i++) {
                    Core core = this.cores[i];
                    if (core.getSubProcess() != null) {
                        System.out.println("Executando core " + core.getId() + ":" + core.getSubProcess().getId());
                        core.run();
                        continueExecution = true; // Há pelo menos um subprocesso em execução, então continue o ciclo
                    } else {
                        System.out.println("Executando core " + core.getId() + ":" + "Nenhum processo alocado");
                    }
                }
                System.out.println("----- Fim do ciclo de clock -----\n");
            }
        }
    }

    public Core[] getCores() {
        return cores;
    }
}
