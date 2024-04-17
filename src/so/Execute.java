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
            SOProcess process = SystemOperation.SystemCall(SystemCallType.CREATE, processSize);
            SystemOperation.SystemCall(SystemCallType.WRITE, process);

        } catch (NumberFormatException e) {
            System.out.println("Tamanho do processo inválido, digite um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao criar e escrever na memória: " + e.getMessage());
        }
    }

    private static void executeExistingProcesses() {
        //1 - SJF 2 - Prioridade 3- FCFS 4 - Loteria
        ArrayList<SOProcess> listProcess = SystemOperation.getAllProcess(4); //Mudar a forma como eu recebo os processo
        if (listProcess == null || listProcess.isEmpty()) { 
            System.out.println("Não há processos na memória para executar.");
        } else {
            for (int i = 0; i < listProcess.size(); i++){
                try {
                    SOProcess p = listProcess.get(i);
                    System.out.println("Executando processo " + p.getId() + "Prioridade: " + p.getPriority());
                    SystemOperation.executeProcesses(p);
                    //System.out.println("Processo finalizado " + p.getId() + p.getSubProcess());

                    
                    // Adiciona uma pausa após a execução de cada processo
                    System.out.println("Iniciando a execução de processos, precione Enter após a finalização");
                    scanner.nextLine();
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
