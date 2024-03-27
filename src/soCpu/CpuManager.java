package soCpu;

import java.util.Timer;
import java.util.TimerTask;

import so.SubProcess;

public class CpuManager {
    private Core[] cors;
    public static int NUM_OF_INSTRUCTIONS_PER_CLOCK = 7;
    public static int CLOCK_TIME = 500;

    
    public CpuManager (int numOfCors){
        this.cors = new Core[numOfCors];
        for (int i =0 ; i < this.cors.length; i++){
            this.cors[i] = new Core(i, NUM_OF_INSTRUCTIONS_PER_CLOCK);
        }
    }
    
    public void registerProcess(int coreId, SubProcess p){
        this.cors[coreId].setSubProcess(p);
    }

    public void clock(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                executeProcesses();
            }
        }, 500);
    }

    private void executeProcesses() {
        for(int i = 0; i < this.cors.length; i++){
            this.cors[i].run();
        }
    }
}
