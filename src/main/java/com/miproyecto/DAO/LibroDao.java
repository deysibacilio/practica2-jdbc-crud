package com.miproyecto.DAO;

import com.miproyecto.db.Db;
import com.miproyecto.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDao {

    private Libro mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String isbn = rs.getString("isbn");
        Integer anio = (Integer) rs.getObject("anio");
        boolean disponible = rs.getBoolean("disponible");

        return new Libro(id, titulo, isbn, anio, disponible);
    }

    public List<Libro> findAll() {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro ORDER BY id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Libro> libros = new ArrayList<>();

            while (rs.next()){
                libros.add(mapRow(rs));
            }

            return libros;

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }
}
