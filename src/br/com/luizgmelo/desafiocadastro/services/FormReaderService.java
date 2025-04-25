package br.com.luizgmelo.desafiocadastro.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FormReaderService {
    private final Scanner fileScanner;

    public FormReaderService(String filePath) {
        try {
            File file = new File(filePath);
            this.fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo de formulário não encontrado: " + filePath);
        }
    }

    public boolean hasNextQuestion() {
        return fileScanner.hasNextLine();
    }

    public String getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new IllegalStateException("Não há mais perguntas no formulário.");
        }
        return fileScanner.nextLine();
    }

    public void close() {
        fileScanner.close();
    }
}
