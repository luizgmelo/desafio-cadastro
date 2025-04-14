package br.com.luizgmelo.desafiocadastro.service;

import br.com.luizgmelo.desafiocadastro.cli.Menu;
import br.com.luizgmelo.desafiocadastro.model.Address;
import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.repository.PetRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

    public void registerPet() {
        File file = new File("formulario.txt");
        Scanner scanner = new Scanner(System.in);

        try (Scanner asksFile = new Scanner(file)) {
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Não foi possível encontrar o arquivo formulario.txt");
        }
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


    public String getQuestion(Scanner asksFile, Scanner scanner) {
        System.out.print(asksFile.nextLine());
        return scanner.nextLine();
    }
}
