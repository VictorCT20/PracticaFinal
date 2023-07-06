package com.example.examenciclo8.Clases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cuentas")
public class Cuenta {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;

    @ColumnInfo(name = "synced")
    private boolean synced;

    public Cuenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
