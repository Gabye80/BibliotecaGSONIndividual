package org.example.interfaz;

import org.example.GestorLibros;
import org.example.Libro;
import org.example.interfaz.dialogs.DialogAgregar;
import org.example.interfaz.dialogs.DialogBuscar;
import org.example.interfaz.dialogs.DialogModificar;
import org.example.interfaz.dialogs.DialogEliminar;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private GestorLibros gestor;
    private JTextArea areaSalida;

    public MainView() {
        gestor = new GestorLibros();

        setTitle("Gestor de Libros de Fantasía");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Área de lista con fuente monoespaciada para mejor formato
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaSalida);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");


        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);

        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Eventos
        btnAgregar.addActionListener(e ->
                new DialogAgregar(this, gestor, this::refrescar));

        btnModificar.addActionListener(e ->
                new DialogModificar(this, gestor, this::refrescar));

        btnEliminar.addActionListener(e ->
                new DialogEliminar(this, gestor, this::refrescar));

        btnBuscar.addActionListener(e ->
                new DialogBuscar(this, gestor));

        refrescar();
        setVisible(true);
    }

    public void refrescar() {
        areaSalida.setText("");

        // Cabecera de la tabla
        String header = String.format("%-10s | %-30s | %-20s | %-6s | %-15s\n",
                "ID", "TÍTULO", "AUTOR", "AÑO", "GÉNERO");
        String separator = "------------+--------------------------------+----------------------+--------+-----------------\n";

        areaSalida.append(header);
        areaSalida.append(separator);

        for (var libro : gestor.listarLibros()) {
            String linea = String.format("%-10s | %-30s | %-20s | %-6d | %-15s\n",
                    libro.getId(),
                    truncar(libro.getTitulo(), 30),
                    truncar(libro.getAutor(), 20),
                    libro.getAnioPub(),
                    truncar(libro.getGenero(), 15));
            areaSalida.append(linea);
        }
    }

    // Método auxiliar para truncar textos largos
    private String truncar(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView());
    }
}