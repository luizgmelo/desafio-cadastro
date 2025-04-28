package br.com.luizgmelo.desafiocadastro.repositories;

import br.com.luizgmelo.desafiocadastro.models.Pet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PetRepository {
    public void savePet(Pet pet) {
        Path folderPetsCadastrados = createFolderPetsCadastrados();

        Path petFile = createPetFile(pet, folderPetsCadastrados);

        try (BufferedWriter bw = Files.newBufferedWriter(petFile)) {
            bw.write(pet.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados no arquivo");
        }
    }

    private Path createPetFile(Pet pet, Path folder) {
        String fileName = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm").format(LocalDateTime.now()) + " - " +
                pet.getName().toUpperCase().replaceAll("\\s", "") + ".txt";

        Path filePath = Paths.get(folder.toString(), fileName);
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo do pet");
            }
        }
        return filePath;
    }

    private Path createFolderPetsCadastrados() {
        Path fileFolder = Paths.get("petsCadastrados");
        if (Files.notExists(fileFolder)) {
            try {
                Files.createDirectory(fileFolder);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar pasta de pets cadastrados");
            }
        }
        return fileFolder;
    }
}
