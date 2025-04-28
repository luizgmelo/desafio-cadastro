package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.ValidateTypes;

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
        menuController.validateValue(petName, ValidateTypes.NOME);

        // 2. Qual o tipo do pet (Cachorro/Gato)?
        String type = readQuestionAndAnswer();
        PetType petType = (PetType) menuController.validateValue(type, ValidateTypes.TIPO);

        // 3. Qual o sexo do animal?
        String sex = readQuestionAndAnswer();
        PetSex petSex = (PetSex) menuController.validateValue(sex, ValidateTypes.SEXO);

        //  4 - Qual o nome da Rua que ele foi encontrado?
        String petStreet = readQuestionAndAnswer();
        menuController.validateValue(petStreet, ValidateTypes.RUA);

        // 5 - Qual o número da casa?
        String petHouseNumber = readQuestionAndAnswer();
        petHouseNumber = (String) menuController.validateValue(petHouseNumber, ValidateTypes.NUMEROCASA);

        // 6 - Qual a cidade?
        String petCity = readQuestionAndAnswer();
        menuController.validateValue(petCity,  ValidateTypes.CIDADE);

        // 7. Qual a idade aproximada do pet?
        String petAge = readQuestionAndAnswer();
        petAge = (String) menuController.validateValue(petAge, ValidateTypes.IDADE);

        // 8. Qual o peso em kilos aproximado do pet?
        String petWeight = readQuestionAndAnswer();
        petWeight = (String) menuController.validateValue(petWeight, ValidateTypes.PESO);

        // 9. Qual a raça do pet?
        String petBreed = readQuestionAndAnswer();
        menuController.validateValue(petBreed, ValidateTypes.RACA);

        menuController.addPet(petName, petType, petSex, petStreet, petHouseNumber, petCity, petAge, petWeight, petBreed);

        System.out.println("\n Cadastro realizado com sucesso!");
    }

    private String readQuestionAndAnswer() {
        System.out.println(menuController.getNextQuestion());
        return scanner.nextLine();
    }
}
