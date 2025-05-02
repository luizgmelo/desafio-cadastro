package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.PetController;
import br.com.luizgmelo.desafiocadastro.enums.MENU;
import br.com.luizgmelo.desafiocadastro.enums.SearchCriteria;
import br.com.luizgmelo.desafiocadastro.enums.ValidateType;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.InputService;

import java.util.*;

public class UI {
    private final Scanner scanner;
    private final PetController petController;

    public UI() {
        this.scanner = new Scanner(System.in);
        this.petController = new PetController();
    }

    public void run () {
        MENU selectedOption;
        do {
            showMainMenu();

            int option = InputService.readInt(scanner);

            if (option < 1 || option > MENU.values().length) {
                selectedOption = null;
                System.out.println(" ** Opção inválida. Digite um dos números do menu.");
            } else {
                selectedOption = MENU.values()[option - 1];

                switch (selectedOption) {
                    case CADASTRAR_PET:
                        register();
                        break;
                    case BUSCAR_PET:
                        List<Pet> listFiltered = searchPet();
                        showPetList(listFiltered);
                        break;
                    case LISTAR_PETS:
                        List<Pet> allPets = petController.getListAllPets();
                        showPetList(allPets);
                        break;
                    case ALTERAR_PET:
                        updatePet();
                        break;
                    case DELETAR_PET:
                        deletePet();
                        break;
                    case SAIR:
                        sair();
                        break;
                }
            }
        } while (selectedOption != MENU.SAIR && selectedOption != null);
    }

    public void showMainMenu () {
        System.out.println("\n==== Sistema de Cadastro de Pet ====\n");
        System.out.println("1. Cadastrar um novo pet");
        System.out.println("2. Listar pets por algum critério (idade, nome, raça)");
        System.out.println("3. Listar todos os pets cadastrados");
        System.out.println("4. Alterar os dados do pet cadastrado");
        System.out.println("5. Deletar um pet cadastrado");
        System.out.println("6. Sair");
        System.out.print("Digite uma das opções acima: ");
    }

    // PET REGISTER ====
    public void register() {
        System.out.println("\n=== Cadastro do Pet ===");
        // 1. Qual o nome e sobrenome do pet?
        String petName = readQuestionAndAnswer();
        petController.validateValue(petName, ValidateType.NOME);

        // 2. Qual o tipo do pet (Cachorro/Gato)?
        String type = readQuestionAndAnswer();
        PetType petType = (PetType) petController.validateValue(type, ValidateType.TIPO);

        // 3. Qual o sexo do animal?
        String sex = readQuestionAndAnswer();
        PetSex petSex = (PetSex) petController.validateValue(sex, ValidateType.SEXO);

        //  4 - Qual o nome da Rua que ele foi encontrado?
        String petStreet = readQuestionAndAnswer();
        petController.validateValue(petStreet, ValidateType.RUA);

        // 5 - Qual o número da casa?
        String petHouseNumber = readQuestionAndAnswer();
        Integer petHouseNumberInteger = (Integer) petController.validateValue(petHouseNumber, ValidateType.NUMEROCASA);

        // 6 - Qual a cidade?
        String petCity = readQuestionAndAnswer();
        petController.validateValue(petCity,  ValidateType.CIDADE);

        // 7. Qual a idade aproximada do pet?
        String petAge = readQuestionAndAnswer();
        Float petAgeFloat = (Float) petController.validateValue(petAge, ValidateType.IDADE);

        // 8. Qual o peso em kilos aproximado do pet?
        String petWeight = readQuestionAndAnswer();
        Float petWeightFloat = (Float) petController.validateValue(petWeight, ValidateType.PESO);

        // 9. Qual a raça do pet?
        String petBreed = readQuestionAndAnswer();
        petController.validateValue(petBreed, ValidateType.RACA);

        petController.addPet(petName, petType, petSex, petStreet, petHouseNumberInteger, petCity, petAgeFloat, petWeightFloat, petBreed);

        System.out.println("Cadastro realizado com sucesso!");
    }

    private String readQuestionAndAnswer() {
        System.out.println(petController.getNextQuestion());
        return scanner.nextLine();
    }
    // ===== END REGISTER

