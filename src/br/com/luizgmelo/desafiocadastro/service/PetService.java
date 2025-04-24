package br.com.luizgmelo.desafiocadastro.service;

import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.repository.PetRepository;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetService {
    private final PetRepository petRepository;

    public PetService() {
        this.petRepository = new PetRepository();
    }

    public void addPet(Pet pet) {
        petRepository.savePet(pet);
    }

    public List<Pet> searchPet(PetType petType,  Map<String, String> criteriaValue) {

        Path petFolder = Paths.get("petsCadastrados");
        List<Pet> petList = getPetList(petFolder);

        return filterPetsByTypeAndCriterias(petType, criteriaValue, petList);
    }

    private List<Pet> filterPetsByTypeAndCriterias(PetType petType, Map<String, String> criteriaValue, List<Pet> petList) {
        return petList.stream()
                .filter(pet -> petType.name().equalsIgnoreCase(pet.getType().name()))
                .filter(pet -> checkCriterias(criteriaValue, pet))
                .collect(Collectors.toList());
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

