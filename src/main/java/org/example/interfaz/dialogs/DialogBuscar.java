package org.example.interfaz.dialogs;

import org.example.GestorLibros;
import org.example.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DialogBuscar extends JDialog {

    private JTextArea areaResultados;
    private GestorLibros gestor;

    public DialogBuscar(JFrame parent, GestorLibros gestor) {
        super(parent, "Buscar Libros", true);
        this.gestor = gestor;
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel de criterios de búsqueda
        JPanel panelCriterios = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCriterios.setBorder(BorderFactory.createTitledBorder("Criterios de búsqueda"));

        JTextField txtId = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtAnio = new JTextField();

        panelCriterios.add(new JLabel("ID:"));
        panelCriterios.add(txtId);
        panelCriterios.add(new JLabel("Título (contiene):"));
        panelCriterios.add(txtTitulo);
        panelCriterios.add(new JLabel("Autor (contiene):"));
        panelCriterios.add(txtAutor);
        panelCriterios.add(new JLabel("Género (contiene):"));
        panelCriterios.add(txtGenero);
        panelCriterios.add(new JLabel("Año exacto:"));
        panelCriterios.add(txtAnio);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        // Área de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));

        add(panelCriterios, BorderLayout.NORTH);
        add(scrollResultados, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de búsqueda
        btnBuscar.addActionListener(e -> realizarBusqueda(txtId, txtTitulo, txtAutor, txtGenero, txtAnio));
        btnLimpiar.addActionListener(e -> limpiarCampos(txtId, txtTitulo, txtAutor, txtGenero, txtAnio));
        btnCerrar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void realizarBusqueda(JTextField txtId, JTextField txtTitulo,
                                  JTextField txtAutor, JTextField txtGenero,
                                  JTextField txtAnio) {
        String id = txtId.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String genero = txtGenero.getText().trim();
        String anioStr = txtAnio.getText().trim();

        // VALIDAR: Al menos un campo* rellenado
        if (id.isEmpty() && titulo.isEmpty() && autor.isEmpty() && genero.isEmpty() && anioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debes ingresar al menos un criterio de búsqueda.",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que año sea número válido
        final Integer anio;  // Declarada como final para usar en lambda
        if (!anioStr.isEmpty()) {
            try {
                anio = Integer.parseInt(anioStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "El año debe ser un número entero válido.",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            anio = null;
        }

        // Variables finales para usar en lambda
        final String finalId = id;
        final String finalTitulo = titulo;
        final String finalAutor = autor;
        final String finalGenero = genero;

        // Realizar búsqueda
        List<Libro> libros = gestor.listarLibros();
        List<Libro> resultados = libros.stream()
                .filter(l -> finalId.isEmpty() || l.getId().equalsIgnoreCase(finalId))
                .filter(l -> finalTitulo.isEmpty() || l.getTitulo().toLowerCase().contains(finalTitulo.toLowerCase()))
                .filter(l -> finalAutor.isEmpty() || l.getAutor().toLowerCase().contains(finalAutor.toLowerCase()))
                .filter(l -> finalGenero.isEmpty() || l.getGenero().toLowerCase().contains(finalGenero.toLowerCase()))
                .filter(l -> anio == null || l.getAnioPub() == anio)
                .collect(Collectors.toList());

        mostrarResultados(resultados);
    }

    private void mostrarResultados(List<Libro> resultados) {
        areaResultados.setText("");
        if (resultados.isEmpty()) {
            areaResultados.append("No se encontraron libros con esos criterios.\n");
            return;
        }

        // Cabecera con formato de tabla
        String header = String.format("%-10s | %-30s | %-20s | %-6s | %-15s\n",
                "ID", "TÍTULO", "AUTOR", "AÑO", "GÉNERO");
        String separator = "------------+--------------------------------+----------------------+--------+-----------------\n";
        areaResultados.append(header);
        areaResultados.append(separator);

        for (Libro libro : resultados) {
            String linea = String.format("%-10s | %-30s | %-20s | %-6d | %-15s\n",
                    libro.getId(),
                    truncar(libro.getTitulo(), 30),
                    truncar(libro.getAutor(), 20),
                    libro.getAnioPub(),
                    truncar(libro.getGenero(), 15));
            areaResultados.append(linea);
        }
    }

    private String truncar(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }

    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
        areaResultados.setText("");
    }
}