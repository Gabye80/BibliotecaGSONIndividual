package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;

public class DialogAgregar extends JDialog {
    public DialogAgregar(JFrame parent, GestorLibros gestor, Runnable refrescar) {
        super(parent, "Agregar Libro", true);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 5, 5));

        JTextField txtId = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtGenero = new JTextField();

        add(new JLabel("ID:")); add(txtId);
        add(new JLabel("Título:")); add(txtTitulo);
        add(new JLabel("Autor:")); add(txtAutor);
        add(new JLabel("Año:")); add(txtAnio);
        add(new JLabel("Género:")); add(txtGenero);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones);

        btnGuardar.addActionListener(e -> {
            String id = txtId.getText().trim();

            if (gestor.buscarPorId(id) != null) {
                JOptionPane.showMessageDialog(this, "El ID ya existe");
                return;
            }

            try {
                Libro libro = new Libro(
                        id,
                        txtTitulo.getText().trim(),
                        txtAutor.getText().trim(),
                        Integer.parseInt(txtAnio.getText().trim()),
                        txtGenero.getText().trim()
                );
                gestor.agregar(libro);
                refrescar.run();
                JOptionPane.showMessageDialog(this, "Libro añadido");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Año inválido");
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setLocationRelativeTo(parent);
        setVisible(true);
    }
}