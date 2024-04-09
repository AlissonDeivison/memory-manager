package so;

import java.util.Scanner;

public class Execute {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (true) {
            System.out.println("\n===== Sistema Operacional XPTO - Menu Principal =====");
            System.out.println("1 - Criar um novo processo e escrever na memória");
            System.out.println("2 - Deletar um processo da memória");
            System.out.println("3 - Visualizar todos os processos em execução");
            System.out.println("4 - Sair do sistema");
            System.out.print("Escolha uma opção digitando o número correspondente: ");

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
                while (true) {
                    System.out.println("\n===== Sistema Operacional XPTO - Criação e Execução de processos =====");
                    System.out.println("1 - Criar um novo processo e escrever na memória");
                    System.out.println("2 - Executar processos existente na memória");
                    System.out.println("3 - Voltar ao menu principal");
                    System.out.print("Escolha uma opção digitando o número correspondente: ");

                    try {
                        option = Integer.parseInt(scanner.nextLine());
                        if (option < 1 || option > 3) {
                            System.out.println("Opção inválida, digite novamente:");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Opção inválida, digite novamente:");
                        continue;
                    }
                    if (option == 1) {
                        System.out.print("Digite o tamanho do processo que deseja criar: ");
                        int processSize = Integer.parseInt(scanner.nextLine());
                        SOProcess process = SystemOperation.SystemCall(SystemCallType.CREATE, processSize);
                        try {
                            SystemOperation.SystemCall(SystemCallType.WRITE, process);
                        } catch (Exception e) {
                            System.out.println("Erro ao escrever na memória: " + e.getMessage());
                        }
                        
                    } else if (option == 2) {
                        // Buscar todos os processos existentes na memória e adicionar a uma lista
                        if (SystemOperation.getUniqueProcesses() == null) {
                            System.out.println("Não há processos na memória para executar");
                        } else {
                            //Executar um processo baseado no Id informado pelo usuario
                            System.out.println("Processos existentes na memória: " + SystemOperation.getUniqueProcesses());
                            System.out.println("Digite o ID do processo que deseja executar: ");
                            String processoExecutar = scanner.nextLine();
                            try {
                                SystemOperation.executeProcesses(SystemOperation.getProcess(processoExecutar));
                                System.out.println("Processo executado com sucesso!");
                            } catch (Exception e) {
                                System.out.println("Erro ao executar processo: " + e.getMessage());
                            }
                        }
                        
                    } else if (option == 3) {
                        break;
                    }
                }
            } else if (option == 2) {

                if (SystemOperation.getUniqueProcesses() == null) {
                    System.out.println("Não há processos na memória para deletar");
                } else {
                    System.out.println("Processos existentes na memória: " + SystemOperation.getUniqueProcesses());
                    System.out.println("Digite o ID do processo que deseja deletar: ");
                    String processoDeletar = scanner.nextLine();
                    try {
                        SystemOperation.removeProcessFromMemory(processoDeletar);
                        System.out.println("Processo deletado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao deletar processo: " + e.getMessage());
                    }
                }
            } else if (option == 3) {
                System.out.println(SystemOperation.getUniqueProcesses());
                System.out.println(SystemOperation.statusMemory());

            } else if (option == 4) {
                break;
            }
        }
        scanner.close();
    }
}
