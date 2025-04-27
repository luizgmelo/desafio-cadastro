package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;

import java.util.Scanner;

public class PetRegisterView {

    private final Scanner scanner;
    private final MenuController menuController;

    public PetRegisterView(Scanner scanner, MenuController menuController) {
        this.scanner = scanner;
        this.menuController = menuController;
    }

    public void register() {
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
}
