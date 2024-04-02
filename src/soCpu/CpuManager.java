package soCpu;

import java.util.Timer;
import java.util.TimerTask;

import so.SubProcess;

public class CpuManager {
    private Core[] cores;
    public static int NUM_OF_INSTRUCTIONS_PER_CLOCK = 7;
    public static int CLOCK_TIME = 500;

    
    public CpuManager (int numOfCors){
        this.cores = new Core[numOfCors];
        for (int i =0 ; i < this.cores.length; i++){
            this.cores[i] = new Core(i, NUM_OF_INSTRUCTIONS_PER_CLOCK);
        }
        //this.clock();
    }
    
    public void registerProcess(int coreId, SubProcess p){
        this.cores[coreId].setSubProcess(p);
    }

    private void clock(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                executeProcesses();
            }
        }, 500);
    }

    private void executeProcesses() {
        for(int i = 0; i < this.cores.length; i++){
            this.cores[i].run();
        }
    }
}
