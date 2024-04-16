package soCpu;

import so.SOProcessListener;
import so.SubProcess;

public class Core implements Runnable {

    private int id;
    private SubProcess subProcess;
    private int numOfInstructions;

    private int count;

    private SOProcessListener listener;

    public Core(int coreId, int numInstructions, SOProcessListener listener) {
        this.id = coreId;
        this.numOfInstructions = numInstructions;
        this.listener = listener;
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
        this.listener.coreExecuted(this.getId());
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
