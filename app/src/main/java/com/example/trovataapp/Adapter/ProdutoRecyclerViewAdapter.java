package com.example.trovataapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trovataapp.Activity.ActivityCadastrarProduto;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Filtro.FiltroProdutos;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRecyclerViewAdapter extends RecyclerView.Adapter<ProdutoRecyclerViewAdapter.ProdutoViewHolder> implements Filterable {

    private Context context;
    public static List<Produto> produtos, filtroProdutos;
    private FiltroProdutos filtro;


    public ProdutoRecyclerViewAdapter(Context mCtx, List<Produto> produtos) {
        this.context = mCtx;
        this.produtos = produtos;
        filtroProdutos = produtos;

    }

    @Override
    public ProdutoRecyclerViewAdapter.ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        ProdutoRecyclerViewAdapter.ProdutoViewHolder produtoViewHolder = new ProdutoRecyclerViewAdapter.ProdutoViewHolder(v);
        return produtoViewHolder;
    }


    @Override
    public void onBindViewHolder(final ProdutoRecyclerViewAdapter.ProdutoViewHolder holder, final int position) {

        final Produto produto = produtos.get(position);

        holder.descricaoProduto.setText(produtos.get(position).getDescricaoProduto());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityCadastrarProduto.class);
                intent.putExtra("produto", produto);
                context.startActivity(intent);

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmaExclusaoProduto(v, produto);

            }
        });


    }


    @Override
    public int getItemCount() {
        return produtos.size();
    }

    @Override
    public Filter getFilter() {
        if (filtro == null) {
            filtro = new FiltroProdutos((ArrayList<Produto>) filtroProdutos, this, context);
        }

        return filtro;
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {

        private final TextView descricaoProduto;
        private final ImageButton btnEdit;
        private final ImageButton btnDelete;


        ProdutoViewHolder(View itemView) {
            super(itemView);

            descricaoProduto = itemView.findViewById(R.id.descricaoProdutoLista);
            btnEdit = itemView.findViewById(R.id.btnEditProduto);
            btnDelete = itemView.findViewById(R.id.btnDeleteProduto);


        }


    }

    private void removerProduto(Produto produto) {
        int position = produtos.indexOf(produto);
        produtos.remove(position);
        notifyItemRemoved(position);
    }


    private void dialogConfirmaExclusaoProduto(View v, Produto produto) {
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmação")
                .setMessage("Tem certeza que deseja excluir este produto?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Banco banco = new Banco(view.getContext());
                        boolean sucesso = banco.deletarProduto(produto.getProdutoId());
                        if (sucesso) {
                            removerProduto(produto);
                            Toast.makeText(context, "Produto excluido!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, " Erro ao excluir produto!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }
}
