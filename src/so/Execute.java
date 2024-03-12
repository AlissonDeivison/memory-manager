package so;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import soMemory.MemoryManager;

public class Execute {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (true) {
            System.out.println("\nBem vindo ao sistema operacional");
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Criar processo e escrever na memória");
            System.out.println("2 - Deletar processo existente");
            System.out.println("3 - Verificar processos existentes na memória");
            System.out.println("4 - Sair do sistema");

            try {
                option = Integer.parseInt(scanner.nextLine());
                if (option < 1 || option > 4) {
                    System.out.println("Opção inválida, digite novamente:");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, digite novamente:");
                continue;
            }

            if (option == 1) {
                System.out.println("Digite o tamanho do processo: ");
                int tamanhoDoProcesso = Integer.parseInt(scanner.nextLine());
                try {
                    Process p = SystemOperation.systemCall(SystemCallType.CREATE, null, tamanhoDoProcesso);
                    try {
                        SystemOperation.systemCall(SystemCallType.WRITE, p, tamanhoDoProcesso);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao criar processo: " + e.getMessage());
                }
            } else if (option == 2) {
                String[] processList = MemoryManager.getPhysicMemory();
                Set<String> processosUnicos = new HashSet<>();
                for (String processo : processList) {
                    if (processo != null) {
                        processosUnicos.add(processo);
                    }
                }
                // Relata os processos únicos
                System.out.println("Processos existentes na memória:");
                for (String processoUnico : processosUnicos) {
                    System.out.println("Processo " + processoUnico);
                }
                System.out.println("Digite o nome do processo que deseja deletar:");
                String processoDeletar = scanner.nextLine();
                for (int i = 0; i < processList.length; i++) {
                    try {
                        if (processList[i] != null && processList[i].equals(processoDeletar)) {
                            MemoryManager.deleteProcess(processoDeletar);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else if (option == 3) {
                // Lógica para verificar processos existentes na memória
                String[] physicMemory = MemoryManager.getPhysicMemory();
                try {
                        System.out.println("Status da memória:");
                        for (String processo : physicMemory) {
                            System.out.print(processo + "-");
                        }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else if (option == 4) {
                break;
            }
        }
        scanner.close();
    }
}
