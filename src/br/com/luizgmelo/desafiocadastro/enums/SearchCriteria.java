package br.com.luizgmelo.desafiocadastro.enums;

public enum SearchCriteria {
    NOME("Nome", ValidateType.NOME),
    SEXO("Sexo", ValidateType.SEXO),
    IDADE("Idade", ValidateType.IDADE),
    RUA("Rua", ValidateType.RUA),
    NUMERO("Numero", ValidateType.NUMEROCASA),
    CIDADE("Cidade", ValidateType.CIDADE),
    PESO("Peso", ValidateType.PESO),
    RACA("Raca", ValidateType.RACA);

    private final String label;
    private final ValidateType validateType;

    SearchCriteria(String label, ValidateType validateType) {
        this.label = label;
        this.validateType = validateType;
    }

    public String getLabel() {
        return label;
    }

    public ValidateType getValidateType() {
        return validateType;
    }
}
