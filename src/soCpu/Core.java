package soCpu;

import so.SubProcess;

public class Core implements Runnable {

    private int id;
    private SubProcess subProcess;
    private int numInstructions;

    private int count;

    public Core(int coreId,int numInstructions) {
        this.id = coreId;
        this.numInstructions = numInstructions;
    }

    @Override
    public void run() {
        count += numInstructions;
        if(count >= subProcess.getInstructions()){
            this.finishExecution();
        }
        
    }

    private void finishExecution(){
        this.subProcess = null;
        this.count = 0;
    }

    public void setSubProcess(SubProcess p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSubProcess'");
    }

}
