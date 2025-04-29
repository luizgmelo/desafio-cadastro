package br.com.luizgmelo.desafiocadastro.enums;

import br.com.luizgmelo.desafiocadastro.services.ValidateService;

public enum ValidateType {
    NOME("1", "Nome"),
    TIPO("2", "Tipo"),
    SEXO("3", "Sexo"),
    RUA("4-1", "Rua"),
    NUMEROCASA("4-2", "NumeroCasa"),
    CIDADE("4-3", "Cidade"),
    IDADE("5", "Idade"),
    PESO("6", "Peso"),
    RACA("7", "Raca");

    private final String fileValue;
    private final String type;
    ValidateType(String fileValue, String type) {
        this.fileValue = fileValue;
        this.type = type;
    }

    public static ValidateType getByFileValue(String fileValue) {
        for (ValidateType validateType : ValidateType.values()) {
            if (validateType.fileValue.equals(fileValue)) {
                return validateType;
            }
        }
        return null;
    }

    public static ValidateType getByType(String type) {
        for (ValidateType validateType : ValidateType.values()) {
            if (validateType.name().equalsIgnoreCase(type)) {
                return validateType;
            }
        }
        return null;
    }

    public <T> Object validate (String value) {
       switch (this) {
           case NOME:
           case RACA:
               ValidateService.validateName(value, "nome");
               return null;
           case TIPO:
               return ValidateService.validateType(value);
           case SEXO:
               return ValidateService.validateSex(value);
           case IDADE:
               return ValidateService.validateAge(value);
           case PESO:
               return ValidateService.validateWeight(value);
           case RUA:
               ValidateService.validateStreetName(value);
               return null;
           case CIDADE:
               ValidateService.validateName(value, "nome da cidade");
               return null;
           case NUMEROCASA:
                return ValidateService.validateHouseNumber(value);
       }
       
       throw new IllegalArgumentException();
   }

    public String getType() {
        return type;
    }
}
