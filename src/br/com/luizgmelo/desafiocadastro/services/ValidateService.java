package br.com.luizgmelo.desafiocadastro.services;

import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;

public class ValidateService {
    private static final String NOT_INFORMED = "NÃO INFORMADO";

    public static void validateName(String name, String fieldName) {
        if (name.trim().isEmpty()) {
            throw new RuntimeException(fieldName + " é um campo obrigatório");
        } else if (!name.matches("^[ a-zA-Z]+$")) {
            throw new RuntimeException(fieldName + " só pode conter letras");
        }
    }

    public static void validateStreetName(String streetName) {
        if (streetName.trim().isEmpty()) {
            throw new RuntimeException("o nome da rua é um campo obrigatório");
        } else if (!streetName.matches("^[ a-zA-Z0-9]+$")) {
            throw new RuntimeException("o nome da rua não pode conter caracteres especiais");
        }
    }

    public static PetType validateType(String type) {
        if (type.trim().isEmpty()) {
            throw new RuntimeException("O tipo de pet é um campo obrigatório");
        }

        PetType petType;
        try {
            petType = PetType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("O tipo do pet só pode ser Cachorro ou Gato");
        }
        return petType;
    }

    public static PetSex validateSex(String sex) {
        if (sex.trim().isEmpty()) {
            throw new RuntimeException("O sexo do pet é um campo obrigatório");
        }

        PetSex petSex;
        try {
            petSex = PetSex.valueOf(sex.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("O sexo do pet só pode ser Macho ou Femea");
        }
        return petSex;
    }

    public static String validateHouseNumber(String string) {
        if (string.trim().isEmpty()) {
            return NOT_INFORMED;
        }

        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("O número da casa deve ser um número inteiro");
        }

        return string;
    }

    public static String validateAge(String ageString) {
        if (ageString.trim().isEmpty()) {
            return NOT_INFORMED;
        }

        try {
            ageString = ageString.replace(',', '.');
            double age = Double.parseDouble(ageString);

            if (age <= 0 || age > 20) {
                throw new RuntimeException("A idade deve ser entre 0.1 e 20 anos.");
            }

        } catch (NumberFormatException e) {
            throw new NumberFormatException("A idade deve ser um número");
        }

        return ageString + " anos";
    }

    public static String validateWeight(String weightString) {
        if (weightString.trim().isEmpty()) {
            return NOT_INFORMED;
        }

        try {
            weightString = weightString.replace(',', '.');
            double weight = Double.parseDouble(weightString);

            if (weight < 0.5 || weight > 60.0) {
                throw new RuntimeException("O peso deve ser um número entre 0.5kg e 60kg");
            }

        } catch (NumberFormatException e) {
            throw new NumberFormatException("O peso deve ser um número");
        }

        return weightString + "kg";
    }
}