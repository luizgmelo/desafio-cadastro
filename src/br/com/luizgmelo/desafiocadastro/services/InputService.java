package br.com.luizgmelo.desafiocadastro.services;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputService {

    public static int readInt(Scanner scanner) {
       int option = -1;
        try {
            option = scanner.nextInt();

            if (option < 1 || option > 6) {
                System.out.println("\nEscolha uma opção entre 1-6");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nEscolha uma opção entre 1-6");
        } finally {
            scanner.nextLine();
        }
        return option;
    }

}
