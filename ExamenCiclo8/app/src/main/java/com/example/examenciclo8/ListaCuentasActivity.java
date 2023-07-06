package com.example.examenciclo8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.examenciclo8.Adapters.CuentaAdapter;
import com.example.examenciclo8.BD.AppDatabase;
import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.Repositories.CuentaRepository;
import com.example.examenciclo8.Service.CuentaService;
import com.example.examenciclo8.Utilities.RetrofitU;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaCuentasActivity extends AppCompatActivity {

    Retrofit mRetrofit;
    RecyclerView mRvLista;
    boolean mIsLoading = false;
    int mPage = 1;
    List<Cuenta> cdata = new ArrayList<>();
    CuentaAdapter mAdapter = new CuentaAdapter(cdata, this);
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cuentas);

        mRetrofit = RetrofitU.build();
        Button btnRegistro = findViewById(R.id.bttnRegistro);
        Button btnActualizar = findViewById(R.id.bttnActualizar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvLista =  findViewById(R.id.rvListaCuentas);
        mRvLista.setLayoutManager(layoutManager);
        mRvLista.setAdapter(mAdapter);

        mRvLista.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!mIsLoading) {

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == cdata.size() - 1) {
                        mPage++;
                        loadMore(mPage);
                    }
                }

            }
        });



        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ListaCuentasActivity.this, RegistroCuentaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToWebService(1);
            }
        });

        AppDatabase db = AppDatabase.getInstance(context);
        CuentaRepository repository = db.cuentaRepository();
        List<Cuenta> users = repository.getAll();
        Log.i("MAIN_APP: DB", new Gson().toJson(users));
        mAdapter.setCuentas(users);
        mAdapter.notifyDataSetChanged();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void uploadToWebService(int nextPage) {

        AppDatabase db = AppDatabase.getInstance(context);
        db.clearAllTables();

        CuentaService service = mRetrofit.create(CuentaService.class);
        service.getAllUser(6, nextPage).enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Inserta los datos en la base de datos
                    AppDatabase db = AppDatabase.getInstance(ListaCuentasActivity.this);
                    CuentaRepository repository = db.cuentaRepository();
                    repository.insertAll(response.body());

                    // Actualiza los datos en el adaptador y notifica los cambios
                    List<Cuenta> newData = repository.getAll();
                    mAdapter.setCuentas(newData);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) {
                // Maneja el error de la llamada al servicio MockAPI
            }
        });
    }
    private void loadMore(int nextPage) {
        mIsLoading = true;

        cdata.add(null);
        mAdapter.notifyItemInserted(cdata.size() - 1);

        CuentaService service = mRetrofit.create(CuentaService.class);
        Log.i("MAIN_APP  Page:", String.valueOf(nextPage));
        service.getAllUser(6, nextPage).enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cdata.remove(cdata.size() - 1);
                    mAdapter.notifyItemRemoved(cdata.size() - 1);

                    cdata.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                    mIsLoading = false;
                }
            }
            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) { }
        });
    }
}