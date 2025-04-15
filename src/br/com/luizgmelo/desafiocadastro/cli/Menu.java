package br.com.luizgmelo.desafiocadastro.cli;

import br.com.luizgmelo.desafiocadastro.repository.PetRepository;
import br.com.luizgmelo.desafiocadastro.service.PetService;
import br.com.luizgmelo.desafiocadastro.service.ValidateService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        PetService petService = new PetService(new ValidateService(), new PetRepository());

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
                    System.out.println("== Busca de Pet ==");
                    petService.searchPet(scanner);
                    break;
                case 3:
                    // TODO Implementar deleção de pet
                    break;
                case 4:
                    // TODO Implementar listar todos os pets
                    break;
                case 5:
                    // TODO Implementar listar pets por critérios
                    break;
                default:
                    System.out.println("\nDigite apenas um número entre 1-6");
            }
        } while (option != 6);
    }

    public static void showSearchMenu() {
        System.out.println("1. Nome ou sobrenome");
        System.out.println("2. Sexo");
        System.out.println("3. Idade");
        System.out.println("4. Peso");
        System.out.println("5. Raça");
        System.out.println("6. Endereço");
    }
}
