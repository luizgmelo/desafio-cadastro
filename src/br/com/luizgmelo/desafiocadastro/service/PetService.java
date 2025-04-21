package br.com.luizgmelo.desafiocadastro.service;

import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.repository.PetRepository;
import br.com.luizgmelo.desafiocadastro.view.Menu;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PetService {
    private final ValidateService validateService;
    private final PetRepository petRepository;

    public PetService() {
        this.validateService = new ValidateService();
        this.petRepository = new PetRepository();
    }

    public void registerPet(Scanner scanner) {
        FormReaderService formReader = new FormReaderService("formulario.txt");

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
        validateService.validateStreetName(petStreet);

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

        Pet pet = new Pet(petName, petType, petSex, petStreet, petHouseNumber, petCity, petAge, petWeight, petBreed);

        petRepository.savePet(pet);
    }

    public void searchPet(Scanner scanner) {

        Path petFolder = Paths.get("petsCadastrados");
        List<Pet> petList = getPetList(petFolder);

        // TODO Mostrar um menu de critérios de busca | Processe a seleção do usuário
        System.out.print("Qual o tipo do pet? (Gato/Cachorro)? ");
        PetType petType = validateService.validateType(scanner.nextLine());

        Map<String, String> criteriaValue = Menu.showSearchMenu(scanner);

        // TODO Filtre os pets de acordo com os critérios
        List<Pet> petFiltered = petList.stream()
                .filter(pet -> petType.name().equalsIgnoreCase(pet.getType().name()))
                .filter(pet -> checkCriterias(criteriaValue, pet))
                .collect(Collectors.toList());

        // TODO Exiba os resultados
        for (int i = 0; i < petFiltered.size(); i++) {
            System.out.println(i+1 + ". " + petFiltered.get(i).getName() + " - " +
                                            petFiltered.get(i).getSex() + " - " +
                                            petFiltered.get(i).getStreetName() + ", " + petFiltered.get(i).getHouseNumber() + ", " + petFiltered.get(i).getCity() + " - " +
                                            petFiltered.get(i).getAge() + " - " +
                                            petFiltered.get(i).getWeight() + " - " +
                                            petFiltered.get(i).getBreed());
        }
    }

    private static List<Pet> getPetList(Path folder) {
        List<Pet> petList = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
            for (Path path : directoryStream) {
                if (!Files.isDirectory(path)) {
                    Pet pet = getPetFromFile(path);
                    if (pet != null) {
                        petList.add(pet);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return petList;
    }

    private static Pet getPetFromFile(Path file) throws IOException {
        List<String> allLines = Files.readAllLines(file);
        Map<Integer, String> data = new HashMap<>();

        if (allLines.isEmpty()) {
            return null;
        }

        for (String line : allLines) {
            String[] part = line.split("-", 2);
            data.put(Integer.parseInt(part[0].trim()), part[1].trim());
        }

        String[] address = data.get(4).split(",");

        return new Pet(
                data.get(1),
                PetType.valueOf(data.get(2).toUpperCase()),
                PetSex.valueOf(data.get(3).toUpperCase()),
                address[0], address[1], address[2],
                data.get(5),
                data.get(6),
                data.get(7)
        );
    }

    public String getQuestion(FormReaderService formReader, Scanner scanner) {
        System.out.print(formReader.getNextQuestion());
        return scanner.nextLine();
    }

    public boolean checkCriterias(Map<String, String> criteriaValue, Pet pet) {
        for (Map.Entry<String, String> entry : criteriaValue.entrySet()) {
            String value = entry.getValue();

            if (pet.toString().toLowerCase().contains(value.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

