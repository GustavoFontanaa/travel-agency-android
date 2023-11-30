package com.example.travel_agency_android.api;

import java.util.ArrayList;

import models.Resposta;
import models.Viagem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ViagemEndPoint {

    @POST("api/cadastro/viagem")
    Call<Resposta> postViagem(@Body Viagem viagem);

    @GET("api/listar/viagem/conta")
    Call<ArrayList<Viagem>> getViagem(@Query("contaId") int contaId);

}
