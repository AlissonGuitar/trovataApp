package com.example.trovataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trovataapp.Adapter.EmpresaRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;

import java.util.ArrayList;
import java.util.List;

public class CadastrarEmpresa extends AppCompatActivity {

    private static EmpresaRecyclerViewAdapter empresaRecyclerViewAdapter;
    private static List<Empresa> empresas;
    private Empresa empresa;
    private Banco bancoEmpresa;
    private EditText nomeFantasia, razaoSocial, endereco, bairro, cep, cidade, telefone, fax, cnpj, ie;
    private Button btnSalvarEmpresa, btnListarEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empresas = new ArrayList<>();

        nomeFantasia = findViewById(R.id.nomeFantasia);
        razaoSocial = findViewById(R.id.razaoSocial);
        endereco = findViewById(R.id.endereco);
        bairro = findViewById(R.id.bairro);
        cep = findViewById(R.id.cep);
        cidade = findViewById(R.id.cidade);
        telefone = findViewById(R.id.telefone);
        fax = findViewById(R.id.fax);
        cnpj = findViewById(R.id.cnpj);
        ie = findViewById(R.id.ie);
        btnSalvarEmpresa = findViewById(R.id.btnSalvarEmpresa);
        btnListarEmpresas = findViewById(R.id.btnListarEmpresas);

        btnSalvarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarEmpresas();
            }
        });

        btnListarEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarEmpresasCadastradas();
            }
        });


    }

    private void mostrarEmpresasCadastradas() {
        Intent intent = new Intent(this, EmpresaCadastradas.class);
        startActivity(intent);
    }

    private void carregarEmpresas() {

        empresa = new Empresa(0,
                nomeFantasia.getText().toString(),
                razaoSocial.getText().toString(),
                endereco.getText().toString(),
                bairro.getText().toString(),
                cep.getText().toString(),
                cidade.getText().toString(),
                telefone.getText().toString(),
                fax.getText().toString(),
                cnpj.getText().toString(),
                ie.getText().toString());


        bancoEmpresa = new Banco(this);

        bancoEmpresa.criarEmpresa(empresa);
        Toast.makeText(this, "empresa inserida com sucesso", Toast.LENGTH_SHORT).show();
        Empresa empresa = bancoEmpresa.retornarUltimaEmpresaInserida();
        empresaRecyclerViewAdapter = new EmpresaRecyclerViewAdapter(this, empresas);
        empresaRecyclerViewAdapter.adicionarEmpresa(empresa);


    }

}
