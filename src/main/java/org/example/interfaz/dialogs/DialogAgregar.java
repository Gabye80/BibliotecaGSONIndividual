package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;

public class DialogAgregar extends JDialog {

    public DialogAgregar(JFrame parent, GestorLibros gestor, Runnable refrescar) {
        super(parent, "Agregar Nuevo Libro", true);
        setSize(450, 380);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel central con GridLayout para etiquetas y campos (2 columnas)
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 15, 12));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField txtId = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtGenero = new JTextField();

        // Estilo opcional: ancho preferido
        txtId.setPreferredSize(new Dimension(200, 28));
        txtTitulo.setPreferredSize(new Dimension(200, 28));
        txtAutor.setPreferredSize(new Dimension(200, 28));
        txtAnio.setPreferredSize(new Dimension(200, 28));
        txtGenero.setPreferredSize(new Dimension(200, 28));

        panelCampos.add(new JLabel("ID (único):"));
        panelCampos.add(txtId);
        panelCampos.add(new JLabel("Título:"));
        panelCampos.add(txtTitulo);
        panelCampos.add(new JLabel("Autor:"));
        panelCampos.add(txtAutor);
        panelCampos.add(new JLabel("Año publicación:"));
        panelCampos.add(txtAnio);
        panelCampos.add(new JLabel("Género:"));
        panelCampos.add(txtGenero);

        // Panel inferior con botones centrados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.setPreferredSize(new Dimension(100, 32));
        btnCancelar.setPreferredSize(new Dimension(100, 32));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

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