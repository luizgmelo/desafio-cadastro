package br.com.luizgmelo.desafiocadastro.controllers;

import br.com.luizgmelo.desafiocadastro.models.Address;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.FormReaderService;
import br.com.luizgmelo.desafiocadastro.services.PetService;
import br.com.luizgmelo.desafiocadastro.services.ValidateService;
import br.com.luizgmelo.desafiocadastro.services.ValidateTypes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class MenuController {

    private final FormReaderService formReaderService;
    private final PetService petService;
    private final ValidateService validateService;

    public MenuController() {
        this.formReaderService = new FormReaderService("formulario.txt");
        this.petService = new PetService();
        this.validateService = new ValidateService();
    }

    public List<Pet> getListAllPets() {
        Path folder = Paths.get("petsCadastrados");
        if (!Files.exists(folder)) {
            return null;
        }
        return petService.getPetList(folder);
    }

    public Path getPetFile(Pet pet) {
        return petService.getPetFile(pet);
    }

    public void addPet(String petName, PetType petType, PetSex petSex,
                          String petStreet, String petHouseNumber, String petCity,
                          String petAge, String petWeight, String petBreed) {

        Pet pet = new Pet(petName, petType, petSex, new Address(petStreet, petHouseNumber, petCity), petAge, petWeight, petBreed);

        petService.addPet(pet);
    }

    public List<Pet> searchPet(PetType petType, Map<String, String> criterias) {
        return petService.searchPet(petType, criterias);
    }

    public <T> Object validateValue(String value, ValidateTypes type) {
        try {
            return type.validate(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de validação é inválido: " + type);
        }
    }

    public String getNextQuestion() {
        return formReaderService.getNextQuestion();
    }
}
