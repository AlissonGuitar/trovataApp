package com.example.trovataapp.Activity;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trovataapp.Adapter.EmpresaLoginRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.*;
import com.example.trovataapp.R;

import java.util.ArrayList;
import java.util.List;

public class EmpresaActivityLogin extends AppCompatActivity {

    private EmpresaLoginRecyclerViewAdapter empresaRecyclerViewAdapter;
    private Banco banco;
    private static List<Empresa> empresas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_empresa_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Empresas");
        }


        inserirEmpresas();



    }

    private void carregarEmpresas() {
        empresas = banco.buscarEmpresa();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        empresaRecyclerViewAdapter = new EmpresaLoginRecyclerViewAdapter(this, empresas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empresaRecyclerViewAdapter);


    }

    public void inserirEmpresas() {
        banco = new Banco(this);
        empresas = banco.buscarEmpresa();
        if (empresas.size() == 0) {
            Empresa empresa1 = new Empresa(1,
                    "ROMA VENDAS ONLINE",
                    "ROMA VENDAS LTDA",
                    "RUA NELSON CALIXTO 142",
                    "PARQUE SAO VICENTE",
                    "16200-320",
                    "",
                    "Araçatuba",
                    "(18)3644-7333",
                    "",
                    "88.060.431/0001-94",
                    "ISENTO");

            Empresa empresa2 = new Empresa(2,
                    "MILANO VENDAS OFFLINE",
                    "MILANO VENDAS OFFLINE LTDA",
                    "RUA BELMONTE, 334",
                    "VILA MARIANA",
                    "16334-532",
                    "",
                    "Araçatuba",
                    "(19)3523-5232",
                    "",
                    "26.523.811/0001-60",
                    "ISENTO");


            banco = new Banco(this);
            banco.criarEmpresa(empresa1);
            banco.criarEmpresa(empresa2);
            inserirProdutos();
            inserirGrupoProduto();
            inserirTipoComplemento();

            List<Empresa> empresasAtualizada = banco.buscarEmpresa();
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            empresaRecyclerViewAdapter = new EmpresaLoginRecyclerViewAdapter(this, empresasAtualizada);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(empresaRecyclerViewAdapter);


        }
        else{
            carregarEmpresas();
        }
    }

    private void inserirProdutos() {


        banco = new Banco(this);


        Produto produto1 = new Produto(1, 1, null, "'ALMOFADA VISCO PESCOÇO 28X30CM ROSA CX:", "10060305",
                4, "0", "A", "0,224", "4", "", "1");


        banco.criarProduto(produto1);


    }

    private void inserirGrupoProduto() {


        banco = new Banco(this);


        GrupoProduto grupoProduto1 = new GrupoProduto(1, 1, "TECIDO", 0, "1");
        GrupoProduto grupoProduto2 = new GrupoProduto(1, 2, "EMBALAGENS", 0, "4");
        GrupoProduto grupoProduto3 = new GrupoProduto(1, 3, "FRONHA", 0, "1");
        GrupoProduto grupoProduto4 = new GrupoProduto(1, 4, "ALMOFADA", 0, "3");
        GrupoProduto grupoProduto5 = new GrupoProduto(1, 5, "DECORAÇAO", 0, "4");

        banco.criarGrupoProduto(grupoProduto1);
        banco.criarGrupoProduto(grupoProduto2);
        banco.criarGrupoProduto(grupoProduto3);
        banco.criarGrupoProduto(grupoProduto4);
        banco.criarGrupoProduto(grupoProduto5);


    }

    private void inserirTipoComplemento() {


        TipoComplemento tipoComplemento1 = new TipoComplemento(1, 1, "COR");
        TipoComplemento tipoComplemento2 = new TipoComplemento(1, 2, "TAMANHO");
        TipoComplemento tipoComplemento3 = new TipoComplemento(1, 3, "ESTAMPA");
        TipoComplemento tipoComplemento4 = new TipoComplemento(1, 4, "MATERIAL");
        TipoComplemento tipoComplemento5 = new TipoComplemento(2, 1, "COR");
        TipoComplemento tipoComplemento6 = new TipoComplemento(2, 2, "TAMANHO");
        TipoComplemento tipoComplemento7 = new TipoComplemento(2, 3, "ESTAMPA");
        TipoComplemento tipoComplemento8 = new TipoComplemento(2, 4, "MATERIAL");

        banco.criarTipoComplemento(tipoComplemento1);
        banco.criarTipoComplemento(tipoComplemento2);
        banco.criarTipoComplemento(tipoComplemento3);
        banco.criarTipoComplemento(tipoComplemento4);
        banco.criarTipoComplemento(tipoComplemento5);
        banco.criarTipoComplemento(tipoComplemento6);
        banco.criarTipoComplemento(tipoComplemento7);
        banco.criarTipoComplemento(tipoComplemento8);


    }


}
