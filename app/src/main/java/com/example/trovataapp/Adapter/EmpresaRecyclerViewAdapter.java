package com.example.trovataapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.R;

import java.util.List;

public class EmpresaRecyclerViewAdapter extends RecyclerView.Adapter<EmpresaRecyclerViewAdapter.EmpresaViewHolder> {

    private Context mCtx;
    private List<Empresa> empresas;

    public EmpresaRecyclerViewAdapter(Context mCtx, List<Empresa> empresas) {
        this.mCtx = mCtx;
        this.empresas = empresas;
    }

    @Override
    public EmpresaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mCtx).inflate(R.layout.item_empresa, parent, false);
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
                    Toast.makeText(mCtx, idEmpresa, Toast.LENGTH_SHORT).show();

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

