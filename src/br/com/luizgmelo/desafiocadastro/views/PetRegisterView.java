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
        String petName = readQuestionAndAnswer();
        menuController.validateName(petName, "nome do pet");

        // 2. Qual o tipo do pet (Cachorro/Gato)?
        String type = readQuestionAndAnswer();
        PetType petType = menuController.validateType(type);

        // 3. Qual o sexo do animal?
        String sex = readQuestionAndAnswer();
        PetSex petSex = menuController.validateSex(sex);

        //  4 - Qual o nome da Rua que ele foi encontrado?
        String petStreet = readQuestionAndAnswer();
        menuController.validateStreetName(petStreet);

        // 5 - Qual o número da casa?
        String petHouseNumber = readQuestionAndAnswer();
        petHouseNumber = menuController.validateHouseNumber(petHouseNumber);

        // 6 - Qual a cidade?
        String petCity = readQuestionAndAnswer();
        menuController.validateName(petCity, "nome da cidade");

        // 7. Qual a idade aproximada do pet?
        String petAge = readQuestionAndAnswer();
        petAge = menuController.validateAge(petAge);

        // 8. Qual o peso em kilos aproximado do pet?
        String petWeight = readQuestionAndAnswer();
        petWeight = menuController.validateWeight(petWeight);

        // 9. Qual a raça do pet?
        String petBreed = readQuestionAndAnswer();
        menuController.validateName(petBreed, "raça");

        menuController.addPet(petName, petType, petSex, petStreet, petHouseNumber, petCity, petAge, petWeight, petBreed);

        System.out.println("\n Cadastro realizado com sucesso!");
    }

    private String readQuestionAndAnswer() {
        System.out.println(menuController.getNextQuestion());
        return scanner.nextLine();
    }
}
