package com.miproyecto.app;

import com.miproyecto.DAO.AutorDao;
import com.miproyecto.DAO.LibroDao;
import com.miproyecto.model.Autor;
import com.miproyecto.model.Libro;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CrudDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AutorDao autor = new AutorDao();
        LibroDao libro = new LibroDao();
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("**MENÚ DE LIBROS**");
            System.out.println("1. listar libros");
            System.out.println("2. añadir libro");
            System.out.println("3. cambiar disponibilidad");
            System.out.println("4. borrar libro");
            System.out.println("5. consultar libros por autor");
            System.out.println("0. salir");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case (1) -> {
                    libro.findAll().forEach(System.out::println);
                }
                case (2) -> {
                    System.out.println("Ingresa el título del libro");
                    String titulo = sc.nextLine();
                    System.out.println("Ingresa el ISBN del libro");
                    String isbn = sc.nextLine();
                    System.out.println("Ingresa el año e la publicación");
                    Integer anio = Integer.parseInt(sc.nextLine());
                    autor.findAll().forEach(System.out::println);

                    System.out.println("Ingresa el id del autor");
                    Integer autorId = Integer.parseInt(sc.nextLine());
                    Optional<Autor> autorBuscado = autor.findById(autorId);

                    if (autorBuscado.isEmpty()) {
                        System.out.println("Ingresa un ID válido");
                    } else {
                        Libro nuevoLibro = new Libro(titulo, isbn, anio, true, autorId);
                        int idGenerado = libro.insert(nuevoLibro);
                        System.out.println("Libro insertado correctamente con ID: " + idGenerado);
                        System.out.println(nuevoLibro);
                    }
                }
                case (3) -> {
                    System.out.println("Ingresa el id del libro");
                    Integer libroId = Integer.parseInt(sc.nextLine());
                    Optional<Libro> libroBuscado = libro.findById(libroId);

                    if (libroBuscado.isEmpty()) {
                        System.out.println("Ingresa un ID válido");
                    } else {
                        Libro libroEncontrado = libroBuscado.get();

                        boolean disponible = libroEncontrado.isDisponible();
                        boolean nuevoEstado = !disponible;

                        libroEncontrado.setDisponible(nuevoEstado);
                        boolean updated = libro.update(libroEncontrado);

                        if (updated) {
                            System.out.println("La disponibilidad se modificó correctamente");
                            System.out.println("Libro actualizado: " + libroEncontrado);
                        } else {
                            System.out.println("No se pudo actualizar el libro");
                        }
                    }
                }
                case (4) -> {
                    System.out.println("Ingresa el ID del libro a eliminar");
                    Integer id = Integer.parseInt(sc.nextLine());
                    boolean libroBorrar = libro.deleteById(id);
                    if (libroBorrar) {
                        System.out.println("El libro con ID " + id + " se eliminó correctamente.");
                    } else {
                        System.out.println("No se encontro el libro a eliminar");
                    }
                }
                case (5) -> {
                    System.out.println("Autores disponibles: ");
                    autor.findAll().forEach(System.out::println);

                    System.out.println("Ingresa el ID del autor");
                    Integer idAutor = Integer.parseInt(sc.nextLine());
                    List<Libro> librosAutor = libro.findByAutorId(idAutor);

                    if (librosAutor.isEmpty()) {
                        System.out.println("No hay libros asociados al autor con ID: " + idAutor);
                    } else {
                        librosAutor.forEach(System.out::println);
                    }
                }
                case (0) -> {
                    opcion = 0;
                    System.out.println("Saliendo del programa...");
                }
                default -> {
                    System.out.println("Opción no válida, ingresa un numero entre 0 y 5");
                }
            }

        }
    }
}