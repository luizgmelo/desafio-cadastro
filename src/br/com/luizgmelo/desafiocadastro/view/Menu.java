package br.com.luizgmelo.desafiocadastro.view;

import br.com.luizgmelo.desafiocadastro.service.PetService;

import java.util.*;

public class Menu {

    public Menu() {
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        PetService petService = new PetService();


        int option = -1;
        do {
            System.out.println("\n==== Sistema de Cadastro de Pet ====\n");
            System.out.println("1. Cadastrar um novo pet");
            System.out.println("2. Alterar os dados do pet cadastrado");
            System.out.println("3. Deletar um pet cadastrado");
            System.out.println("4. Listar todos os pets cadastrados");
            System.out.println("5. Listar pets por algum critério (idade, nome, raça)");
            System.out.println("6. Sair");
            System.out.print("Digite uma das opções acima: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\nDigite apenas um número entre 1-6");
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("\n=== Cadastro do Pet ===");
                    petService.registerPet(scanner);
                    break;
                case 2:
                    // TODO Implementar alterar cadastro de pet
                    break;
                case 3:
                    // TODO Implementar deleção de pet
                    break;
                case 4:
                    // TODO Implementar listar todos os pets
                    break;
                case 5:
                    // TODO Implementar listar pets por critérios
                    System.out.println("== Busca de Pet ==");
                    petService.searchPet(scanner);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("\nDigite apenas um número entre 1-6");
            }
        } while (option != 6);
    }

    public static Map<String, String> showSearchMenu(Scanner scanner) {
        List<String> options = Arrays.asList("Nome", "Sexo", "Idade", "Peso", "Raça", "Endereço");
        Map<String, String> map = new HashMap<>();


        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }


        for (int i = 0; i < 2; i++) {
            System.out.print("Escolha o " + (i+1) + "° critério: ");
            int criteria;
            try {
                criteria = scanner.nextInt();
            } catch (RuntimeException e) {
                throw new RuntimeException("Escolha uma opção entre 1-6");
            } finally {
                scanner.nextLine();
            }

            System.out.print("Digite o valor de " + options.get(criteria - 1) + ": ");
            String value = scanner.nextLine();

            map.put(options.get(criteria - 1), value);

            if (map.size() == 1) {
                System.out.print("Deseja selecionar mais um critério? (S/N)");
                String res = scanner.nextLine();

                if (res.charAt(0) == 'N' || res.charAt(0) == 'n') {
                    break;
                }
            }
        }

        return map;
    }
}
