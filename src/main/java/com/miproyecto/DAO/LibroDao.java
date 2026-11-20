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
        Integer autorId = (Integer) rs.getObject("autor_id");
        boolean disponible = rs.getBoolean("disponible");

        return new Libro(id, titulo, isbn, anio, disponible, autorId);
    }

    public List<Libro> findAll() {
        String sql = "SELECT id, titulo, isbn, anio, disponible, autor_id FROM libro ORDER BY id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Libro> libros = new ArrayList<>();

            while (rs.next()){
                libros.add(mapRow(rs));
            }

            return libros;

        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }

    public Optional<Libro> findById(int id) {
        String sql = "SELECT id, titulo, isbn, anio, disponible, autor_id FROM libro WHERE id = ?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }

                return Optional.empty();
            }
        } catch (SQLException e){
            throw new RuntimeException("Error buscando libro por id=" + id, e);
        }
    }
    public List<Libro> findByAutorId(int autorId) {
        String sql = "SELECT id, titulo, isbn, anio, disponible, autor_id FROM libro WHERE autor_id = ? ORDER BY id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, autorId);

            try(ResultSet rs = ps.executeQuery()) {
                List<Libro> libros = new ArrayList<>();
                while (rs.next()) libros.add(mapRow(rs));
                return libros;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando libros del autor id=" + autorId, e);
        }
    }

    public int insert(Libro libro) {
        String sql = "INSERT INTO libro (titulo, isbn, anio, disponible, autor_id) VALUES (?,?,?,?,?)";

        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setObject(3, libro.getAnio());
            ps.setBoolean(4, libro.isDisponible());
            ps.setObject(5, libro.getAutorId());

            int filas = ps.executeUpdate();
            if (filas != 1){
                throw new SQLException("INSERT inesperado. Filas afectadas: " + filas);
            }

            try (ResultSet keys = ps.getGeneratedKeys()){
                if (keys.next()){
                    int id = keys.getInt(1);
                    libro.setId(id);
                    return id;
                }
                throw new SQLException("No se devolvió id generado (getGenerateKeys vacío)");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando libro", e);
        }
    }

    public boolean update (Libro libro){
        if (libro.getId() == null){
            throw new IllegalArgumentException("No se puede UPDATE sin id. ¿Hiciste insert primero?");
        }

        String sql = "UPDATE libro SET titulo=?, isbn=?, anio=?, disponible=?, autor_id=? WHERE id=?";

        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setObject(3,libro.getAnio());
            ps.setBoolean(4, libro.isDisponible());
            ps.setObject(5,libro.getAutorId());
            ps.setInt(6, libro.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando libro id=" + libro.getId(), e);
        }
    }

    public boolean deleteById(int id){
        String sql = "DELETE FROM libro WHERE id=?";

        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error borrando libro id=" + id, e);
        }
    }
}
