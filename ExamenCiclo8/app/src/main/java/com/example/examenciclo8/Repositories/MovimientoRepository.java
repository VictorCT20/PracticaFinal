package com.example.examenciclo8.Repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.Clases.Movimiento;

import java.util.List;

@Dao
public interface MovimientoRepository {

    @Query("SELECT * FROM Movimiento")
    List<Movimiento> getAll();

    @Insert
    void create(Movimiento movimiento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movimiento> movimientos);

    @Query("SELECT * FROM Movimiento WHERE synced = 0")
    List<Cuenta> getUnsyncedMovimiento();

    @Update
    void updateMovimiento(Movimiento movimiento);

    @Query("SELECT * FROM Movimiento WHERE cuentaId = :cuentaId")
    List<Movimiento> getMovimientosByCuentaId(int cuentaId);



}
