package br.com.luizgmelo.desafiocadastro.services;

import br.com.luizgmelo.desafiocadastro.models.Address;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.repositories.PetRepository;

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
        List<Pet> petList = petRepository.getPetList();

        return filterPetsByTypeAndCriterias(petType, criteriaValue, petList);
    }

    private List<Pet> filterPetsByTypeAndCriterias(PetType petType, Map<String, String> criteriaValue, List<Pet> petList) {
        return petList.stream()
                .filter(pet -> petType.name().equalsIgnoreCase(pet.getType().name()))
                .filter(pet -> checkCriterias(criteriaValue, pet))
                .collect(Collectors.toList());
    }

    public List<Pet> getPetList() {
        return petRepository.getPetList();
    }

    public Path getPetFile(Pet pet) {
        return petRepository.getPetFile(pet);
    }

    public Pet getPetFromFile(Path file) throws IOException {
        return petRepository.getPetFromFile(file);
    }

    public boolean checkCriterias(Map<String, String> criteriaValue, Pet pet) {
        boolean match = false;

        for (Map.Entry<String, String> entry : criteriaValue.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();

            switch (key.toLowerCase()) {
                case "nome":
                    match |= pet.getName().toLowerCase().contains(value.toLowerCase());
                    break;
                case "sexo":
                    match |= pet.getSex().name().toLowerCase().contains(value.toLowerCase());
                    break;
                // TODO Validate age should validate the entire age and not only the first number
                case "idade":
                    match |= pet.getAge().charAt(0) == value.charAt(0);
                    break;
                case "peso":
                    match |= pet.getWeight().charAt(0) == value.charAt(0);
                    break;
                case "endereco":
                    // TODO validate streetName, city, houseNumber
                    break;
                case "raca":
                    match &= pet.getBreed().toLowerCase().contains(value.toLowerCase());
                    break;
            }

            if (match) break;
        }

        return match;
    }
}

