package br.com.luizgmelo.desafiocadastro.service;

import br.com.luizgmelo.desafiocadastro.model.PetSex;
import br.com.luizgmelo.desafiocadastro.model.PetType;

public class ValidateService {
    final String NOT_INFORMED = "NÃO INFORMADO";

    public boolean isEmpty(String string) {
        return string.trim().isEmpty();
    }

    public void validateName(String name, String fieldName) {
        if (isEmpty(name)) {
            throw new RuntimeException(fieldName + " é um campo obrigatório");
        } else if (hasNumbersOrSpecialCharacters(name)) {
            throw new RuntimeException(fieldName + " só pode conter letras");
        }
    }

    public void validateStreetName(String streetName) {
        if (isEmpty(streetName)) {
            throw new RuntimeException("o nome da rua é um campo obrigatório");
        } else if (!streetName.matches("^[ a-zA-Z0-9]+$")) {
            throw new RuntimeException("o nome da rua não pode conter caracteres especiais");
        }
    }

    public PetType validateType(String typeString) {
        PetType type;
        try {
            type = PetType.valueOf(capitalize(typeString));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("O tipo do pet só pode ser Cachorro ou Gato");
        }
        return type;
    }

    public PetSex validateSex(String sexString) {
        PetSex sex;
        try {
            sex = PetSex.valueOf(capitalize(sexString));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("O sexo do pet só pode ser Macho ou Femea");
        }
        return sex;
    }

    public String validateHouseNumber(String string) {
        if (isEmpty(string)) {
            return NOT_INFORMED;
        }

        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("O número da casa deve ser um número inteiro");
        }

        return string;
    }

    public String validateAge(String ageString) {
        if (isEmpty(ageString)) {
            return NOT_INFORMED;
        }

        try {
            double age = Double.parseDouble(ageString.replace(',', '.'));

            if (age <= 0 || age > 20) {
                throw new RuntimeException("A idade deve ser entre 0.1 e 20 anos.");
            }

            if (age < 1) {
                return (int) (age * 12) + " meses";
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("A idade deve ser um número");
        }

        return ageString + " anos";
    }

    public String validateWeight(String weightString) {
        if (isEmpty(weightString)) {
            return NOT_INFORMED;
        }

        try {
            double weight = Double.parseDouble(weightString.replace(',', '.'));

            if (weight < 0.5 || weight > 60.0) {
                throw new RuntimeException("O peso deve ser um número entre 0.5kg e 60kg");
            }

        } catch (NumberFormatException e) {
            throw new NumberFormatException("O peso deve ser um número");
        }

        return weightString + "kg";
    }

    public boolean hasNumbersOrSpecialCharacters(String string) {
        return !string.matches("^[ a-zA-Z]+$");
    }

    public String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}