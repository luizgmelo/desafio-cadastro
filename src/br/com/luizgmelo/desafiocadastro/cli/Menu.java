package br.com.luizgmelo.desafiocadastro.cli;

import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.repository.PetRepository;
import br.com.luizgmelo.desafiocadastro.service.PetService;
import br.com.luizgmelo.desafiocadastro.service.ValidateService;

import java.util.Scanner;

public class Menu {
    public void showMenu() {
        System.out.println("==== Sistema de Cadastro de Pet ====");
        System.out.println("1. Cadastrar um novo pet");
        System.out.println("2. Alterar os dados do pet cadastrado");
        System.out.println("3. Deletar um pet cadastrado");
        System.out.println("4. Listar todos os pets cadastrados");
        System.out.println("5. Listar pets por algum critério (idade, nome, raça)");
        System.out.println("6. Sair");
        System.out.print("Digite uma das opções acima: ");

        Scanner scanner = new Scanner(System.in);
        PetService petService = new PetService(new ValidateService(), new PetRepository());

        int option;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("\nDigite apenas o número da opção.\n");
                this.showMenu();
                scanner.next();
            }

            option = scanner.nextInt();
            System.out.print("\n");

            switch (option) {
                case 1:
                    petService.registerPet();
                    break;
                case 2:
                    System.out.println("Você escolheu a opção 2");
                    break;
                case 3:
                    System.out.println("Você escolheu a opção 3");
                    break;
                case 4:
                    System.out.println("Você escolheu a opção 4");
                    break;
                case 5:
                    System.out.println("Você escolheu a opção 5");
                    break;
                case 6:
                    System.out.println("Você escolheu a opção 6");
                    break;
                default:
                    System.out.println("Opção inválida por favor digite um número entre 1 e 6");

                    System.out.print("\n");
            }
        } while (option < 1 || option > 6);
    }
}
