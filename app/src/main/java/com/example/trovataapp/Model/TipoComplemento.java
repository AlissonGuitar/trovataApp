package com.example.trovataapp.Model;

public class TipoComplemento {

    private int tipoComplementoEmpresaId;
    private int tipoComplementoId;
    private String descricaoTipoComplemento;

    public TipoComplemento(int tipoComplementoEmpresaId, int tipoComplementoId, String descricaoTipoComplemento) {
        this.tipoComplementoEmpresaId = tipoComplementoEmpresaId;
        this.tipoComplementoId = tipoComplementoId;
        this.descricaoTipoComplemento = descricaoTipoComplemento;
    }

    public int getTipoComplementoEmpresaId() {
        return tipoComplementoEmpresaId;
    }

    public void setTipoComplementoEmpresaId(int tipoComplementoEmpresaId) {
        this.tipoComplementoEmpresaId = tipoComplementoEmpresaId;
    }

    public int getTipoComplementoId() {
        return tipoComplementoId;
    }

    public void setTipoComplementoId(int tipoComplementoId) {
        this.tipoComplementoId = tipoComplementoId;
    }

    public String getDescricaoTipoComplemento() {
        return descricaoTipoComplemento;
    }

    public void setDescricaoTipoComplemento(String descricaoTipoComplemento) {
        this.descricaoTipoComplemento = descricaoTipoComplemento;
    }
}
