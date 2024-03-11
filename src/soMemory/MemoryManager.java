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
        int maxSize = -1;
        int start = -1;
        int i = 0;
  
        while(true) {
           int j;
           while(i < physicMemory.length) {
              if (physicMemory[i] == null) {
                 for(j = i; j < physicMemory.length && physicMemory[j] == null; ++j) {
                 }
  
                 int blockSize = j - i;
                 if (blockSize >= p.getSizeInMemory() && blockSize > maxSize) {
                    maxSize = blockSize;
                    start = i;
                 }
  
                 i = j;
              } else {
                 ++i;
              }
           }
  
           if (start != -1) {
              i = start + p.getSizeInMemory() - 1;
  
              for(j = start; j <= i; ++j) {
                 physicMemory[j] = p.getId();
              }
  
              printStatusMemory(p);
           } else {
              System.out.println("Sem espaço suficiente para alocar o processo: " + p.getId());
           }
  
           return;
        }
     }

    /* 
    private void writeProcessUsingWorstFit(Process p) {
        int maxSize = -1;
        int start = -1;
    
        // Procura por um bloco disponível no início da memória
        for (int i = 0; i < physicMemory.length && physicMemory[i] == null; i++) {
            int j = i;
            while (j < physicMemory.length && physicMemory[j] == null) {
                j++;
            }
            int blockSize = j - i;
            if (blockSize >= p.getSizeInMemory() && blockSize > maxSize) {
                maxSize = blockSize;
                start = i;
            }
            i = j;
        }
    
        // Se não encontrou no início, busca pelo maior bloco em qualquer posição
        if (start == -1) {
            for (int i = 0; i < physicMemory.length; ) {
                if (physicMemory[i] == null) {
                    int j = i;
                    while (j < physicMemory.length && physicMemory[j] == null) {
                        j++;
                    }
                    int blockSize = j - i;
                    if (blockSize >= p.getSizeInMemory() && blockSize > maxSize) {
                        maxSize = blockSize;
                        start = i;
                    }
                    i = j;
                } else {
                    i++;
                }
            }
        }
    
        if (start != -1) {
            int end = start + p.getSizeInMemory() - 1;
            for (int j = start; j <= end; j++) {
                physicMemory[j] = p.getId();
            }
            printStatusMemory(p);
        } else {
            System.out.println("Não há espaço suficiente na memória para alocar o processo: " + p.getId());
        }
    }
    */
    private void writeProcessUsingBestFit(Process p) {
        int minSize = Integer.MAX_VALUE;
        int start = -1;

        for (int i = 0; i < physicMemory.length; ) {
            if (physicMemory[i] == null) {
                int j = i;
                while (j < physicMemory.length && physicMemory[j] == null) {
                    j++;
                }
                int blockSize = j - i;
                if (blockSize >= p.getSizeInMemory() && blockSize < minSize) {
                    minSize = blockSize;
                    start = i;
                }
                i = j + 1;
            } else {
                i++;
            }
        }

        if (start != -1) {
            int end = start + p.getSizeInMemory() - 1;
            for (int j = start; j <= end; j++) {
                physicMemory[j] = p.getId();
            }
            printStatusMemory(p);
        } else {
            System.out.println("Não há espaço suficiente na memória para alocar o processo: " + p.getId());
        }
    }

    public static void deleteProcess(String p) {
        try {
            for (int i = 0; i < physicMemory.length; i++) {
                if (physicMemory[i] != null && physicMemory[i].equals(p)) {
                    physicMemory[i] = null;
                    break; // Interrompe o loop após excluir o processo
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao excluir o processo: " + e.getMessage());
        }
    }

    public void readProcess(Process p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readProcess'");
    }

    private static void printStatusMemory(Process p) {
        System.out.println(
                "Processo: " + p.getId() + " | Tamanho: " + p.getSizeInMemory() + " | " + p.getPriority() + "\n");
        System.out.println("Status da memória:");
        for (int i = 0; i < physicMemory.length; i++) {
            System.out.print(physicMemory[i] + "-");
        }
        System.out.println("\n");
    }

}
