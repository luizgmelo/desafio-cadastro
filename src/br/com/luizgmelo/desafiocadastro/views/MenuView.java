package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.services.InputService;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    private final Scanner scanner;
    private final MenuController menuController;
    private final PetRegisterView petRegisterView;
    private final PetSearchView petSearchView;
    private final PetUpdateView petUpdateView;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
        this.petRegisterView = new PetRegisterView(scanner, menuController);
        this.petSearchView = new PetSearchView(scanner, menuController);
        this.petUpdateView = new PetUpdateView(scanner, menuController, this, petSearchView);
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
                    deletePet();
                    break;
            }
        } while (option != 6);
    }

    private void deletePet() {
        List<Pet> petsFiltered = petSearchView.searchPet();

        showPetList(petsFiltered);

        if (petsFiltered.isEmpty()) {
            return;
        }

        int petOption = -1;
        do {
            System.out.printf("\nEscolha o número do pet na listagem para deletar: ", 1, petsFiltered.size());

            try {
                petOption = scanner.nextInt();

                if (petOption < 1 || petOption > petsFiltered.size()) {
                    System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                    showPetList(petsFiltered);
                }
            } catch (InputMismatchException ignored) {
                System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                showPetList(petsFiltered);
            } finally {
                scanner.nextLine();
            }
        } while (petOption < 1 || petOption > petsFiltered.size());

        Pet petWantsDelete = petsFiltered.get(petOption - 1);

        System.out.println("Deseja realmente deletar " + petWantsDelete.getName() + ": (SIM/NAO) ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("sim")) {
            Path folder = Paths.get("petsCadastrados");
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
                for (Path path : directoryStream) {
                    if (!Files.isDirectory(path)) {
                        if (path.getFileName().toString().contains(petWantsDelete.getName().toUpperCase())) {
                            Files.delete(path);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
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
