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

    public void addPet(Pet pet) {
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

