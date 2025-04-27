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
    private final PetListView petListView;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
        this.petRegisterView = new PetRegisterView(scanner, menuController);
        this.petSearchView = new PetSearchView(scanner, menuController);
        this.petListView = new PetListView();
        this.petUpdateView = new PetUpdateView(scanner, menuController, petListView, petSearchView);
        this.petDeleteView = new PetDeleteView(scanner, petSearchView, petListView);
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


    public void run() {
        int option;
        do {
            showMainMenu();

            option = InputService.readInt(scanner);

            switch (option) {
                case 1:
                    petRegisterView.register();
                    break;
                case 2:
                    List<Pet> listFiltered = petSearchView.searchPet();
                    petListView.showPetList(listFiltered);
                    break;
                case 3:
                    List<Pet> allPets = menuController.getListAllPets();
                    petListView.showPetList(allPets);
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
}
