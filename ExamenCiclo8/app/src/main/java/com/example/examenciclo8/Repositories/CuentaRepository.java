package com.example.examenciclo8.Repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.examenciclo8.Clases.Cuenta;

import java.util.List;

@Dao
public interface CuentaRepository {

    @Query("SELECT * FROM Cuentas")
    List<Cuenta> getAll();

    @Insert
    void create(Cuenta cuenta);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Cuenta> cuentas);

    @Query("SELECT * FROM Cuentas WHERE synced = 0")
    List<Cuenta> getUnsyncedCuentas();

    @Update
    void updateCuenta(Cuenta cuenta);

}
