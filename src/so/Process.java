package so;

import java.util.LinkedList;
import java.util.List;

public class Process {
    private String id;
    private int sizeInMemory;
    private List<String> subProcess;

    private static int countIndex = 0;

    public Process(int sizeInMemory, int timeToExecute, Priority priority) {
        countIndex++;
        this.id = "P" + countIndex;
        this.sizeInMemory = sizeInMemory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSizeInMemory() {
        return sizeInMemory;
    }

    public void setSizeInMemory(int sizeInMemory) {
        this.sizeInMemory = sizeInMemory;
    }
    //Função para fatiar o processo
    public List<String> getSubProcess(){
        if(this.subProcess == null || this.subProcess.isEmpty()){
            this.subProcess = new LinkedList<>();
            for (int i =0; i < this.getSizeInMemory(); i++){
                this.subProcess.add(id + i);
            }
        }
        return this.subProcess;
    }

}
