package br.com.luizgmelo.desafiocadastro.service;

import br.com.luizgmelo.desafiocadastro.cli.Menu;
import br.com.luizgmelo.desafiocadastro.model.Address;
import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.repository.PetRepository;
import br.com.luizgmelo.desafiocadastro.utils.FormReader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PetService {
    ValidateService validateService;
    PetRepository petRepository;

    public PetService(ValidateService validateService, PetRepository petRepository) {
        this.validateService = validateService;
        this.petRepository = petRepository;
    }

    public void registerPet(Scanner scanner) {
        FormReader formReader = new FormReader("formulario.txt");

        // 1. Qual o nome e sobrenome do pet?
        String petName = getQuestion(formReader, scanner);
        validateService.validateName(petName, "nome do pet");

        // 2. Qual o tipo do pet (Cachorro/Gato)?
        String type = getQuestion(formReader, scanner);
        PetType petType = validateService.validateType(type);

        // 3. Qual o sexo do animal?
        String sex = getQuestion(formReader, scanner);
        PetSex petSex = validateService.validateSex(sex);

        // 4. Qual endereço e bairro que ele foi encontrado?
        formReader.getNextQuestion();

        //  4.1 - Rua:
        String petStreet = getQuestion(formReader, scanner);
        validateService.validateName(petStreet, "nome da rua");

        // 4.2 - Número da Casa:
        String petHouseNumber = getQuestion(formReader, scanner);
        petHouseNumber = validateService.validateHouseNumber(petHouseNumber);

        // 4.3 - Cidade:
        String petCity = getQuestion(formReader, scanner);
        validateService.validateName(petCity, "nome da cidade");

        // 5. Qual a idade aproximada do pet?
        String petAge = getQuestion(formReader, scanner);
        petAge = validateService.validateAge(petAge);

        // 6. Qual o peso em kilos aproximado do pet?
        String petWeight = getQuestion(formReader, scanner);
        petWeight = validateService.validateWeight(petWeight);


        // 7. Qual a raça do pet?
        String petBreed = getQuestion(formReader, scanner);
        validateService.validateName(petBreed, "raça");

        formReader.close();

        Address petAddress = new Address(petHouseNumber, petCity, petStreet);
        Pet pet = new Pet(petName, petType, petSex, petAddress, petAge, petWeight, petBreed);

        petRepository.savePet(pet);
    }

    public void searchPet(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Qual o tipo de animal você quer buscar(Cachorro ou Gato)? ");
        PetType petType = validateService.validateType(scanner.nextLine());
        List<String> query = new ArrayList<>();

        int criteria;
        int[] options = new int[2];

        do {
            System.out.print("Quantos critérios você quer usar (1 ou 2)? ");
            criteria = scanner.nextInt();
        } while (criteria != 1 && criteria != 2);

        Menu.showSearchMenu();

        for (int i = 0; i < criteria; i++) {
            System.out.print("Digite o " + (i + 1) + "° critério: ");
            options[i] = scanner.nextInt();
        }

        for (int i = 0; i < criteria; i++) {
            switch (options[i]) {
                case 1:
                    System.out.println("Qual parte do nome quer pesquisar: ");
                    break;
                case 2:
                    System.out.println("Qual sexo (Macho/Femea): ");
                    break;
                case 3:
                    System.out.println("Qual a idade: ");
                    break;
                case 4:
                    System.out.println("Qual o peso: ");
                    break;
                case 5:
                    System.out.println("Qual a raça: ");
                    break;
                case 6:
                    System.out.println("Qual o endereço: ");
                    break;
            }
            String q = scanner.nextLine();
            query.add(q);
        }

        Path folder = Paths.get("petsCadastrados");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    List<String> allLines = Files.readAllLines(path);
                    String petTypeInFile = allLines.get(1).substring(4);
                    if (petTypeInFile.equalsIgnoreCase(petType.name())) {
                        for (String line : allLines) {
                            for (String q : query) {
                                boolean isMatch = line.toUpperCase().contains(q.toUpperCase());
                                System.out.println(isMatch);
                            }
                            System.out.println("outra linha");
                        }

                    }
                }
                System.out.println("outro arquivo");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getQuestion(FormReader formReader, Scanner scanner) {
        System.out.print(formReader.getNextQuestion());
        return scanner.nextLine();
    }
}
