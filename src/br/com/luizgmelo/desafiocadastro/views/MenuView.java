package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.services.InputService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MenuView {
    private final Scanner scanner;
    private final MenuController menuController;
    private final PetRegisterView petRegisterView;
    private final PetSearchView petSearchView;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
        this.petRegisterView = new PetRegisterView(scanner, menuController);
        this.petSearchView = new PetSearchView(scanner, menuController);
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
                    updatePet();
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

    private void updatePet() {
        List<Pet> petsFiltered = petSearchView.searchPet();

        showPetList(petsFiltered);

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
                    showPetList(petsFiltered);
                }
            } catch (InputMismatchException ignored) {
                System.out.printf("\nOpção inválida. Digite um número entre %d e %d.\n\n", 1, petsFiltered.size());
                showPetList(petsFiltered);
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

    private void listAllPets() {
        showPetList(menuController.getListAllPets());
    }

    private void showPetList(List<Pet> pets) {
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
