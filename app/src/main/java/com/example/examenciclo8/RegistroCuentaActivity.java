package com.example.examenciclo8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.examenciclo8.BD.AppDatabase;
import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.Repositories.CuentaRepository;
import com.example.examenciclo8.Service.CuentaService;
import com.example.examenciclo8.Utilities.RetrofitU;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistroCuentaActivity extends AppCompatActivity {

    Retrofit mRetrofit;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuenta);




        Button btnRegistrar = findViewById(R.id.bttnRegistrar);
        Button btnVolver = findViewById(R.id.bttnVolver);
        EditText etNombre = findViewById(R.id.etNombre);

        mRetrofit = RetrofitU.build();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(RegistroCuentaActivity.this, ListaCuentasActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // llamar a retrofit

                if(!isNetworkConnected()) {
                    AppDatabase database = AppDatabase.getInstance(context);
                    CuentaRepository cuentaRepository = database.cuentaRepository();

                    Cuenta cuenta = new Cuenta();
                    cuenta.setNombre(etNombre.getText().toString());
                    cuenta.setSynced(false);

                    cuentaRepository.create(cuenta);
                    Log.i("MAIN_APP: DB", new Gson().toJson(cuenta));
                    Intent intent =  new Intent(RegistroCuentaActivity.this, ListaCuentasActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    CuentaService service = mRetrofit.create(CuentaService.class);
                    Cuenta cuenta = new Cuenta();
                    cuenta.setNombre(etNombre.getText().toString());
                    cuenta.setSynced(true);

                    Call<Cuenta> call = service.create(cuenta);

                    call.enqueue(new Callback<Cuenta>() {
                        @Override
                        public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                            Log.i("MAIN_APP",  String.valueOf(response.code()));

                            Intent intent =  new Intent(RegistroCuentaActivity.this, ListaCuentasActivity.class);
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}