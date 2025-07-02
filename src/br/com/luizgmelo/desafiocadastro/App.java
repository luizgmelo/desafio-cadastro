package br.com.luizgmelo.desafiocadastro;

import br.com.luizgmelo.desafiocadastro.database.DatabaseConnection;
import br.com.luizgmelo.desafiocadastro.views.UI;

import java.sql.SQLException;

// TODO Implement Database instead Files.txt

public class App {
    public static void main(String[] args) {
        try (var connection = DatabaseConnection.connect()) {
            UI menu = new UI();
            menu.run();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}