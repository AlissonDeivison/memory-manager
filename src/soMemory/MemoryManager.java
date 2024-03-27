package soMemory;

// import java.util.ArrayList;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

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
        int totalOfPages = p.getSizeInMemory() / this.pageSize; // Quantidade de páginas que o processo ocupa
        List<FrameMemory> frames = new LinkedList<>(); // Lista de frames que o processo ocupa

        for (int frame = 0; frame < physicalMemory.length; frame++) {
            if (this.physicalMemory[frame][0] == null) {
                frames.add(new FrameMemory(frame, 0)); // Adiciona o frame na lista
                if (frames.size() >= totalOfPages) {
                    return frames;
                }
            }
        }
        return null;
    }

    public Process write (Process p) {
        List<FrameMemory> frames = this.getFrames(p); // Lista de frames que o processo ocupa

        int increment = 0; // Gambiarra
        
        for (int i = 0; i < frames.size(); i++) {
            FrameMemory frame = frames.get(i);
            for (int offset = 0; offset < this.pageSize; offset++) {
                this.physicalMemory[frame.getPageNumber()][offset] = new SubProcess(p.getId(), NUMBER_OF_PROCESS_INSTRUCTIONS);
                increment++;
                if (increment >= p.getSubProcess().size()) {
                    break;
                }
            }
        }
        this.printStatusMemory();
        return p;
    }

    public void printStatusMemory() {
        for (int i = 0; i < this.physicalMemory.length; i++) {
            for (int j = 0; j < this.pageSize; j++) {
                if (j == (this.pageSize - 1)) {
                    System.out.println(this.physicalMemory[i][j].getId());
                } else {
                    System.out.print(this.physicalMemory[i][j].getId() + " | ");
                }
            }
        }
    }

}
