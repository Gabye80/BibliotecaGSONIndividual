package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestorLibros {

    private static final String ARCHIVO = "libros.json";
    private final Gson gson;

    public GestorLibros() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private List<Libro> leerLibros() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Libro[] array = gson.fromJson(reader, Libro[].class);
            if (array == null) return new ArrayList<>();
            return new ArrayList<>(Arrays.asList(array));

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Libro> listarLibros() {
        return leerLibros();
    }

    private void guardarLibro(List<Libro> libros) {
        try (Writer writer = new FileWriter(ARCHIVO)) {
            gson.toJson(libros, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar el fichero: " + e.getMessage());
        }
    }

    public void agregar(Libro libro) {
        List<Libro> lista = leerLibros();
        lista.add(libro);
        guardarLibro(lista);
        System.out.println("Libro añadido correctamente.");
    }

    public void modificar(String id, Libro nuevo) {
        List<Libro> lista = leerLibros();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(id)) {
                nuevo.setId(id);
                lista.set(i, nuevo);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            guardarLibro(lista);
            System.out.println("Libro modificado correctamente.");
        } else {
            System.out.println("No se encontró ningún libro con id " + id);
        }
    }

    public void eliminar(String id) {
        List<Libro> lista = leerLibros();
        boolean eliminado = lista.removeIf(l -> l.getId().equals(id));

        if (eliminado) {
            guardarLibro(lista);
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún libro con id " + id);
        }
    }

    public Libro buscarPorId(String id) {
        List<Libro> lista = leerLibros();

        for (Libro l : lista) {
            if (l.getId().equals(id)) return l;
        }

        return null;
    }


}