package soMemory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import so.SOProcess;
import so.SubProcess;

public class MemoryManager {
    private SubProcess[][] physicalMemory;
    private Hashtable<String, FrameMemory> logicalMemory;
    private int pageSize;
    private ArrayList<SOProcess> listOfProcesses;
    private ArrayList<SubProcess> allSubProcesses;

    public static int NUMBER_OF_PROCESS_INSTRUCTIONS = 7;

    // Construtor
    public MemoryManager(int pageSize, int memorySize) {
        this.pageSize = pageSize;
        int pages = memorySize / pageSize;
        physicalMemory = new SubProcess[pages][pageSize];
        logicalMemory = new Hashtable<String, FrameMemory>();
        this.listOfProcesses = new ArrayList<>();
        this.allSubProcesses = new ArrayList<SubProcess>();
    }

    // Retorna os quadros disponíveis para alocar o processo
    private List<FrameMemory> getAvailableFrames(int requiredFrames) {
        List<FrameMemory> frames = new LinkedList<>();
        int consecutiveEmptyFrames = 0;

        for (int frame = 0; frame < physicalMemory.length; frame++) {
            if (consecutiveEmptyFrames == requiredFrames) {
                break;
            }
            if (physicalMemory[frame][0] == null) {
                frames.add(new FrameMemory(frame, 0));
                consecutiveEmptyFrames++;
            } else {
                consecutiveEmptyFrames = 0;
                frames.clear();
            }
        }
        if (consecutiveEmptyFrames != requiredFrames) {
            frames.clear();
        }
        return frames;
    }

    // Aloca o processo na memória
    public SOProcess write(SOProcess p) {
        int requiredFrames = (int) Math.ceil((double) p.getSubProcess().size() / pageSize);
        List<FrameMemory> frames = getAvailableFrames(requiredFrames);
    
        if (!frames.isEmpty()) {
            int remainingSubProcesses = p.getSubProcess().size();
            for (FrameMemory frame : frames) {
                for (int offset = 0; offset < pageSize; offset++) {
                    if (remainingSubProcesses > 0) {
                        SubProcess sp = new SubProcess(p.getId(), NUMBER_OF_PROCESS_INSTRUCTIONS);
                        allSubProcesses.add(sp);
                        //Não está icrementando o offset
                        physicalMemory[frame.getPageNumber()][offset] = sp;
                        logicalMemory.put(sp.getId(), new FrameMemory(frame.getPageNumber(), offset));
                        remainingSubProcesses--;
                    } else {
                        break;
                    }
                }
                if (remainingSubProcesses <= 0) {
                    break;
                }
            }
            printStatusMemory();
            return p;
        } else {
            System.out.println("Não há espaço suficiente na memória para o processo.");
            return null;
        }
    }
    

    // Imprime o status da memória
    public void printStatusMemory() {
        System.out.println("Status da Memória:");
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null) {
                    System.out.print(physicalMemory[i][j].getId() + " | ");
                } else {
                    System.out.print("null | ");
                }
            }
            System.out.println();
        }
    }

    // Deleta um processo da memória
    public void deleteProcess(String processId) {
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null && physicalMemory[i][j].getId().equals(processId)) {
                    physicalMemory[i][j] = null;
                }
            }
        }
    }

    // Obtém a lista de processos únicos na memória
    public Set<String> getUniqueProcesses() {
        Set<String> uniqueProcesses = new HashSet<>();
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null) {
                    uniqueProcesses.add(physicalMemory[i][j].getId().substring(0, 2));
                }
            }
        }
        return uniqueProcesses;
    }

    // Obtém um processo com base no ID
    public SOProcess getProcess(String id) {
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null && physicalMemory[i][j].getId().startsWith(id)) {
                    return new SOProcess(physicalMemory[i][j].getInstructions());
                }
            }
        }
        return null;
    }

    // Remove um processo da memória
    public void removeProcessFromMemory(String processId) {
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null && physicalMemory[i][j].getId().startsWith(processId)) {
                    physicalMemory[i][j] = null;
                }
            }
        }
    }

    // Imprime o status da memória como uma string
    public String printStatusMemoryAsString() {
        StringBuilder status = new StringBuilder();

        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null) {
                    status.append(physicalMemory[i][j].getId()).append(" | ");
                } else {
                    status.append("null | ");
                }
            }
            status.append("\n");
        }

        return status.toString();
    }

    // Lê os subprocessos de um processo na memória
    public List<SubProcess> read(SOProcess p) {
        List<String> ids = p.getSubProcess();
        List<SubProcess> sps = new LinkedList<>();
    
        for (int i = 0; i < allSubProcesses.size(); i++) {
            SubProcess sp = allSubProcesses.get(i);
            if (ids.contains(sp.getId())) {
                sps.add(sp);
            }
        }
    
        return sps;
    }
    

    // Adicionar processo a listOfProcesses
    public void addProcess(SOProcess p) {
        listOfProcesses.add(p); // Adiciona o processo ao ArrayList
    }

    public SOProcess getProcessFromList(String id) {
        for (SOProcess process : listOfProcesses) {
            if (process != null && process.getId().equals(id)) {
                return process;
            }
        }
        return null;
    }

    public ArrayList<SOProcess> getListOfProcesses() {
        return listOfProcesses;
    }
}
