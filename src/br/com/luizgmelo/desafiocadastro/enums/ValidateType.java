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
               return ValidateService.validateName(value, "nome");
           case TIPO:
               return ValidateService.validateType(value);
           case SEXO:
               return ValidateService.validateSex(value);
           case IDADE:
               return ValidateService.validateAge(value);
           case PESO:
               return ValidateService.validateWeight(value);
           case RUA:
               return ValidateService.validateStreetName(value);
           case CIDADE:
               return ValidateService.validateName(value, "nome da cidade");
           case NUMEROCASA:
                return ValidateService.validateHouseNumber(value);
           case RACA:
               return ValidateService.validateName(value, "raca");
       }
       
       throw new IllegalArgumentException();
   }

    public String getType() {
        return type;
    }
}
