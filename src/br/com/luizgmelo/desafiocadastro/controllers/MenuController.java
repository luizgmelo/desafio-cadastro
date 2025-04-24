package br.com.luizgmelo.desafiocadastro.controllers;

import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.service.FormReaderService;
import br.com.luizgmelo.desafiocadastro.service.PetService;
import br.com.luizgmelo.desafiocadastro.service.ValidateService;

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
