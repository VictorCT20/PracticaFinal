package com.example.examenciclo8.Service;

import com.example.examenciclo8.Clases.Cuenta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CuentaService {

    @GET("Cuentas")
    Call<List<Cuenta>> getAllUser(@Query("limit") int limit, @Query("page") int page);

    @GET("Cuentas/{id}")
    Call<Cuenta> findUser(@Path("id") int id);

    @POST("Cuentas")
    Call<Cuenta> create(@Body Cuenta cuenta);

    @PUT("Cuentas/{id}")
    Call<Cuenta> update(@Path("id") int id, @Body Cuenta cuenta);

    @DELETE("Cuentas/{id}")
    Call<Void> delete(@Path("id") int id);

    @POST("Cuentas/upload")
    Call<Void> uploadCuentas(@Body List<Cuenta> cuentas);

}
