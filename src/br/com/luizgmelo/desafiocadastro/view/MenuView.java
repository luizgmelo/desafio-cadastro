package br.com.luizgmelo.desafiocadastro.view;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;

import java.util.*;

public class MenuView {
    final Scanner scanner;
    final MenuController menuController;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
    }

    public void showMenu() {
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
            } catch (InputMismatchException e) {
                System.out.println("\nDigite apenas um número entre 1-6");
                continue;
            } finally {
                scanner.nextLine();
            }

            switch (option) {
                case 1:
                    System.out.println("\n=== Cadastro do Pet ===");
                    // 1. Qual o nome e sobrenome do pet?
                    System.out.print(menuController.showNextQuestion());
                    String petName = scanner.nextLine();
                    menuController.validateName(petName, "nome do pet");

                    // 2. Qual o tipo do pet (Cachorro/Gato)?
                    System.out.print(menuController.showNextQuestion());
                    String type = scanner.nextLine();
                    PetType petType = menuController.validateType(type);

                    // 3. Qual o sexo do animal?
                    System.out.print(menuController.showNextQuestion());
                    String sex = scanner.nextLine();
                    PetSex petSex = menuController.validateSex(sex);

                    //  4 - Qual o nome da Rua que ele foi encontrado?
                    System.out.print(menuController.showNextQuestion());
                    String petStreet = scanner.nextLine();
                    menuController.validateStreetName(petStreet);

                    // 5 - Qual o número da casa?
                    System.out.print(menuController.showNextQuestion());
                    String petHouseNumber = scanner.nextLine();
                    petHouseNumber = menuController.validateHouseNumber(petHouseNumber);

                    // 6 - Qual a cidade?
                    System.out.print(menuController.showNextQuestion());
                    String petCity = scanner.nextLine();
                    menuController.validateName(petCity, "nome da cidade");

                    // 7. Qual a idade aproximada do pet?
                    System.out.print(menuController.showNextQuestion());
                    String petAge = scanner.nextLine();
                    petAge = menuController.validateAge(petAge);

                    // 8. Qual o peso em kilos aproximado do pet?
                    System.out.print(menuController.showNextQuestion());
                    String petWeight = scanner.nextLine();
                    petWeight = menuController.validateWeight(petWeight);

                    // 9. Qual a raça do pet?
                    System.out.print(menuController.showNextQuestion());
                    String petBreed = scanner.nextLine();
                    menuController.validateName(petBreed, "raça");

                    menuController.addPet(petName, petType, petSex,
                            petStreet, petHouseNumber, petCity,
                            petAge, petWeight, petBreed);

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
//                    petService.searchPet(scanner);
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
