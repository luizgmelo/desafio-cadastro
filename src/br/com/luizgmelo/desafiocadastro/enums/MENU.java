package br.com.luizgmelo.desafiocadastro.enums;

public enum MENU {
    CADASTRAR_PET(1),
    BUSCAR_PET(2),
    LISTAR_PETS(3),
    ALTERAR_PET(4),
    DELETAR_PET(5),
    SAIR(6);

    private final int value;

    MENU(int value) {
        this.value = value;
    }
}
