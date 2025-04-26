package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.InputService;

import java.util.*;

public class MenuView {
    final Scanner scanner;
    final MenuController menuController;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
    }

    public void menu() {
        System.out.println("\n==== Sistema de Cadastro de Pet ====\n");
        System.out.println("1. Cadastrar um novo pet");
        System.out.println("2. Listar pets por algum critério (idade, nome, raça)");
        System.out.println("3. Listar todos os pets cadastrados");
        System.out.println("4. Alterar os dados do pet cadastrado");
        System.out.println("5. Deletar um pet cadastrado");
        System.out.println("6. Sair");
        System.out.print("Digite uma das opções acima: ");
    }


    public void showMenu() {
        int option;
        do {
            menu();

            option = InputService.readInt(scanner);

            switch (option) {
                case 1:
                    registerPet();
                    break;
                case 2:
                    showPetList(searchPet());
                    break;
                case 3:
                    listAllPets();
                    break;
                case 4:
                    updatePet();
                    break;
                case 5:
                    // TODO Implementar deleção de pet
                    break;
            }
        } while (option != 6);
    }

    private void updatePet() {
        List<Pet> petsFiltered = searchPet();

        showPetList(petsFiltered);

        if (petsFiltered.isEmpty()) {
            return;
        }


        int petOption = -1;
        do {
            System.out.printf("\nEscolha o número do pet na listagem para alterar o cadastro entre (%d-%d): ", 1, petsFiltered.size());

            try {
                petOption = scanner.nextInt();

                if (petOption < 1 || petOption > petsFiltered.size()) {
                    System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                    showPetList(petsFiltered);
                }
            } catch (InputMismatchException ignored) {
                System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                showPetList(petsFiltered);
            } finally {
                scanner.nextLine();
            }
        } while (petOption < 1 || petOption > petsFiltered.size());
    }

    private void listAllPets() {
        showPetList(menuController.getListAllPets());
    }

    private List<Pet> searchPet() {
        Map<String, String> criterias = new HashMap<>();
        List<String> options = Arrays.asList("Nome", "Sexo", "Idade", "Peso", "Raca", "Endereco");

        System.out.print("Qual o tipo do pet? (Gato/Cachorro)? ");
        PetType petTypeSearch = menuController.validateType(scanner.nextLine());

        do {
            showSearchMenu(options);

            System.out.print("Escolha o " + (criterias.size() + 1) + "° critério: ");
            int criteria = InputService.readInt(scanner);

            if (criteria >= 1 && criteria <= 6) {
                String option = options.get(criteria - 1);
                System.out.print("Digite o valor de " + option + ": ");
                String value = scanner.nextLine();
                menuController.validateValue(value, option);

                criterias.put(option, value);

                if (criterias.size() == 1) {
                    System.out.print("Deseja selecionar mais um critério? (S/N)");
                    String res = scanner.nextLine();

                    if (res.charAt(0) == 'N' || res.charAt(0) == 'n') {
                        break;
                    }
                }
            }

        } while (criterias.size() != 2);

        List<Pet> petFiltered = menuController.searchPet(petTypeSearch, criterias);

        return petFiltered;
    }

    private void registerPet() {
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

        boolean isCreated = menuController.addPet(petName, petType, petSex,
                petStreet, petHouseNumber, petCity,
                petAge, petWeight, petBreed);

        if (isCreated) {
            System.out.println("\n Cadastro realizado com sucesso!");
        } else {
            System.out.println("\n Não foi possível fazer o cadastro");
        }
    }

    private void showPetList(List<Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("Não foi encontrado nenhum pet!");
        } else {
            for (int i = 0; i < pets.size(); i++) {
                System.out.println(i + 1 + ". " + pets.get(i).getName() + " - " +
                        pets.get(i).getSex() + " - " +
                        pets.get(i).getStreetName() + ", " + pets.get(i).getHouseNumber() + ", " + pets.get(i).getCity() + " - " +
                        pets.get(i).getAge() + " - " +
                        pets.get(i).getWeight() + " - " +
                        pets.get(i).getBreed());
            }
        }
    }

    public void showSearchMenu(List<String> menuOptions) {
        System.out.println("\n==== Busca de Pet ====");
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println((i + 1) + ". " + menuOptions.get(i));
        }
    }
}
