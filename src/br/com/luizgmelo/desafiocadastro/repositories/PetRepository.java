package br.com.luizgmelo.desafiocadastro.repositories;

import br.com.luizgmelo.desafiocadastro.models.Address;
import br.com.luizgmelo.desafiocadastro.models.Pet;
import br.com.luizgmelo.desafiocadastro.models.PetSex;
import br.com.luizgmelo.desafiocadastro.models.PetType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetRepository {

    private final Path folder = Paths.get("petsCadastrados");

    public void savePet(Pet pet) {
        createFolderPetsCadastrados();

        Path petFile = createPetFile(pet);

        try (BufferedWriter bw = Files.newBufferedWriter(petFile)) {
            bw.write(pet.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados no arquivo");
        }
    }

    private Path createPetFile(Pet pet) {
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

    private void createFolderPetsCadastrados() {
        if (Files.notExists(folder)) {
            try {
                Files.createDirectory(folder);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar pasta de pets cadastrados");
            }
        }
    }

    public List<Pet> getPetList() {
        createFolderPetsCadastrados();

        List<Pet> petList = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
            for (Path path : directoryStream) {
                if (!Files.isDirectory(path)) {
                    Pet pet = getPetFromFile(path);
                    if (pet != null) {
                        petList.add(pet);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return petList;
    }

    public Pet getPetFromFile(Path file) throws IOException {
        List<String> allLines = Files.readAllLines(file);
        Map<Integer, String> data = new HashMap<>();

        if (allLines.isEmpty()) {
            return null;
        }

        for (String line : allLines) {
            String[] part = line.split("-", 2);
            data.put(Integer.parseInt(part[0].trim()), part[1].trim());
        }

        String[] address = data.get(4).split(",");

        return new Pet(
                data.get(1),
                PetType.valueOf(data.get(2).toUpperCase()),
                PetSex.valueOf(data.get(3).toUpperCase()),
                new Address(address[0], address[1], address[2]),
                data.get(5),
                data.get(6),
                data.get(7)
        );
    }

    public Path getPetFile(Pet pet) {
        Path folder = Paths.get("petsCadastrados");
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
            for (Path path : directoryStream) {
                if (!Files.isDirectory(path)) {
                    Pet petFromFile = getPetFromFile(path);
                    if (petFromFile.getName().equalsIgnoreCase(pet.getName())) {
                        return path;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
