package br.com.luizgmelo.desafiocadastro.controllers;

import br.com.luizgmelo.desafiocadastro.model.Pet;
import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;
import br.com.luizgmelo.desafiocadastro.service.FormReaderService;
import br.com.luizgmelo.desafiocadastro.service.PetService;
import br.com.luizgmelo.desafiocadastro.service.ValidateService;


public class MenuController {

    private final FormReaderService formReaderService;
    private final PetService petService;
    private ValidateService validateService;

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

    public String validateWeight(String age) {
        return validateService.validateAge(age);
    }

    public void closeForm() {
        formReaderService.close();
    }

    public String showNextQuestion() {
        return formReaderService.getNextQuestion();
    }
}
