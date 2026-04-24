package org.example;

public class Libro {

    private String id;
    private String titulo;
    private String autor;
    private int anioPub;
    private String genero;

    public Libro(String id, String titulo, String autor, int anioPub, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPub = anioPub;
        this.genero = genero;
    }

    public Libro() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAnioPub() {
        return anioPub;
    }

    public void setAnioPub(int anioPub) {
        this.anioPub = anioPub;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return titulo + " | " + autor + "| " + anioPub;
    }
}