package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogModificar extends JDialog {

    public DialogModificar(JFrame parent, GestorLibros gestor, Runnable refrescar) {
        super(parent, "Modificar Libro", true);
        setSize(450, 400);
        setLayout(new BorderLayout(10, 10));

        List<Libro> libros = gestor.listarLibros();
        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros para modificar.");
            dispose();
            return;
        }

        // Panel superior: selección
        JPanel panelSeleccion = new JPanel(new FlowLayout());
        JLabel lblSeleccion = new JLabel("Seleccionar libro:");
        JComboBox<Libro> combo = new JComboBox<>(libros.toArray(new Libro[0]));
        panelSeleccion.add(lblSeleccion);
        panelSeleccion.add(combo);

        // Panel central: campos editables
        JPanel panelDatos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtGenero = new JTextField();

        panelDatos.add(new JLabel("ID (no editable):"));
        panelDatos.add(lblId);
        panelDatos.add(new JLabel("Nuevo Título:"));
        panelDatos.add(txtTitulo);
        panelDatos.add(new JLabel("Nuevo Autor:"));
        panelDatos.add(txtAutor);
        panelDatos.add(new JLabel("Nuevo Año:"));
        panelDatos.add(txtAnio);
        panelDatos.add(new JLabel("Nuevo Género:"));
        panelDatos.add(txtGenero);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelSeleccion, BorderLayout.NORTH);
        add(panelDatos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos del libro seleccionado
        Runnable cargarDatos = () -> {
            Libro l = (Libro) combo.getSelectedItem();
            if (l != null) {
                lblId.setText(l.getId());
                txtTitulo.setText(l.getTitulo());
                txtAutor.setText(l.getAutor());
                txtAnio.setText(String.valueOf(l.getAnioPub()));
                txtGenero.setText(l.getGenero());
            }
        };
        combo.addActionListener(e -> cargarDatos.run());
        cargarDatos.run();

        btnGuardar.addActionListener(e -> {
            Libro seleccionado = (Libro) combo.getSelectedItem();
            if (seleccionado == null) return;

            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String anioStr = txtAnio.getText().trim();
            String genero = txtGenero.getText().trim();

            // Validaciones de campos vacíos
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

            // Si pasa validaciones, se modifica el libro
            Libro libroModificado = new Libro(seleccionado.getId(), titulo, autor, anio, genero);
            gestor.modificar(seleccionado.getId(), libroModificado);
            refrescar.run();
            JOptionPane.showMessageDialog(this, "✓ Libro modificado correctamente.");
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