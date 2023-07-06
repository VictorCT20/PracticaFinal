package com.example.examenciclo8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.examenciclo8.BD.AppDatabase;
import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.Clases.Movimiento;
import com.example.examenciclo8.Repositories.CuentaRepository;
import com.example.examenciclo8.Repositories.MovimientoRepository;
import com.example.examenciclo8.Service.CuentaService;
import com.example.examenciclo8.Utilities.RetrofitU;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetallesCuentaActivity extends AppCompatActivity {


    Cuenta cuenta;
    Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_cuenta);

        mRetrofit = RetrofitU.build();

        float saldoF = 0;

        int position = getIntent().getIntExtra("position", 0);

        AppDatabase db = AppDatabase.getInstance(this);
        CuentaRepository repository = db.cuentaRepository();
        Cuenta cuenta = repository.findCuentaById(position);

        MovimientoRepository repositoryM = db.movimientoRepository();
        List<Movimiento> movimientos = repositoryM.getMovimientosByCuentaId(position);


        TextView tvNombre = findViewById(R.id.tvNombreCuenta);
        TextView tvSaldoFinal = findViewById(R.id.tvSaldoFinal);
        Button bttnRegistrar = findViewById(R.id.DbtnRegistrar);
        Button bttnVerM = findViewById(R.id.DbtnVerMovimiento);
        Button bttnSincro = findViewById(R.id.DbtnSincro);

        tvNombre.setText(cuenta.nombre);

        if(movimientos == null) tvSaldoFinal.setText("S/. 0");
        else {
            for (int i = 0; i< movimientos.size(); i++){
                String aux = movimientos.get(i).tipo.toString();
                if(aux.equals("Ingreso")) saldoF += movimientos.get(i).monto;
                if(aux.equals("Gasto")) saldoF -= movimientos.get(i).monto;
            }
            tvSaldoFinal.setText("S/. " + saldoF);
        }

        bttnSincro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cuenta.isSynced()) {
                    CuentaService service = mRetrofit.create(CuentaService.class);
                    Cuenta cuenta = new Cuenta();
                    cuenta.setNombre(tvNombre.getText().toString());
                    cuenta.setSynced(true);

                    Call<Cuenta> call = service.create(cuenta);

                    call.enqueue(new Callback<Cuenta>() {
                        @Override
                        public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                            Log.i("MAIN_APP",  String.valueOf(response.code()));

                            Intent intent =  new Intent(DetallesCuentaActivity.this, ListaCuentasActivity.class);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onFailure(Call<Cuenta> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
}