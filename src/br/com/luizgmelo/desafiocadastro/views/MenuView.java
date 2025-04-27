package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.InputService;

import javax.sound.midi.Soundbank;
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
    final Scanner scanner;
    final MenuController menuController;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.menuController = new MenuController();
    }

    public void menu() {
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
            menu();

            option = InputService.readInt(scanner);

            switch (option) {
                case 1:
                    registerPet();
                    break;
                case 2:
                    showPetList(searchPet());
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
        List<Pet> petsFiltered = searchPet();

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
        List<Pet> petsFiltered = searchPet();

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

    private List<Pet> searchPet() {
        Map<String, String> criterias = new HashMap<>();
        List<String> options = Arrays.asList("Nome", "Sexo", "Idade", "Peso", "Raca", "Endereco");

        System.out.print("Qual o tipo do pet? (Gato/Cachorro)? ");
        PetType petTypeSearch = menuController.validateType(scanner.nextLine());

        do {
            showSearchMenu(options);

            System.out.print("Escolha o " + (criterias.size() + 1) + "° critério: ");
            int criteria = InputService.readInt(scanner);

            if (criteria >= 1 && criteria <= 6) {
                String option = options.get(criteria - 1);
                System.out.print("Digite o valor de " + option + ": ");
                String value = scanner.nextLine();
                menuController.validateValue(value, option);

                criterias.put(option, value);

                if (criterias.size() == 1) {
                    System.out.print("Deseja selecionar mais um critério? (S/N)");
                    String res = scanner.nextLine();

                    if (res.charAt(0) == 'N' || res.charAt(0) == 'n') {
                        break;
                    }
                }
            }

        } while (criterias.size() != 2);

        List<Pet> petFiltered = menuController.searchPet(petTypeSearch, criterias);

        return petFiltered;
    }

    private void registerPet() {
        System.out.println("\n=== Cadastro do Pet ===");
        // 1. Qual o nome e sobrenome do pet?
        System.out.print(menuController.showNextQuestion());
        String petName = scanner.nextLine();
        menuController.validateName(petName, "nome do pet");

        // 2. Qual o tipo do pet (Cachorro/Gato)?
        System.out.print(menuController.showNextQuestion());
        String type = scanner.nextLine();
        PetType petType = menuController.validateType(type);

        // 3. Qual o sexo do animal?
        System.out.print(menuController.showNextQuestion());
        String sex = scanner.nextLine();
        PetSex petSex = menuController.validateSex(sex);

        //  4 - Qual o nome da Rua que ele foi encontrado?
        System.out.print(menuController.showNextQuestion());
        String petStreet = scanner.nextLine();
        menuController.validateStreetName(petStreet);

        // 5 - Qual o número da casa?
        System.out.print(menuController.showNextQuestion());
        String petHouseNumber = scanner.nextLine();
        petHouseNumber = menuController.validateHouseNumber(petHouseNumber);

        // 6 - Qual a cidade?
        System.out.print(menuController.showNextQuestion());
        String petCity = scanner.nextLine();
        menuController.validateName(petCity, "nome da cidade");

        // 7. Qual a idade aproximada do pet?
        System.out.print(menuController.showNextQuestion());
        String petAge = scanner.nextLine();
        petAge = menuController.validateAge(petAge);

        // 8. Qual o peso em kilos aproximado do pet?
        System.out.print(menuController.showNextQuestion());
        String petWeight = scanner.nextLine();
        petWeight = menuController.validateWeight(petWeight);

        // 9. Qual a raça do pet?
        System.out.print(menuController.showNextQuestion());
        String petBreed = scanner.nextLine();
        menuController.validateName(petBreed, "raça");

        boolean isCreated = menuController.addPet(petName, petType, petSex,
                petStreet, petHouseNumber, petCity,
                petAge, petWeight, petBreed);

        if (isCreated) {
            System.out.println("\n Cadastro realizado com sucesso!");
        } else {
            System.out.println("\n Não foi possível fazer o cadastro");
        }
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

    public void showSearchMenu(List<String> menuOptions) {
        System.out.println("\n==== Busca de Pet ====");
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println((i + 1) + ". " + menuOptions.get(i));
        }
    }
}
