package com.example.trovataapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trovataapp.Adapter.EmpresaRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;

import java.util.List;

public class EmpresaCadastradas extends AppCompatActivity {

    private EmpresaRecyclerViewAdapter empresaRecyclerViewAdapter;
    private Banco bancoEmpresa;
    private static List<Empresa> empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_cadastradas);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Empresas");
        }
        carregarEmpresas();
        inserirRegistrosPadrao();


    }

    private void carregarEmpresas() {

        bancoEmpresa = new Banco(this);
        empresas = bancoEmpresa.buscarEmpresa();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEmpresa);
        empresaRecyclerViewAdapter = new EmpresaRecyclerViewAdapter(this, empresas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empresaRecyclerViewAdapter);

    }

    private void inserirRegistrosPadrao() {
        if (empresas.size() == 0) {
            Empresa empresa1 = new Empresa(1, "ROMA VENDAS ONLINE", "ROMA VENDAS LTDA", "RUA NELSON CALIXTO 142", "PARQUE SAO VICENTE", "16200-320", "Araçatuba",
                    "(18)3644-7333", "", "88.060.431/0001-94", "ISENTO");

            Empresa empresa2 = new Empresa(2, "MILANO VENDAS OFFLINE", "MILANO VENDAS OFFLINE LTDA", "RUA BELMONTE, 334", "VILA MARIANA", "16334-532", "Araçatuba",
                    "(19)3523-5232", "", "26.523.811/0001-60", "ISENTO");


            bancoEmpresa = new Banco(this);
            bancoEmpresa.criarEmpresa(empresa1);
            bancoEmpresa.criarEmpresa(empresa2);
        }
    }


}
