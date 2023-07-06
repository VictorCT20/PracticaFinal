package com.example.examenciclo8.Service;

import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.Clases.Movimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovimientoService {
    @GET("Movimiento")
    Call<List<Movimiento>> getAllUser(@Query("limit") int limit, @Query("page") int page);

    @GET("Movimiento/{id}")
    Call<Cuenta> findUser(@Path("id") int id);

    @POST("Movimiento")
    Call<Movimiento> create(@Body Movimiento movimiento);

    @PUT("Movimiento/{id}")
    Call<Movimiento> update(@Path("id") int id, @Body Movimiento movimiento);

    @DELETE("Movimiento/{id}")
    Call<Void> delete(@Path("id") int id);

    @POST("Movimiento/upload")
    Call<Void> uploadMovimientos(@Body List<Movimiento> movimientos);
}
