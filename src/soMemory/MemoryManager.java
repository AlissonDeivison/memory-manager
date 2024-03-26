package soMemory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import so.Process;
import so.SubProcess;

public class MemoryManager {
    private SubProcess[][] physicalMemory;
    private Hashtable<String, FrameMemory> logicalMemory;
    private int pageSize;

    public MemoryManager(int pageSize, int memorySize) {
        this.pageSize = pageSize;
        int pages = memorySize / pageSize;
        physicalMemory = new SubProcess [pages][pageSize]; 
        this.logicalMemory = new Hashtable<>();
    }

    public void writeProcess(Process p) {
        this.writeProcessUsingPaging(p);
    }

    private List<FrameMemory> getFrames(Process p) {
        int increment = 0;
        List<FrameMemory> adresses = new ArrayList<>();
        for (int frame = 0; frame < physicalMemory.length; frame += this.pageSize) {
            if (physicalMemory[frame] == null) {
                increment += this.pageSize;
                adresses.add(new FrameMemory(frame, this.pageSize));
                if (increment >= p.getSizeInMemory()) {
                    return adresses;
                }
            }
        }
        return null;
    }

    private void writeProcessUsingPaging(Process p) {
        
        
        
        
        
        
        
        
        
        
        List<FrameMemory> adresses = this.getFrames(p);
        if (adresses == null) {
            System.out.println("Não há espaço suficiente na memória para alocar o processo: " + p.getId());
            return;
        } else {
            for (int i = 0; i < adresses.size(); i++) {
                FrameMemory frame = adresses.get(i);
                int start = frame.getPageNumber();
                int end = start + frame.getDisplacement(); //- 1;
                for(int j = start; j < end; j++){
                    this.physicalMemory[j] = p.getId();
                }
            }
            this.logicalMemory.put(p.getId(), adresses);
        }

    }

    public static void deleteProcess(String p) {
        try {
            for (int i = 0; i < physicalMemory.length; i++) {
                if (physicalMemory[i] != null && physicalMemory[i].equals(p)) {
                    physicalMemory[i] = null;
                    break; // Interrompe o loop após excluir o processo
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao excluir o processo: " + e.getMessage());
        }
    }

    public List<String> readProcess(Process p) {
        List <String> processParts = new ArrayList<String>();
        List<FrameMemory> adresses = this.logicalMemory.get(p.getId());
        for (int i = 0; i < adresses.size(); i++) {
            FrameMemory frame = adresses.get(i);
            int start = frame.getPageNumber();
            int end = start + frame.getDisplacement(); //- 1;
            for(int j = start; j < end; j++){
                processParts.add(this.physicalMemory[j]);
            }
        }
        return processParts;
    }

    private static void printStatusMemory(Process p) {
        System.out.println(
                "Processo: " + p.getId() + " | Tamanho: " + p.getSizeInMemory() + " | " + p.getPriority() + "\n");
        System.out.println("Status da memória:");
        for (int i = 0; i < physicalMemory.length; i++) {
            System.out.print(physicalMemory[i] + "-");
        }
        System.out.println("\n");
    }

}
