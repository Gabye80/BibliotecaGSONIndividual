package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        GestorLibros g = new GestorLibros();

        g.agregar(new Libro("1", "El caimino de los reyes", "Brandon Sanderson", 2010, "Fantasia"));

        System.out.println(g.listarLibros());

        g.eliminar("1");
        System.out.println(g.listarLibros());
    }
}