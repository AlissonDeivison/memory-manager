package soCpu;

import so.Process;
import so.Priority;

import java.util.Random;
import java.util.UUID;

public class CpuManager {

    public Process createProcess(Process p, int processLenght) {
        Random rand = new Random();
        int sizeInMemory = processLenght;
        int timeToExecute = rand.nextInt(100); // Tempo de execução aleatório entre 0 e 99
        Priority priority = Priority.values()[rand.nextInt(Priority.values().length)]; // Prioridade aleatória
        String id = UUID.randomUUID().toString(); // ID aleatório
        return new Process(id, sizeInMemory, timeToExecute, priority);
    }

    public static void main(String[] args) {
        // Criar uma instância de CpuManager
        CpuManager cpuManager = new CpuManager();
        // Inicializar o processo aleatório
        Process processoAleatorio = null;
        // Criar um processo aleatório
        processoAleatorio = cpuManager.createProcess(processoAleatorio,0);
    }

    public Process imprimirProcesso(Process processo) {
        System.out.println("Processo criado:");
        System.out.println("ID: " + processo.getId());
        System.out.println("Tamanho na memória: " + processo.getSizeInMemory());
        System.out.println("Tempo para executar: " + processo.getTimeToExecute());
        System.out.println("Prioridade: " + processo.getPriority());
        return processo;
    }
}
