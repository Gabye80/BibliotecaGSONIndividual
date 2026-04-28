package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;

public class DialogAgregar extends JDialog {

    public DialogAgregar(JFrame parent, GestorLibros gestor, Runnable refrescar) {
        super(parent, "Agregar Libro", true);
        setSize(400, 350);
        setLayout(new GridLayout(7, 2, 10, 10));

        JTextField txtId = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtGenero = new JTextField();

        add(new JLabel("ID (único):"));
        add(txtId);
        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Autor:"));
        add(txtAutor);
        add(new JLabel("Año publicación:"));
        add(txtAnio);
        add(new JLabel("Género:"));
        add(txtGenero);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones);

        btnGuardar.addActionListener(e -> {
            String id = txtId.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String anioStr = txtAnio.getText().trim();
            String genero = txtGenero.getText().trim();

            // Validación de campos vacíos
            if (id.isEmpty()) {
                mostrarError("El ID no puede estar vacío.");
                return;
            }

            if (titulo.isEmpty()) {
                mostrarError("El título no puede estar vacío.");
                return;
            }

            if (autor.isEmpty()) {
                mostrarError("El autor no puede estar vacío.");
                return;
            }

            if (genero.isEmpty()) {
                mostrarError("El género no puede estar vacío.");
                return;
            }

            // Validar que año es un integer valido
            int anio;
            try {
                anio = Integer.parseInt(anioStr);
            } catch (NumberFormatException ex) {
                mostrarError("El año debe ser un número entero válido.");
                return;
            }

            // Validar ID único
            if (gestor.buscarPorId(id) != null) {
                mostrarError("Ya existe un libro con el ID '" + id + "'. Usa otro ID.");
                return;
            }

            // Guardar tras pasar validaciones
            Libro nuevoLibro = new Libro(id, titulo, autor, anio, genero);
            gestor.agregar(nuevoLibro);
            refrescar.run();
            JOptionPane.showMessageDialog(this, "✓ Libro añadido correctamente.");
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
}