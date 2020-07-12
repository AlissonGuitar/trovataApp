package com.example.trovataapp.Filtro;

import android.content.Context;
import android.widget.Filter;
import com.example.trovataapp.Activity.ProdutoActivity;
import com.example.trovataapp.Adapter.ProdutoRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.GrupoProduto;
import com.example.trovataapp.Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class FiltroProdutos extends Filter {

    ArrayList<Produto> filtroProdutos;
    ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;
    public static String tipoFiltroProduto = "Descrição";
    private Context context;

    public FiltroProdutos(ArrayList<Produto> filtro, ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter, Context context) {
        this.filtroProdutos = filtro;
        this.produtoRecyclerViewAdapter = produtoRecyclerViewAdapter;
        this.context = context;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {

            constraint = constraint.toString().toUpperCase();

            ArrayList<Produto> filtroModelo = new ArrayList<>();

            switch (tipoFiltroProduto) {
                case "Grupo Produto":
                    for (Produto produto : filtroProdutos) {

                        Banco banco = new Banco(context);
                        List<GrupoProduto> grupoProdutos = banco.buscarGrupoProdutoEmpresa(ProdutoActivity.idEmpresa);
                        for (GrupoProduto grupoProduto : grupoProdutos) {
                            if (grupoProduto.getGrupoProdutoId() == produto.getGrupoProdutoId()) {

                                if (String.valueOf(grupoProduto.getDescricaoGrupoProduto()).toUpperCase().contains(constraint)) {
                                    filtroModelo.add(produto);
                                }
                            }
                        }
                    }
                    break;
                case "Descrição":
                    for (Produto produto : filtroProdutos) {
                        if (produto.getDescricaoProduto().toUpperCase().contains(constraint)) {
                            filtroModelo.add(produto);
                        }
                    }
                    break;

                case "Apelido":
                    for (Produto produto : filtroProdutos) {
                        if (produto.getApelidoProduto().toUpperCase().contains(constraint)) {
                            filtroModelo.add(produto);
                        }
                    }
                    break;

                case "Código":

                    for (Produto produto : filtroProdutos) {
                        if (String.valueOf(produto.getProdutoId()).toUpperCase().contains(constraint)) {
                            filtroModelo.add(produto);
                        }
                    }
                    break;

                default:


            }

            results.count = filtroModelo.size();
            results.values = filtroModelo;

        } else {
            results.count = filtroProdutos.size();
            results.values = filtroProdutos;
        }

        return results;

    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        produtoRecyclerViewAdapter.produtos = (ArrayList<Produto>) results.values;
        produtoRecyclerViewAdapter.notifyDataSetChanged();

    }
}
