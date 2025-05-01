package br.com.luizgmelo.desafiocadastro.enums;

import br.com.luizgmelo.desafiocadastro.services.ValidateService;

public enum ValidateType {
    NOME("Nome"),
    TIPO("Tipo"),
    SEXO("Sexo"),
    RUA("Rua"),
    NUMEROCASA( "NumeroCasa"),
    CIDADE("Cidade"),
    IDADE( "Idade"),
    PESO( "Peso"),
    RACA( "Raca");

    private final String type;
    ValidateType(String type) {
        this.type = type;
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
           case RACA:
               ValidateService.validateName(value, "raca");
               return null;
       }
       
       throw new IllegalArgumentException();
   }

    public String getType() {
        return type;
    }
}
