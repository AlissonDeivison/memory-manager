package soMemory;

import java.util.HashSet;

// import java.util.ArrayList;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import so.Process;
import so.SubProcess;

public class MemoryManager {
    private SubProcess[][] physicalMemory;
    private Hashtable<String, FrameMemory> logicalMemory;
    private int pageSize;

    public static int NUMBER_OF_PROCESS_INSTRUCTIONS = 7;

    public MemoryManager(int pageSize, int memorySize) {
        this.pageSize = pageSize;
        int pages = memorySize / pageSize;
        physicalMemory = new SubProcess[pages][pageSize];
        this.logicalMemory = new Hashtable<>();
    }

    private List<FrameMemory> getFrames(Process p) {
        List<String> subProcesses = p.getSubProcess();
        int totalSubProcesses = subProcesses.size();
        int totalOfPages = (int) Math.ceil((double) totalSubProcesses / this.pageSize);

        List<FrameMemory> frames = new LinkedList<>();
        int consecutiveEmptyFrames = 0;

        for (int frame = 0; frame < physicalMemory.length; frame++) {
            if (consecutiveEmptyFrames == totalOfPages) {
                return frames;
            }
            if (this.physicalMemory[frame][0] == null) {
                frames.add(new FrameMemory(frame, 0));
                consecutiveEmptyFrames++;
            } else {
                consecutiveEmptyFrames = 0;
                frames.clear();
            }
        }
        if (consecutiveEmptyFrames == totalOfPages) {
            return frames;
        }
        return null;
    }

    public Process write(Process p) {
        List<FrameMemory> frames = this.getFrames(p); // Lista de frames que o processo ocupa

        if (frames != null) {
            int increment = 0; // Gambiarra

            for (FrameMemory frame : frames) {
                for (int offset = 0; offset < this.pageSize; offset++) {
                    if (increment < p.getSubProcess().size()) {
                        this.physicalMemory[frame.getPageNumber()][offset] = new SubProcess(p.getId(),
                                NUMBER_OF_PROCESS_INSTRUCTIONS);
                        increment++;
                    } else {
                        break;
                    }
                }
            }
            this.printStatusMemory();
            return p;
        } else {
            System.out.println("Não há espaço suficiente na memória para o processo.");
            return null;
        }
    }

    public void printStatusMemory() {
        for (int i = 0; i < this.physicalMemory.length; i++) {
            for (int j = 0; j < this.pageSize; j++) {
                if (this.physicalMemory[i][j] != null) {
                    if (j == (this.pageSize - 1)) {
                        System.out.println(this.physicalMemory[i][j].getId());
                    } else {
                        System.out.print(this.physicalMemory[i][j].getId() + " | ");
                    }
                } else {
                    if (j == (this.pageSize - 1)) {
                        System.out.println("null");
                    } else {
                        System.out.print("null | ");
                    }
                }
            }
        }

    }

    public void deleteProcess(String processId) {
        for (int i = 0; i < this.physicalMemory.length; i++) {
            for (int j = 0; j < this.pageSize; j++) {
                if (this.physicalMemory[i][j] != null && this.physicalMemory[i][j].getId().equals(processId)) {
                    this.physicalMemory[i][j] = null;
                }
            }
        }
    }

    public Set<String> getUniqueProcesses() {
        Set<String> uniqueProcesses = new HashSet<>();

        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null) {
                    uniqueProcesses.add(physicalMemory[i][j].getId().substring(0, 2)); // Ajuste para pegar apenas o ID
                                                                                       // do processo
                }
            }
        }
        return uniqueProcesses;
    }

    // Verifica se o processo existe na memória
    public Process getProcess(String id) {
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null && physicalMemory[i][j].getId().equals(id)) {
                    return new Process(physicalMemory[i][j].getInstructions());
                }
            }
        }
        return null;
    }

    public void removeProcessFromMemory(String processId) {
        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null && physicalMemory[i][j].getId().startsWith(processId)) {
                    physicalMemory[i][j] = null;
                }
            }
        }
    }

    public String printStatusMemoryAsString() {
        StringBuilder status = new StringBuilder();

        for (int i = 0; i < physicalMemory.length; i++) {
            for (int j = 0; j < pageSize; j++) {
                if (physicalMemory[i][j] != null) {
                    if (j == (pageSize - 1)) {
                        status.append(physicalMemory[i][j].getId()).append("\n");
                    } else {
                        status.append(physicalMemory[i][j].getId()).append(" | ");
                    }
                } else {
                    if (j == (pageSize - 1)) {
                        status.append("null\n");
                    } else {
                        status.append("null | ");
                    }
                }
            }
        }

        return status.toString();
    }

}
