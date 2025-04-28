package br.com.luizgmelo.desafiocadastro.services;

public enum ValidateTypes {
    NOME,
    TIPO,
    SEXO,
    IDADE,
    PESO,
    RUA,
    NUMEROCASA,
    CIDADE,
    ENDERECO,
    RACA;

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
           case ENDERECO:

       }
       
       throw new IllegalArgumentException();
   }



}
