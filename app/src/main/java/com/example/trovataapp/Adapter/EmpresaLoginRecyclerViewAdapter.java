package com.example.trovataapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Activity.ProdutoActivity;
import com.example.trovataapp.R;

import java.util.List;

public class EmpresaLoginRecyclerViewAdapter extends RecyclerView.Adapter<EmpresaLoginRecyclerViewAdapter.EmpresaViewHolder> {

    private Context context;
    private List<Empresa> empresas;

    public EmpresaLoginRecyclerViewAdapter(Context context, List<Empresa> empresas) {
        this.context = context;
        this.empresas = empresas;
    }

    @Override
    public EmpresaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_empresa_login, parent, false);
        EmpresaViewHolder empresaViewHolder = new EmpresaViewHolder(v);
        return empresaViewHolder;
    }


    @Override
    public void onBindViewHolder(final EmpresaViewHolder holder, final int position) {

        holder.razaoSocialEmpresa.setText(empresas.get(position).getRazaoSocial());
        holder.cidadeEmpresa.setText(empresas.get(position).getCidade());

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String idEmpresa = String.valueOf(empresas.get(position).getEmpresaId());
                    ProdutoActivity.idEmpresa = Integer.parseInt(idEmpresa);
                    ProdutoActivity.nomeEmpresa = empresas.get(position).getRazaoSocial();
                    Intent intent = new Intent(context, ProdutoActivity.class);
                    context.startActivity(intent);

                }
                return true;
            }
        });


    }


    @Override
    public int getItemCount() {
        return empresas.size();
    }

    static class EmpresaViewHolder extends RecyclerView.ViewHolder {

        private final TextView razaoSocialEmpresa;
        private final TextView cidadeEmpresa;

        EmpresaViewHolder(View itemView) {
            super(itemView);

            razaoSocialEmpresa = itemView.findViewById(R.id.razaoSocialEmpresa);
            cidadeEmpresa = itemView.findViewById(R.id.cidadeEmpresa);
        }


    }

    public void adicionarEmpresa(Empresa empresa) {
        empresas.add(empresa);
        notifyItemInserted(getItemCount());
    }
}

