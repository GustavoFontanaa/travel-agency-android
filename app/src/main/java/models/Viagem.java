package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Viagem implements Serializable {

    @SerializedName("total_viajantes")
    private int totalViajantes;

    @SerializedName("duracao_viagem")
    private int duracaoViagem;

    @SerializedName("custo_total_viagem")
    private double custoTotalViagem;

    @SerializedName("custo_por_pessoa")
    private double custoPorPessoa;

    @SerializedName("local")
    private String local;

    @SerializedName("id_conta")
    private int idConta;

    @SerializedName("gasolina")
    private Gasolina gasolina;

    @SerializedName("lista_entretenimento")
    private ArrayList<Entretenimento> listaEntretenimento;

    public Viagem() {

    }

    public int getTotalViajantes() {
        return totalViajantes;
    }

    public void setTotalViajantes(int totalViajantes) {
        this.totalViajantes = totalViajantes;
    }

    public int getDuracaoViagem() {
        return duracaoViagem;
    }

    public void setDuracaoViagem(int duracaoViagem) {
        this.duracaoViagem = duracaoViagem;
    }

    public double getCustoTotalViagem() {
        return custoTotalViagem;
    }

    public void setCustoTotalViagem(double custoTotalViagem) {
        this.custoTotalViagem = custoTotalViagem;
    }

    public double getCustoPorPessoa() {
        return custoPorPessoa;
    }

    public void setCustoPorPessoa(double custoPorPessoa) {
        this.custoPorPessoa = custoPorPessoa;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public Gasolina getGasolina() {
        return gasolina;
    }

    public void setGasolina(Gasolina gasolina) {
        this.gasolina = gasolina;
    }

    public ArrayList<Entretenimento> getListaEntretenimento() {
        return listaEntretenimento;
    }

    public void setListaEntretenimento(ArrayList<Entretenimento> listaEntretenimento) {
        this.listaEntretenimento = listaEntretenimento;
    }
}
