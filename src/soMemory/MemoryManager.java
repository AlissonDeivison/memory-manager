package soMemory;

import java.util.ArrayList;
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
        physicalMemory = new SubProcess [pages][pageSize]; 
        this.logicalMemory = new Hashtable<>();
    }

    public void writeProcess(Process p) {
        this.write(p);
    }

    private List<FrameMemory> getFrames(Process p) {
        int totalOfPages = p.getSizeInMemory() / this.pageSize;
        List<FrameMemory> frames = new LinkedList<>();
        for (int frame = 0; frame < physicalMemory.length; frame++) {
            if (physicalMemory[frame][0] == null) {
                frames.add(new FrameMemory(frame, 0));
                if(frames.size() >= totalOfPages){
                    return frames;
                }
            }
        }
        return null;
    }

    private void write(Process p) {
        List<FrameMemory> frames = this.getFrames(p);
        int increment = 0; //Gambiarra
        for (int i = 0; i < frames.size(); i++) {
            FrameMemory frame = frames.get(i);
            for(int offset = 0; offset < this.pageSize; offset++){
                increment++;
                this.physicalMemory[frame.getPageNumber()][offset] = 
                    new SubProcess(p.getId(), NUMBER_OF_PROCESS_INSTRUCTIONS);
                    if(increment >= p.getSubProcess().size()){
                        break;
                    }
            }
        }
    }

    public static void deleteProcess(String p) {
        
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

    public void printStatusMemory() {
        for(int i = 0; i < this.physicalMemory.length; i++){
            for (int j = 0 ; j < this.pageSize; j++){
                if(j == (this.pageSize - 1)){
                    if(this.physicalMemory[i][j] != null){
                        System.out.println(this.physicalMemory[i][j].getId());
                    } else {
                        System.out.println(this.physicalMemory[i][j]);
                    }
                } else {
                    if(this.physicalMemory[i][j] != null){
                        System.out.print(this.physicalMemory[i][j].getId() + "|");
                    } else {
                        System.out.print(this.physicalMemory[i][j] + "|");
                    }
                }
            }
        }
    }

}
