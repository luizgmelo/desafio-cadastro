package br.com.luizgmelo.desafiocadastro.controllers;

import br.com.luizgmelo.desafiocadastro.enums.SearchCriteria;
import br.com.luizgmelo.desafiocadastro.models.Address;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.FormReaderService;
import br.com.luizgmelo.desafiocadastro.services.PetService;
import br.com.luizgmelo.desafiocadastro.enums.ValidateType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PetController {

    private final FormReaderService formReaderService;
    private final PetService petService;

    public PetController() {
        this.formReaderService = new FormReaderService("formulario.txt");
        this.petService = new PetService();
    }

    public List<Pet> getListAllPets() {
        return petService.getPetList();
    }

    public Path getPetFile(Pet pet) {
        return petService.getPetFile(pet);
    }

    public void addPet(String name, PetType type, PetSex sex,
                          String street, int houseNumber, String city,
                          float age, float weight, String breed) {

        Pet pet = new Pet(name, type, sex, new Address(street, houseNumber, city), age, weight, breed);

        petService.addPet(pet);
    }

    public void addPet(Pet pet) {
        addPet(pet.getName(), pet.getType(), pet.getSex(),
                pet.getAddress().getStreet(), pet.getAddress().getHouseNumber(), pet.getAddress().getCity(),
                pet.getAge(), pet.getWeight(), pet.getBreed());
    }

    public List<Pet> searchPet(PetType type, Map<SearchCriteria, String> criterias) {
        return petService.searchPet(type, criterias);
    }

    public void updatePet(Pet petOld, List<String> petData) {

        Pet newPet = new Pet(petData);

        deletePet(petOld);

        addPet(newPet);
    }

    public <T> Object validateValue(String value, ValidateType type) {
        try {
            return type.validate(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de validação é inválido: " + type);
        }
    }

    public void openReaderService() {
        formReaderService.open();
    }

    public void closeReaderService() {
        formReaderService.close();
    }

    public String getNextQuestion() {
        return formReaderService.getNextQuestion();
    }

    public List<String> getPetData(Pet pet) {
        Path petFile = getPetFile(pet);
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(petFile)) {

            String line = br.readLine();
            while (line != null) {
                if (line.startsWith("4 -")) {
                    String[] address = line.substring(4).split(", ");
                    lines.addAll(Arrays.asList(address));
                } else {
                    lines.add(line.substring(4));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    public void deletePet(Pet pet) {
        Path petFile = getPetFile(pet);

        try {
            Files.delete(petFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
