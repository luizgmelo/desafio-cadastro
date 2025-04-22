package br.com.luizgmelo.desafiocadastro;

import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.service.FormReaderService;
import br.com.luizgmelo.desafiocadastro.service.PetService;
import br.com.luizgmelo.desafiocadastro.service.ValidateService;
import br.com.luizgmelo.desafiocadastro.view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FormReaderService formReaderService = new FormReaderService("formulario.txt");
        PetService petService = new PetService();
        ValidateService validateService = new ValidateService();
        Menu menu = new Menu();

        int option = -1;
        do {
            menu.showMenu();

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
                    // 1. Qual o nome e sobrenome do pet?
                    String petName = getFormQuestion(formReaderService, scanner);
                    validateService.validateName(petName, "nome do pet");

                    // 2. Qual o tipo do pet (Cachorro/Gato)?
                    String type = getFormQuestion(formReaderService, scanner);
                    PetType petType = validateService.validateType(type);

                    // 3. Qual o sexo do animal?
                    String sex = getFormQuestion(formReaderService, scanner);
                    PetSex petSex = validateService.validateSex(sex);

                    //  4 - Qual o nome da Rua que ele foi encontrado?
                    String petStreet = getFormQuestion(formReaderService, scanner);
                    validateService.validateStreetName(petStreet);

                    // 5 - Qual o número da casa?
                    String petHouseNumber = getFormQuestion(formReaderService, scanner);
                    petHouseNumber = validateService.validateHouseNumber(petHouseNumber);

                    // 6 - Qual a cidade?
                    String petCity = getFormQuestion(formReaderService, scanner);
                    validateService.validateName(petCity, "nome da cidade");

                    // 7. Qual a idade aproximada do pet?
                    String petAge = getFormQuestion(formReaderService, scanner);
                    petAge = validateService.validateAge(petAge);

                    // 8. Qual o peso em kilos aproximado do pet?
                    String petWeight = getFormQuestion(formReaderService, scanner);
                    petWeight = validateService.validateWeight(petWeight);

                    // 9. Qual a raça do pet?
                    String petBreed = getFormQuestion(formReaderService, scanner);
                    validateService.validateName(petBreed, "raça");

                    formReaderService.close();

                    Pet pet = new Pet(petName, petType, petSex, petStreet, petHouseNumber, petCity, petAge, petWeight, petBreed);

                    petService.addPet(pet);

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

    public static String getFormQuestion(FormReaderService formReader, Scanner scanner) {
        System.out.print(formReader.getNextQuestion());
        return scanner.nextLine();
    }
}