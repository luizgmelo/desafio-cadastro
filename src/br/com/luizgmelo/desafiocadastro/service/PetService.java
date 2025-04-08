package br.com.luizgmelo.desafiocadastro.service;

import br.com.luizgmelo.desafiocadastro.model.Address;
import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.repository.PetRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PetService {
    ValidateService validateService;
    PetRepository petRepository;

    public PetService(ValidateService validateService, PetRepository petRepository) {
        this.validateService = validateService;
        this.petRepository = petRepository;
    }

    public void registerPet() {
        File file = new File("formulario.txt");
        Scanner scanner = new Scanner(System.in);
        Scanner asksFile;

        try {
            asksFile = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Não foi possível encontrar o arquivo formulario.txt");
        }

        // 1. Qual o nome e sobrenome do pet?
        String petName = getQuestion(asksFile, scanner);
        validateService.validateName(petName);

        // 2. Qual o tipo do pet (Cachorro/Gato)?
        String type = getQuestion(asksFile, scanner).toUpperCase();
        PetType petType = validateService.validateType(type);

        // 3. Qual o sexo do animal?
        String sex = getQuestion(asksFile, scanner);
        PetSex petSex = validateService.validateSex(sex);

        // 4. Qual endereço e bairro que ele foi encontrado?
        System.out.println(asksFile.nextLine());

        // 4.1 - Número da Casa:
        String petHouseNumber = getQuestion(asksFile, scanner);
        if (petHouseNumber.isEmpty()) {
            petHouseNumber = validateService.NOT_INFORMED;
        } else {
            validateService.validateInteger(petHouseNumber, "O número da casa deve ser um número inteiro");
        }

        // 4.2 - Cidade:
        String petCity = getQuestion(asksFile, scanner);

        //  4.4 - Rua:
        String petStreet = getQuestion(asksFile, scanner);

        // 4.5 - Bairro:
        String petNeighborhood = getQuestion(asksFile, scanner);

        // 5. Qual a idade em anos aproximada do pet?
        String petAge = getQuestion(asksFile, scanner);

        if (petAge.isEmpty()) {
            petAge = validateService.NOT_INFORMED;
        } else {
            validateService.validateAge(petAge);
        }
        petAge = petAge.equals(validateService.NOT_INFORMED) ? validateService.NOT_INFORMED : petAge + " anos";

        // 6. Qual o peso em kilos aproximado do pet?
        String petWeight = getQuestion(asksFile, scanner);

        if (petWeight.isEmpty()) {
            petWeight = validateService.NOT_INFORMED;
        } else {
            validateService.validateWeight(petWeight);
        }
        petWeight = petWeight.equals(validateService.NOT_INFORMED) ? validateService.NOT_INFORMED : petWeight + " kg";

        // 7. Qual a raça do pet?
        String petBreed = getQuestion(asksFile, scanner);
        validateService.validateBreed(petBreed);

        Address petAddress = new Address(petHouseNumber, petCity, petStreet, petNeighborhood);
        Pet pet = new Pet(petName, petType, petSex, petAddress, petAge, petWeight, petBreed);

        petRepository.savePet(pet);
    }

    public String getQuestion(Scanner asksFile, Scanner scanner) {
        System.out.print(asksFile.nextLine());
        return scanner.nextLine();
    }
}
