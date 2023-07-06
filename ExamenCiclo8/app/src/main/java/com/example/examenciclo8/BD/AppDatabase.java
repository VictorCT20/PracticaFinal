package com.example.examenciclo8.BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.Clases.Movimiento;
import com.example.examenciclo8.Repositories.CuentaRepository;
import com.example.examenciclo8.Repositories.MovimientoRepository;

@Database(entities = {Cuenta.class, Movimiento.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CuentaRepository cuentaRepository();
    public abstract MovimientoRepository movimientoRepository();

    public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "CuentaBD")
                .allowMainThreadQueries()
                .build();
    }

}
