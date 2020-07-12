package com.example.trovataapp.Model;

import java.io.Serializable;

public class Produto implements Serializable {

    private int empresaProdutoId;
    private int produtoId;
    private byte[] imagemProduto;
    private String descricaoProduto;
    private String apelidoProduto;
    private int grupoProdutoId;
    private String subgrupoProduto;
    private String situacao;
    private String pesoLiquido;
    private String classificacaoFiscal;
    private String codigoBarras;
    private String colecao;

    public int getEmpresaProdutoId() {
        return empresaProdutoId;
    }

    public void setEmpresaProdutoId(int empresaProdutoId) {
        this.empresaProdutoId = empresaProdutoId;
    }

    public byte[] getImagemProduto() {
        return imagemProduto;
    }

    public void setImagemProduto(byte[] imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getApelidoProduto() {
        return apelidoProduto;
    }

    public void setApelidoProduto(String apelidoProduto) {
        this.apelidoProduto = apelidoProduto;
    }

    public int getGrupoProdutoId() {
        return grupoProdutoId;
    }

    public void setGrupoProdutoId(int grupoProdutoId) {
        this.grupoProdutoId = grupoProdutoId;
    }

    public String getSubgrupoProduto() {
        return subgrupoProduto;
    }

    public void setSubgrupoProduto(String subgrupoProduto) {
        this.subgrupoProduto = subgrupoProduto;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getPesoLiquido() {
        return pesoLiquido;
    }

    public void setPesoLiquido(String pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    public String getClassificacaoFiscal() {
        return classificacaoFiscal;
    }

    public void setClassificacaoFiscal(String classificacaoFiscal) {
        this.classificacaoFiscal = classificacaoFiscal;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public Produto(int empresaProdutoId, int produtoId,byte[] imagemProduto, String descricaoProduto, String apelidoProduto, int grupoProdutoId, String subgrupoProduto, String situacao, String pesoLiquido, String classificacaoFiscal, String codigoBarras, String colecao) {
        this.empresaProdutoId = empresaProdutoId;
        this.produtoId = produtoId;
        this.imagemProduto = imagemProduto;
        this.descricaoProduto = descricaoProduto;
        this.apelidoProduto = apelidoProduto;
        this.grupoProdutoId = grupoProdutoId;
        this.subgrupoProduto = subgrupoProduto;
        this.situacao = situacao;
        this.pesoLiquido = pesoLiquido;
        this.classificacaoFiscal = classificacaoFiscal;
        this.codigoBarras = codigoBarras;
        this.colecao = colecao;
    }
}
