package br.com.luizgmelo.desafiocadastro.services;

import br.com.luizgmelo.desafiocadastro.enums.SearchCriteria;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.repositories.PetRepository;

import java.nio.file.Path;
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

    public List<Pet> searchPet(PetType petType,  Map<SearchCriteria, String> criteriaValue) {
        List<Pet> petList = petRepository.getPetList();

        return filterPetsByTypeAndCriterias(petType, criteriaValue, petList);
    }

    private List<Pet> filterPetsByTypeAndCriterias(PetType petType, Map<SearchCriteria, String> criteriaValue, List<Pet> petList) {
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

    public boolean checkCriterias(Map<SearchCriteria, String> criteriaValue, Pet pet) {
        boolean match = false;

        for (Map.Entry<SearchCriteria, String> entry : criteriaValue.entrySet()) {
            String value = entry.getValue();
            SearchCriteria key = entry.getKey();

            switch (key) {
                case NOME:
                    match = pet.getName().toLowerCase().contains(value.toLowerCase());
                    break;
                case SEXO:
                    match = pet.getSex().name().toLowerCase().contains(value.toLowerCase());
                    break;
                case IDADE:
                    match = pet.getAge() == Float.parseFloat(value.replace(",", "."));
                    break;
                case PESO:
                    match = pet.getWeight() ==  Float.parseFloat(value.replace(",", "."));
                    break;
                case RUA:
                    match = pet.getAddress().getStreet().toLowerCase().contains(value.toLowerCase());
                    break;
                case CIDADE:
                    match = pet.getAddress().getCity().toLowerCase().contains(value.toLowerCase());
                    break;
                    case NUMERO:
                    match = pet.getAddress().getHouseNumber() == Integer.parseInt(value);
                    break;
                case RACA:
                    match = pet.getBreed().toLowerCase().contains(value.toLowerCase());
                    break;
            }

            if (match) break;
        }

        return match;
    }
}

