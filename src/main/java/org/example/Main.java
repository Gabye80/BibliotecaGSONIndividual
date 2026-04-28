package org.example;

import org.example.interfaz.MainView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainView();
        });

    }
}