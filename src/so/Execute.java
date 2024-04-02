package so;

import java.util.Scanner;

public class Execute {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (true) {
            System.out.println("\n===== Sistema Operacional XPTO - Menu Principal =====");
            System.out.println("Escolha uma opção digitando o número correspondente:");
            System.out.println("1 - Criar um novo processo e escrever na memória");
            System.out.println("2 - Deletar um processo da memória");
            System.out.println("3 - Visualizar todos os processos em execução");
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
                    // Instância o Processo e chama os Sistema Operacional
                    Process process = new Process(tamanhoDoProcesso);
                    SystemOperation.systemCall(SystemCallType.CREATE, process, tamanhoDoProcesso);
                    try {
                        SystemOperation.systemCall(SystemCallType.WRITE, process, tamanhoDoProcesso);
                        // process.getSubProcess());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao criar processo: " + e.getMessage());
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
