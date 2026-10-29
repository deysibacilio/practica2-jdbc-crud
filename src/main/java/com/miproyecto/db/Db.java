package com.miproyecto.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {
    //objeto de una sola copa para toda la aplicación que nunca cambiara
    private static final Properties PROPS = new Properties();

    //bloque estatico que se ejecuta una sola vez, cuando la clase se carga por primera vez
    static {
        //intenta abrir el archivo db.properties como un InputStream, busca el archivo dentro de resources
        try (InputStream in = Db.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("No se encuentra db.properties en src/main/resources");
            }
            //lee el contenidoy lo mete dentro del objeto PROPS
            PROPS.load(in);
        } catch (Exception e){
            throw new RuntimeException("Error cargando db.properties", e);
        }
    }

    //devuelve una conexión
    public static Connection getConnection() throws SQLException {
        //datos para abrir la conexión con PostgreSQL
        return DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password")
        );
    }
}
