package org.example;

import org.example.interfaz.MainView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainView();
        });

        // Se ha utilizado IA para el estilizado visual de la interfaz (No tengo la extensión visual de JFrame).
        // Y también para solucionar un error con la función de búsqueda (Todas las variables tenían que ser finales para su uso en la funcón lambda).
    }
}