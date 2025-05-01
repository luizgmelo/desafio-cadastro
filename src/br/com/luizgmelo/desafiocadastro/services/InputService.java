package br.com.luizgmelo.desafiocadastro.services;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputService {

    public static int readInt(Scanner scanner) {
       int option = -1;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Opção inválida, Digite apenas o número da opção");
        } finally {
            scanner.nextLine();
        }
        return option;
    }

}
