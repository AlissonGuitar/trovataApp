package com.example.trovataapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trovataapp.Adapter.EmpresaRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.R;

import java.util.List;

public class EmpresaActivity extends AppCompatActivity {

    private EmpresaRecyclerViewAdapter empresaRecyclerViewAdapter;
    private Banco banco;
    private static List<Empresa> empresas;
    private static List<Produto> produtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Empresas");
        }


        carregarEmpresas();
        inserirEmpresas();


    }

    private void carregarEmpresas() {

        banco = new Banco(this);
        empresas = banco.buscarEmpresa();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        empresaRecyclerViewAdapter = new EmpresaRecyclerViewAdapter(this, empresas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empresaRecyclerViewAdapter);

    }

    private void inserirEmpresas() {
        if (empresas.size() == 0) {
            Empresa empresa1 = new Empresa(1, "ROMA VENDAS ONLINE", "ROMA VENDAS LTDA", "RUA NELSON CALIXTO 142", "PARQUE SAO VICENTE", "16200-320", "Araçatuba",
                    "(18)3644-7333", "", "88.060.431/0001-94", "ISENTO");

            Empresa empresa2 = new Empresa(2, "MILANO VENDAS OFFLINE", "MILANO VENDAS OFFLINE LTDA", "RUA BELMONTE, 334", "VILA MARIANA", "16334-532", "Araçatuba",
                    "(19)3523-5232", "", "26.523.811/0001-60", "ISENTO");


            banco = new Banco(this);
            banco.criarEmpresa(empresa1);
            banco.criarEmpresa(empresa2);
        }
    }

    private void inserirProdutosEmpresa() {

        banco = new Banco(this);
        Produto produto = new Produto(1, 1, "'ALMOFADA VISCO PESCOÇO 28X30CM ROSA CX:", "10060305",
                "355", "0", "A", "0,224", "4", "", "1");
        banco.criarProduto(produto);

    }


}
