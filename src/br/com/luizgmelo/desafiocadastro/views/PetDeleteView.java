package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.models.Pet;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PetDeleteView {

    private final Scanner scanner;
    private final PetSearchView petSearchView;
    private final PetListView petListView;

    public PetDeleteView(Scanner scanner, PetSearchView petSearchView, PetListView petListView) {
        this.scanner = scanner;
        this.petSearchView = petSearchView;
        this.petListView = petListView;
    }

    public void deletePet() {
        List<Pet> petsFiltered = petSearchView.searchPet();

        petListView.showPetList(petsFiltered);

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
                    petListView.showPetList(petsFiltered);
                }
            } catch (InputMismatchException ignored) {
                System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                petListView.showPetList(petsFiltered);
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

}
