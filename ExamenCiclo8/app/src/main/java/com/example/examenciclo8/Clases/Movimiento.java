package com.example.examenciclo8.Clases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movimiento")
public class Movimiento {

    @PrimaryKey()
    public int id;

    @ColumnInfo(name = "cuentaId")
    public int cuentaId;
    @ColumnInfo(name = "tipo")
    public String tipo;
    @ColumnInfo(name = "monto")
    public Double monto;
    @ColumnInfo(name = "motivo")
    public String motivo;
    @ColumnInfo(name = "latitud")
    public Double latitud;
    @ColumnInfo(name = "longitud")
    public Double longitud;
    @ColumnInfo(name = "imagen")
    public String imagen;
    @ColumnInfo(name = "synced")
    private boolean synced;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(int cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
