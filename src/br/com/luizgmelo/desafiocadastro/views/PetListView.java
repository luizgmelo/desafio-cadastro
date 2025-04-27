package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.models.Pet;

import java.util.List;

public class PetListView {
    public void showPetList(List<Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("Não foi encontrado nenhum pet!");
        } else {
            String petFormat = "%d. %s - %s - %s, %s, %s - %s - %s - %s\n";

            int id = 1;
            for (Pet pet : pets) {
                System.out.printf(petFormat,
                        id,
                        pet.getName(),
                        pet.getSex(),
                        pet.getStreetName(), pet.getHouseNumber(), pet.getCity(),
                        pet.getAge(),
                        pet.getWeight(),
                        pet.getBreed());
                id++;
            }
        }
    }
}
