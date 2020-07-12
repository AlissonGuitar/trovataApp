package com.example.trovataapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trovataapp.Activity.ActivityCadastrarEmpresa;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.R;

import java.util.List;

public class EmpresaCadastradaRecyclerViewAdapter extends RecyclerView.Adapter<EmpresaCadastradaRecyclerViewAdapter.EmpresaCadastradaViewHolder> {

    private Context context;
    private List<Empresa> empresas;

    public EmpresaCadastradaRecyclerViewAdapter(Context context, List<Empresa> empresas) {
        this.context = context;
        this.empresas = empresas;
    }

    @Override
    public EmpresaCadastradaRecyclerViewAdapter.EmpresaCadastradaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_empresa_cadastrada, parent, false);
        EmpresaCadastradaViewHolder empresaCadastradaViewHolder = new EmpresaCadastradaViewHolder(v);
        return empresaCadastradaViewHolder;
    }


    @Override
    public void onBindViewHolder(final EmpresaCadastradaViewHolder holder, final int position) {
        final Empresa empresa = empresas.get(position);

        holder.razaoSocialEmpresaLista.setText(empresas.get(position).getRazaoSocial());

        holder.btnEditEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityCadastrarEmpresa.class);
                intent.putExtra("empresa", empresa);
                context.startActivity(intent);

            }
        });

        holder.btnDeleteEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmaExclusaoEmpresa(v, empresa);

            }
        });
    }


    @Override
    public int getItemCount() {
        return empresas.size();
    }

    static class EmpresaCadastradaViewHolder extends RecyclerView.ViewHolder {

        private final TextView razaoSocialEmpresaLista;
        private final ImageButton btnEditEmpresa, btnDeleteEmpresa;

        EmpresaCadastradaViewHolder(View itemView) {
            super(itemView);

            razaoSocialEmpresaLista = itemView.findViewById(R.id.razaoSocialEmpresaLista);
            btnEditEmpresa = itemView.findViewById(R.id.btnEditEmpresa);
            btnDeleteEmpresa = itemView.findViewById(R.id.btnDeleteEmpresa);

        }


    }

    private void dialogConfirmaExclusaoEmpresa(View v, Empresa empresa) {
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmação")
                .setMessage("Tem certeza que deseja excluir esta empresa?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Banco banco = new Banco(view.getContext());
                        boolean sucesso = banco.deletarEmpresa(empresa.getEmpresaId());
                        if (sucesso) {
                            removerEmpresaLista(empresa);
                            Toast.makeText(context, "Empresa excluida!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, " Erro ao excluir empresa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void removerEmpresaLista(Empresa empresa) {
    }


}
