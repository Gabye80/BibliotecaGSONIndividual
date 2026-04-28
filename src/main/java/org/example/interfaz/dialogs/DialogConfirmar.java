package org.example.interfaz.dialogs;

import javax.swing.*;

public class DialogConfirmar {
    public static boolean confirmar(JFrame parent, String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(
                parent,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return opcion == JOptionPane.YES_OPTION;
    }
}