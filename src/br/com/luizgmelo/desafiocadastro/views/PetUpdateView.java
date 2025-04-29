package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.ValidateTypes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PetUpdateView {

    private final Scanner scanner;
    private final MenuController menuController;
    private final PetListView petListView;
    private final PetSearchView petSearchView;

    public PetUpdateView(Scanner scanner, MenuController menuController, PetListView petListView, PetSearchView petSearchView) {
        this.scanner = scanner;
        this.menuController = menuController;
        this.petListView = petListView;
        this.petSearchView = petSearchView;
    }

    public void updatePet() {
        List<Pet> petsFiltered = petSearchView.searchPet();

        petListView.showPetList(petsFiltered);

        if (petsFiltered.isEmpty()) {
            return;
        }


        int petOption = -1;
        do {
            System.out.printf("\nEscolha o número do pet na listagem para alterar o cadastro entre (%d-%d): ", 1, petsFiltered.size());

            try {
                petOption = scanner.nextInt();

                if (petOption < 1 || petOption > petsFiltered.size()) {
                    System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                    petListView.showPetList(petsFiltered);
                }
            } catch (InputMismatchException ignored) {
                System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                petListView.showPetList(petsFiltered);
            } finally {
                scanner.nextLine();
            }
        } while (petOption < 1 || petOption > petsFiltered.size());

        Pet pet = petsFiltered.get(petOption - 1);
        Path petFile = menuController.getPetFile(pet);

        List<String> lines;
        try {
            lines = Files.readAllLines(petFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            String actualType = line.substring(0, 1);


            String field = ValidateTypes.getByFileValue(actualType).getType();
            System.out.print("Deseja alterar o " + field + " (S/N) ?");
            String answer = scanner.nextLine();

            if (answer.charAt(0) == 'S') {
                System.out.println(field + " atual " + line.substring(4));
                System.out.print("Digite o novo " + field + ": ");
                String newValue = scanner.nextLine();
                menuController.validateValue(newValue, ValidateTypes.getByFileValue(actualType));
                String prefix = line.substring(0, line.indexOf("-") + 1);
                lines.set(i, prefix + " " + newValue);
            }
        }

        try {
            Files.delete(petFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] address = lines.get(3).substring(3).split(", ");

        menuController.addPet(
                lines.get(0).substring(3),
                PetType.valueOf(lines.get(1).substring(4).toUpperCase()),
                PetSex.valueOf(lines.get(2).substring(4).toUpperCase()),
                address[0], address[1], address[2],
                lines.get(4).substring(3),
                lines.get(5).substring(3),
                lines.get(6).substring(3)

        );
    }
}