    // PET SEARCH ====
    public List<Pet> searchPet() {
        Map<SearchCriteria, String> criterias = new HashMap<>();
        List<SearchCriteria> options = Arrays.asList(SearchCriteria.values());

        System.out.print("Qual o tipo do pet? (Gato/Cachorro)? ");
        PetType petTypeSearch = (PetType) petController.validateValue(scanner.nextLine(), ValidateType.TIPO);

        do {
            System.out.println("\n==== Busca de Pet ====");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i).getLabel());
            }

            System.out.printf("Escolha o %d° critério: ", criterias.size() + 1);
            int criteria = InputService.readInt(scanner);

            if (criteria >= 1 && criteria <= options.size()) {
                SearchCriteria selectedCriteria = options.get(criteria - 1);
                System.out.printf("Digite o valor de %s : ", selectedCriteria);
                String value = scanner.nextLine();
                petController.validateValue(value, selectedCriteria.getValidateType());

                criterias.put(selectedCriteria, value);

                if (criterias.size() == 1) {
                    System.out.print("Deseja selecionar mais um critério? (S/N)");
                    String res = scanner.nextLine();

                    if (res.charAt(0) == 'N' || res.charAt(0) == 'n') {
                        break;
                    }
                }
            }

        } while (criterias.size() != 2);

        return petController.searchPet(petTypeSearch, criterias);
    }
    // SEARCH END ===

    // LIST PETS ===
    public void showPetList(List<Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("Não foi encontrado nenhum pet!");
        } else {
            String petFormat = "%d. %s - %s - %s, %d, %s - %.1f anos - %.2fkg - %s\n";

            int id = 1;
            for (Pet pet : pets) {
                System.out.printf(petFormat,
                        id,
                        pet.getName(),
                        pet.getSex(),
                        pet.getAddress().getStreet(), pet.getAddress().getHouseNumber(), pet.getAddress().getCity(),
                        pet.getAge(),
                        pet.getWeight(),
                        pet.getBreed());
                id++;
            }
        }
    }
    // END ===


    // UPDATE PET ===
    public void updatePet() {
        List<Pet> petsFiltered = searchPet();

        showPetList(petsFiltered);

        if (petsFiltered.isEmpty()) {
            return;
        }

        int petOption = -1;
        do {
            System.out.printf("\nEscolha o número do pet na listagem para alterar o cadastro entre (%d-%d): ", 1, petsFiltered.size());

            petOption = getPetOption(petsFiltered, petOption);
        }
        while (petOption < 1 || petOption > petsFiltered.size());

        Pet petWantDelete = petsFiltered.get(petOption - 1);
        List<String> petData = petController.getPetData(petWantDelete);

        for (int i = 0; i < petData.size(); i++) {
            ValidateType field = ValidateType.values()[i];
            String value = petData.get(i);

            if (field == ValidateType.TIPO || field == ValidateType.SEXO) {
                petData.set(i, value.toUpperCase());
                continue;
            }

            System.out.printf("Deseja alterar o %s (S/N) ?", field);
            String answer = scanner.nextLine();

            if (answer.charAt(0) == 'S') {
                System.out.printf("%s atual %s\n", field, value);
                System.out.printf("Digite o novo %s: ", field);
                String newValue = scanner.nextLine();
                petController.validateValue(newValue, field);
                petData.set(i, newValue);
            }
        }

        petController.updatePet(petWantDelete, petData);

        System.out.println("Atualização efetuada com sucesso!");
    }
    // END ===

    // DELETE PET ===
    public void deletePet() {
        List<Pet> petsFiltered = searchPet();

        showPetList(petsFiltered);

        if (petsFiltered.isEmpty()) {
            return;
        }

        int petOption = -1;
        do {
            System.out.printf("\nEscolha o número do pet na listagem para deletar (%d-%d): ", 1, petsFiltered.size());
            petOption = getPetOption(petsFiltered, petOption);
        } while (petOption < 1 || petOption > petsFiltered.size());

        Pet petWantsDelete = petsFiltered.get(petOption - 1);

        System.out.println("Deseja realmente deletar " + petWantsDelete.getName() + ": (SIM/NAO) ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("sim")) {
            petController.deletePet(petWantsDelete);
            System.out.println("Pet removido com sucesso!");
        }
    }
    // END ===

    private int getPetOption(List<Pet> petsFiltered, int petOption) {
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
        return petOption;
    }

    public void sair() {
        System.out.println("Volte sempre!");
    }

}
