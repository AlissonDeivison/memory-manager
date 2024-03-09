package soMemory;

import so.Process;

public class MemoryManager {
    private static String[] physicMemory;
    private Strategy strategy;

    public MemoryManager(Strategy strategy) {
        this.strategy = strategy;
        MemoryManager.physicMemory = new String[128];
    }

    public void writeProcess(Process p) {
        if (this.strategy.equals(Strategy.BEST_FIT)) {
            this.writeProcessUsingBestFit(p);
        } else if (this.strategy.equals(Strategy.FIRST_FIT)) {
            this.writeProcessUsingFirstFit(p);
        } else if (this.strategy.equals(Strategy.WORST_FIT)) {
            this.writeProcessUsingWorstFit(p);
        }
    }
    
    public static String[] getPhysicMemory() {
        return physicMemory;
    }
    
    private void writeProcessUsingFirstFit(Process p) {
        int start = 0;
        int actualSize = 0;

        for (int i = 0; i < physicMemory.length; i++) {
            if (actualSize >= p.getSizeInMemory()) {
                // Se já alocamos espaço suficiente para o processo, saímos do loop
                break;
            }

            if (physicMemory[i] == null) {
                // Se o espaço atual estiver vazio, aumentamos o tamanho atual
                actualSize++;
            } else {
                // Se encontrarmos um espaço ocupado, redefinimos o tamanho atual para 0
                actualSize = 0;
                start = i + 1; // Avançamos o início para o próximo espaço vazio
            }
        }

        // Verificamos se encontramos espaço suficiente para o processo
        if (actualSize >= p.getSizeInMemory()) {
            // Alocamos o processo na memória
            int end = start + p.getSizeInMemory() - 1; // Endereço final do processo
            for (int j = start; j <= end; j++) {
                physicMemory[j] = p.getId();
            }
            printStatusMemory(p); // Imprime o estado da memória após a alocação do processo
        } else {
            // Não há espaço suficiente para o processo na memória
            System.out.println("Não há espaço suficiente na memória para alocar o processo: " + p.getId());
        }
    }

    private void writeProcessUsingWorstFit(Process p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeProcessUsingFirstFit'");
    }

    private void writeProcessUsingBestFit(Process p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeProcessUsingBestFit'");
    }

    public static void deleteProcess(String p) {
        for (int i = 0; i < physicMemory.length; i++) {
            if (physicMemory[i] != null) {
                try {
                    String processId = physicMemory[i];
                    if (physicMemory[i].equals(processId)) {
                        physicMemory[i] = null;
                        System.out.println("Deletando processo " + physicMemory[i] + " na posição " + i);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                physicMemory[i] = null;
                break; // Interrompe o loop após excluir o processo
            }
        }
    }
    
    public void readProcess(Process p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readProcess'");
    }

    private void printStatusMemory(Process p) {
        System.out.println("Status da memória:");
        System.out.println(p.getId() + " | Tamanho: " + p.getSizeInMemory() + " || " + p.getPriority());
        for (int i = 0; i < physicMemory.length; i++) {
            System.out.print(physicMemory[i] + "||");
        }
    }

    
}
