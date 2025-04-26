package br.com.luizgmelo.desafiocadastro.controllers;

import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.FormReaderService;
import br.com.luizgmelo.desafiocadastro.services.PetService;
import br.com.luizgmelo.desafiocadastro.services.ValidateService;

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

    public boolean addPet(String petName, PetType petType, PetSex petSex,
                          String petStreet, String petHouseNumber, String petCity,
                          String petAge, String petWeight, String petBreed) {

        Pet pet = new Pet(petName, petType, petSex, petStreet, petHouseNumber, petCity, petAge, petWeight, petBreed);

        petService.addPet(pet);
        return true;
    }

    public List<Pet> searchPet(PetType petType, Map<String, String> criterias) {
        return petService.searchPet(petType, criterias);
    }

    public void validateValue(String value, String type) {
        switch (type.toLowerCase()) {
            case "nome":
            case "raca":
                validateName(value, type);
                break;
            case "sexo":
                validateSex(value);
                break;
            case "idade":
                validateAge(value);
                break;
            case "peso":
                validateWeight(value);
                break;
            case "endereco":
                // TODO validate streetName, city, houseNumber
                break;
        }

    }
    public void validateName(String name, String fieldName) {
        validateService.validateName(name, fieldName);
    }

    public PetType validateType(String type) {
        return validateService.validateType(type);
    }

    public PetSex validateSex(String sex) {
        return validateService.validateSex(sex);
    }

    public void validateStreetName(String streetName) {
        validateService.validateStreetName(streetName);
    }

    public String validateHouseNumber(String houseNumber) {
        return validateService.validateHouseNumber(houseNumber);
    }

    public String validateAge(String age) {
        return validateService.validateAge(age);
    }

    public String validateWeight(String weight) {
        return validateService.validateWeight(weight);
    }

    public void closeForm() {
        formReaderService.close();
    }

    public String showNextQuestion() {
        return formReaderService.getNextQuestion();
    }
}
