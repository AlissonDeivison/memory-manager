package so;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import soMemory.MemoryManager;
import soSchedule.FCFS;
import soSchedule.Scheduler;

public class SystemOperation {
    private static MemoryManager mm;
    private static Scheduler sc;

    public static SOProcess SystemCall(SystemCallType type, int processSize, int priority, int timeToExecute) {
        if (type == SystemCallType.CREATE) {
            if (mm == null) {
                mm = new MemoryManager(4, 256);
            }

            if (sc == null) {
                sc = new FCFS(); // Modifica a estratégia de escalonamento aqui
            }
        }
        return new SOProcess(processSize, priority, timeToExecute);
    }

    public static List<SubProcess> SystemCall(SystemCallType type, SOProcess p) {
        if (type == SystemCallType.WRITE) {
            boolean writeSuccess = mm.write(p) != null;
            mm.addProcess(p);
            if (writeSuccess) {
                System.out.println("Processo criado com sucesso! ID: " + p.getId() + " Tamanho: " + p.getSizeInMemory() + " Prioridade: " + p.getPriority() + " Tempo de execução: " + p.getTimeToExecute());
            } else {
                mm.deleteProcess(p.getId());
            }
        } else if (type == SystemCallType.READ) {
            return mm.read(p);
        } else if (type == SystemCallType.DELETE) {
            String processId = p.getId().toString();
            mm.deleteProcess(processId);
        }
        return null;
    }

    // Função para executar os processos existentes na memória
    public static void executeProcesses(SOProcess p) {
        sc.execute(p);
    }

    public static Set<String> getUniqueProcesses() {
        if (mm == null) {
            return null;
        } else {
            return mm.getUniqueProcesses();
        }
    }

    public static void removeProcessFromMemory(String processId) {
        if (mm != null) {
            mm.removeProcessFromMemory(processId);
        }
    }

    public static String statusMemory() {
        if (mm != null) {
            return mm.printStatusMemoryAsString();
        }
        return null;
    }

    public static SOProcess getProcess(String p) {
        if (mm != null) {
            return mm.getProcessFromList(p);
        }
        return null;
    }

    public static ArrayList<SOProcess> getAllProcess(int n){
        if (mm != null) {
            return mm.getListOfProcesses(n);
            
        }
        return null;
    }
}
