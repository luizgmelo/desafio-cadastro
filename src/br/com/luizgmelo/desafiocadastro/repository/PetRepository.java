package br.com.luizgmelo.desafiocadastro.repository;

import br.com.luizgmelo.desafiocadastro.model.Pet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PetRepository {
    public void savePet(Pet pet) {
        String fileName = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm").format(LocalDateTime.now()) + " - " +
                pet.getName().toUpperCase().replaceAll("\\s", "") + ".txt";

        Path fileFolder = Paths.get("petsCadastrados");
        if (Files.notExists(fileFolder)) {
            try {
                Files.createDirectory(fileFolder);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar pasta de pets cadastrados");
            }
        }

        Path filePath = Paths.get(fileFolder.toString(), fileName);
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo do pet");
            }
        }

        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            bw.write(pet.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados no arquivo");
        }
    }
}
