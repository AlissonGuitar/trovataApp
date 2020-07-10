package com.example.trovataapp.Filtro;

import android.widget.Filter;
import com.example.trovataapp.Adapter.ProdutoRecyclerViewAdapter;
import com.example.trovataapp.Model.Produto;

import java.util.ArrayList;

public class FiltroProdutos extends Filter {

    ArrayList<Produto> filtroProdutos;
    ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;
    public static String tipoFiltroProduto = "";

    public FiltroProdutos(ArrayList<Produto> filtro, ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter) {
        this.filtroProdutos = filtro;
        this.produtoRecyclerViewAdapter = produtoRecyclerViewAdapter;
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
                        if (produto.getGrupoProduto().toUpperCase().contains(constraint)) {
                            filtroModelo.add(produto);
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
