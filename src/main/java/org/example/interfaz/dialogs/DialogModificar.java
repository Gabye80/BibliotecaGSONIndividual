package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogModificar extends JDialog {
    public DialogModificar(JFrame parent, GestorLibros gestor, Runnable refrescar) {
        super(parent, "Modificar Libro", true);
        setSize(400, 350);
        setLayout(new GridLayout(7, 2, 5, 5));

        List<Libro> libros = gestor.listarLibros();
        JComboBox<Libro> combo = new JComboBox<>(libros.toArray(new Libro[0]));

        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtGenero = new JTextField();

        add(new JLabel("Seleccionar:")); add(combo);
        add(new JLabel("Nuevo Título:")); add(txtTitulo);
        add(new JLabel("Nuevo Autor:")); add(txtAutor);
        add(new JLabel("Nuevo Año:")); add(txtAnio);
        add(new JLabel("Nuevo Género:")); add(txtGenero);

        combo.addActionListener(e -> {
            Libro l = (Libro) combo.getSelectedItem();
            if (l != null) {
                txtTitulo.setText(l.getTitulo());
                txtAutor.setText(l.getAutor());
                txtAnio.setText(String.valueOf(l.getAnioPub()));
                txtGenero.setText(l.getGenero());
            }
        });

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones);

        btnGuardar.addActionListener(e -> {
            Libro seleccionado = (Libro) combo.getSelectedItem();
            if (seleccionado != null) {
                try {
                    // Crear libro con los nuevos datos (mismo ID)
                    Libro nuevo = new Libro(
                            seleccionado.getId(),  // Mantener el ID original
                            txtTitulo.getText().trim(),
                            txtAutor.getText().trim(),
                            Integer.parseInt(txtAnio.getText().trim()),
                            txtGenero.getText().trim()
                    );

                    // PRIMERO modificar en el gestor
                    gestor.modificar(seleccionado.getId(), nuevo);

                    // LUEGO refrescar la vista
                    refrescar.run();

                    // Mostrar mensaje y cerrar
                    JOptionPane.showMessageDialog(this, "Libro modificado correctamente");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Año inválido");
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        // Seleccionar el primer elemento por defecto
        if (libros.size() > 0) {
            combo.setSelectedIndex(0);
        }

        setLocationRelativeTo(parent);
        setVisible(true);
    }
}