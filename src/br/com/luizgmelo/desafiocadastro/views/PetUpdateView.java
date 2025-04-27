package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PetUpdateView {

    private final Scanner scanner;
    private final MenuController menuController;
    private final MenuView menuView;
    private final PetSearchView petSearchView;

    public PetUpdateView(Scanner scanner, MenuController menuController, MenuView menuView, PetSearchView petSearchView) {
        this.scanner = scanner;
        this.menuController = menuController;
        this.menuView = menuView;
        this.petSearchView = petSearchView;
    }

    public void updatePet() {
        List<Pet> petsFiltered = petSearchView.searchPet();

        menuView.showPetList(petsFiltered);

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
                    menuView.showPetList(petsFiltered);
                }
            } catch (InputMismatchException ignored) {
                System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                menuView.showPetList(petsFiltered);
            } finally {
                scanner.nextLine();
            }
        } while (petOption < 1 || petOption > petsFiltered.size());

        Pet pet = petsFiltered.get(petOption-1);
        Path petFile = menuController.getPetFile(pet);

        Map<Character, String> options = new HashMap<>();
        options.put('1', "nome");
        options.put('4', "endereco");
        options.put('5', "idade");
        options.put('6', "peso");
        options.put('7', "raca");

        try {
            List<String> lines = Files.readAllLines(petFile);

            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i);

                if (line.startsWith("2") || line.startsWith("3")) {
                    continue;
                };

                System.out.println("Deseja alterar " + line.substring(4) + ":");
                System.out.print("Deseja alterar esse dado(S/N): ");
                String answer = scanner.nextLine();

                if (answer.charAt(0) == 'S') {
                    System.out.print("Digite o novo valor: ");
                    String newValue = scanner.nextLine();
                    menuController.validateValue(newValue, options.get(line.charAt(0)));
                    String prefix = line.substring(0, line.indexOf("-") + 1);
                    lines.set(i, prefix + " " + newValue);
                }
            }

            Files.delete(petFile);

            String fileName = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm").format(LocalDateTime.now()) + " - " +
                    lines.get(0).substring(3).toUpperCase().replaceAll("\\s", "") + ".txt";

            Path newPetFile = Paths.get("petsCadastrados", fileName);

            if (!Files.exists(newPetFile)) {
                Files.createFile(newPetFile);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(newPetFile.toFile()))) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                    System.out.println("\nArquivo atualizado com sucesso!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
