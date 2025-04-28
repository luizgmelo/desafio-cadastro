package br.com.luizgmelo.desafiocadastro.views;

import br.com.luizgmelo.desafiocadastro.controllers.MenuController;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetType;
import br.com.luizgmelo.desafiocadastro.services.InputService;
import br.com.luizgmelo.desafiocadastro.services.ValidateTypes;

import java.util.*;

public class PetSearchView {
    private final Scanner scanner;
    private final MenuController menuController;

    public PetSearchView(Scanner scanner, MenuController menuController) {
        this.scanner = scanner;
        this.menuController = menuController;
    }

    public List<Pet> searchPet() {
        Map<String, String> criterias = new HashMap<>();
        List<String> options = Arrays.asList("Nome", "Sexo", "Idade", "Peso", "Raca", "Endereco");

        System.out.print("Qual o tipo do pet? (Gato/Cachorro)? ");
        PetType petTypeSearch = (PetType) menuController.validateValue(scanner.nextLine(), ValidateTypes.TIPO);

        do {
            showSearchMenu(options);

            System.out.print("Escolha o " + (criterias.size() + 1) + "° critério: ");
            int criteria = InputService.readInt(scanner);

            if (criteria >= 1 && criteria <= 6) {
                String option = options.get(criteria - 1);
                System.out.print("Digite o valor de " + option + ": ");
                String value = scanner.nextLine();
                menuController.validateValue(value, ValidateTypes.valueOf(option));

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

    public void showSearchMenu(List<String> menuOptions) {
        System.out.println("\n==== Busca de Pet ====");
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println((i + 1) + ". " + menuOptions.get(i));
        }
    }
}
