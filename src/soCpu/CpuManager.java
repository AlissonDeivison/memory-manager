package soCpu;

import java.util.Timer;
import java.util.TimerTask;

import so.SubProcess;

public class CpuManager {
    private Core[] cores;
    public static int NUM_OF_INSTRUCTIONS_PER_CLOCK = 7;
    public static int CLOCK_TIME = 2000;
    public static int NUM_OF_CORES = 4;

    
    public CpuManager (){
        this.cores = new Core[NUM_OF_CORES];
        for (int i =0 ; i < this.cores.length; i++){
            this.cores[i] = new Core(i, NUM_OF_INSTRUCTIONS_PER_CLOCK);
        }
        this.clock();
    }
    
    public void registerProcess(int coreId, SubProcess p){
        this.cores[coreId].setSubProcess(p);
    }

    private void clock(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                executeProcesses();
            }
        }, 0, CLOCK_TIME);
    }

    private void executeProcesses() {
        for(int i = 0; i < this.cores.length; i++){
            this.cores[i].run();
        }
    }

    public Core[] getCores() {
        return cores;
    }
    
}
