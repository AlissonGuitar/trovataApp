package com.example.trovataapp.Model;

public class GrupoProduto {

    private int empresaGrupoProdutoId;
    private int grupoProdutoId;
    private String descricaoGrupoProduto;
    private float percDesconto;
    private String tipoComplemento;

    public int getEmpresaGrupoProdutoId() {
        return empresaGrupoProdutoId;
    }

    public void setEmpresaGrupoProdutoId(int empresaGrupoProdutoId) {
        this.empresaGrupoProdutoId = empresaGrupoProdutoId;
    }

    public int getGrupoProdutoId() {
        return grupoProdutoId;
    }

    public void setGrupoProdutoId(int grupoProdutoId) {
        this.grupoProdutoId = grupoProdutoId;
    }

    public String getDescricaoGrupoProduto() {
        return descricaoGrupoProduto;
    }

    public void setDescricaoGrupoProduto(String descricaoGrupoProduto) {
        this.descricaoGrupoProduto = descricaoGrupoProduto;
    }

    public float getPercDesconto() {
        return percDesconto;
    }

    public void setPercDesconto(float percDesconto) {
        this.percDesconto = percDesconto;
    }

    public String getTipoComplemento() {
        return tipoComplemento;
    }

    public void setTipoComplemento(String tipoComplemento) {
        this.tipoComplemento = tipoComplemento;
    }


    public GrupoProduto(int empresaGrupoProdutoId, int grupoProdutoId, String descricaoGrupoProduto, float percDesconto, String tipoComplemento) {
        this.empresaGrupoProdutoId = empresaGrupoProdutoId;
        this.grupoProdutoId = grupoProdutoId;
        this.descricaoGrupoProduto = descricaoGrupoProduto;
        this.percDesconto = percDesconto;
        this.tipoComplemento = tipoComplemento;
    }
}
