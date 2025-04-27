package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.services.InputService;

import java.util.List;
import java.util.Scanner;

public class MenuView {
    private final Scanner scanner;
    private final MenuController menuController;
    private final PetRegisterView petRegisterView;
    private final PetSearchView petSearchView;
    private final PetUpdateView petUpdateView;
    private final PetDeleteView petDeleteView;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
        this.petRegisterView = new PetRegisterView(scanner, menuController);
        this.petSearchView = new PetSearchView(scanner, menuController);
        this.petUpdateView = new PetUpdateView(scanner, menuController, this, petSearchView);
        this.petDeleteView = new PetDeleteView(scanner, petSearchView, this);
    }

    public void showMainMenu() {
        System.out.println("\n==== Sistema de Cadastro de Pet ====\n");
        System.out.println("1. Cadastrar um novo pet");
        System.out.println("2. Listar pets por algum critério (idade, nome, raça)");
        System.out.println("3. Listar todos os pets cadastrados");
        System.out.println("4. Alterar os dados do pet cadastrado");
        System.out.println("5. Deletar um pet cadastrado");
        System.out.println("6. Sair");
        System.out.print("Digite uma das opções acima: ");
    }


    public void showMenu() {
        int option;
        do {
            showMainMenu();

            option = InputService.readInt(scanner);

            switch (option) {
                case 1:
                    petRegisterView.register();
                    break;
                case 2:
                    showPetList(petSearchView.searchPet());
                    break;
                case 3:
                    listAllPets();
                    break;
                case 4:
                    petUpdateView.updatePet();
                    break;
                case 5:
                    petDeleteView.deletePet();
                    break;
            }
        } while (option != 6);
    }



    private void listAllPets() {
        showPetList(menuController.getListAllPets());
    }

    public void showPetList(List<Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("Não foi encontrado nenhum pet!");
        } else {
            for (int i = 0; i < pets.size(); i++) {
                System.out.println(i + 1 + ". " + pets.get(i).getName() + " - " +
                        pets.get(i).getSex() + " - " +
                        pets.get(i).getStreetName() + ", " + pets.get(i).getHouseNumber() + ", " + pets.get(i).getCity() + " - " +
                        pets.get(i).getAge() + " - " +
                        pets.get(i).getWeight() + " - " +
                        pets.get(i).getBreed());
            }
        }
    }

}
