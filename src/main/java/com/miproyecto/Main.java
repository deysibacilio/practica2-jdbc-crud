package com.miproyecto;

import com.miproyecto.DAO.LibroDao;
import com.miproyecto.model.Libro;

public class Main {
    public static void main(String[] args) {
        var dao = new LibroDao();

        System.out.println("======== ESTADO INICIAL ======");
        dao.findAll().forEach(System.out::println);
    }
}