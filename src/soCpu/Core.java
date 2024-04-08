package soCpu;

import so.SubProcess;

public class Core implements Runnable {

    private int id;
    private SubProcess subProcess;
    private int numOfInstructions;

    private int count;

    public Core(int coreId, int numInstructions) {
        this.id = coreId;
        this.numOfInstructions = numInstructions;
    }

    @Override
    public void run() {
        if (subProcess != null) {
            this.count += numOfInstructions;
            if (this.count >= this.subProcess.getInstructions()) {
                this.finishExecution();
            }
        }
    }

    private void finishExecution() {
        this.subProcess = null;
        this.count = 0;
    }

    public void setSubProcess(SubProcess p) {
        this.subProcess = p;
    }

    public SubProcess getSubProcess() {
        return this.subProcess;
    }

    public int getId() {
        return this.id;
    }
}
