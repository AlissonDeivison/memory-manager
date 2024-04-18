package so;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Execute {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        while (true) {
            printMainMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        executeProcessCreationMenu();
                        break;
                    case 2:
                        deleteProcess();
                        break;
                    case 3:
                        viewProcesses();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, digite novamente.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n===== Sistema Operacional XPTO - Menu Principal =====");
        System.out.println("1 - Criar um novo processo e escrever na memória");
        System.out.println("2 - Deletar um processo da memória");
        System.out.println("3 - Visualizar todos os processos em execução");
        System.out.println("4 - Sair do sistema");
        System.out.print("Escolha uma opção digitando o número correspondente: ");
    }

    private static void executeProcessCreationMenu() {
        int option;
        while (true) {
            printProcessCreationMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        createAndWriteToMemory();
                        break;
                    case 2:
                        executeExistingProcesses();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opção inválida, digite novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, digite novamente.");
            }
        }
    }

    private static void printProcessCreationMenu() {
        System.out.println("\n===== Sistema Operacional XPTO - Criação e Execução de processos =====");
        System.out.println("1 - Criar um novo processo e escrever na memória");
        System.out.println("2 - Executar processos existente na memória");
        System.out.println("3 - Voltar ao menu principal");
        System.out.print("Escolha uma opção digitando o número correspondente: ");
    }

    private static void createAndWriteToMemory() {
        try {
            System.out.print("Digite o tamanho do processo que deseja criar: ");
            int processSize = Integer.parseInt(scanner.nextLine());
            System.out.println("Digite a prioridade do processo:  [0] - Crítico, [1] - Alta, [2] - Média, [3] - Baixa");
            int priority = Integer.parseInt(scanner.nextLine());
            System.out.println("Digite o tempo de execução do processo: ");
            int timeToExecute = Integer.parseInt(scanner.nextLine());
            SOProcess process = SystemOperation.SystemCall(SystemCallType.CREATE, processSize, priority, timeToExecute);
            SystemOperation.SystemCall(SystemCallType.WRITE, process);

        } catch (NumberFormatException e) {
            System.out.println("Tamanho do processo inválido, digite um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao criar e escrever na memória: " + e.getMessage());
        }
    }

    private static void executeExistingProcesses() {
        int algorithmOption;
        while (true) {
            System.out.println("Escolha o algoritmo de escalonamento:");
            System.out.println("1 - SJF");
            System.out.println("2 - Prioridade");
            System.out.println("3 - FCFS");
            System.out.println("4 - Loteria");
            System.out.print("Digite o número correspondente ao algoritmo desejado: ");

            try {
                algorithmOption = Integer.parseInt(scanner.nextLine());
                if (algorithmOption < 1 || algorithmOption > 4) {
                    System.out.println("Opção inválida, digite novamente.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, digite novamente.");
            }
        }

        ArrayList<SOProcess> listProcess = SystemOperation.getAllProcess(algorithmOption);
        ArrayList<String> orderedProcess = new ArrayList<String>();
        for(int i = 0; i < listProcess.size(); i++){
            orderedProcess.add(listProcess.get(i).getId());
        }
        System.out.println("Fila de processos:" + orderedProcess);
        
        if (listProcess == null || listProcess.isEmpty()) {
            System.out.println("Não há processos na memória para executar.");
        } else {
            for (int i = 0; i < listProcess.size(); i++) {
                try {
                    SOProcess p = listProcess.get(i);
                    if (p.getSubProcess().isEmpty()) {
                        break;
                    } else {
                        System.out.println("Executando processo " + p.getId());
                        SystemOperation.executeProcesses(p);
                        System.out.println("Iniciando a execução de processos, pressione Enter após a finalização");
                        scanner.nextLine();
                    }

                    // Adiciona uma pausa após a execução de cada processo
                } catch (Exception e) {
                    System.out.println("Erro ao executar processo: " + e.getMessage());
                }
            }
        }
    }

    private static void deleteProcess() {
        Set<String> processes = SystemOperation.getUniqueProcesses();
        if (processes == null || processes.isEmpty()) {
            System.out.println("Não há processos na memória para deletar.");
        } else {
            System.out.println("Processos existentes na memória: " + processes);
            System.out.print("Digite o ID do processo que deseja deletar: ");
            String processId = scanner.nextLine();
            try {
                SystemOperation.removeProcessFromMemory(processId);
                System.out.println("Processo deletado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao deletar processo: " + e.getMessage());
            }
        }
    }

    private static void viewProcesses() {
        System.out.println("Processos existentes na memória: " + SystemOperation.getUniqueProcesses());
        System.out.println("Status da memória: " + SystemOperation.statusMemory());
    }
}
