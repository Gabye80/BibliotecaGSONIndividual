package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogEliminar extends JDialog {

    public DialogEliminar(JFrame parent, GestorLibros gestor, Runnable refrescar) {
        super(parent, "Eliminar Libro", true);
        setSize(400, 150);
        setLayout(new BorderLayout());

        List<Libro> libros = gestor.listarLibros();
        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros para eliminar.");
            dispose();
            return;
        }

        JComboBox<Libro> combo = new JComboBox<>(libros.toArray(new Libro[0]));
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        add(combo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> {
            Libro seleccionado = (Libro) combo.getSelectedItem();
            if (seleccionado == null) return;

            // Vonfirmacion antes de eliminar
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar el libro?\n" +
                            "ID: " + seleccionado.getId() + "\n" +
                            "Título: " + seleccionado.getTitulo(),
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                gestor.eliminar(seleccionado.getId());
                refrescar.run();
                JOptionPane.showMessageDialog(this, "✓ Libro eliminado.");
                dispose();
            }
        });

        btnCancelar.addActionListener(e -> dispose());
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}