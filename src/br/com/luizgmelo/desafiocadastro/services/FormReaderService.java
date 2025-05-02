package br.com.luizgmelo.desafiocadastro.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FormReaderService {
    private Scanner fileScanner;
    private String filePath;

    public FormReaderService(String filePath) {
        this.filePath = filePath;
        open();
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

    public void open() {
        try {
            File file = new File(this.filePath);
            this.fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo de formulário não encontrado: " + this.filePath);
        }
    }

    public void close() {
        fileScanner.close();
    }
}
