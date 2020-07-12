package com.example.trovataapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trovataapp.Adapter.EmpresaLoginRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityCadastrarEmpresa extends AppCompatActivity {

    private static EmpresaLoginRecyclerViewAdapter empresaRecyclerViewAdapter;
    private static List<Empresa> empresas;
    private Banco banco;
    private Empresa empresaEdicao = null;
    private EditText nomeFantasia, razaoSocial, endereco, bairro, cep, pais, cidade, telefone, fax, cnpj, ie;
    private Button btnCadastrarEmpresa;
    private boolean sucesso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_empresa);


        iniciarItens();
        carregarItensEmpresaEdicao();
        salvarEmpresa();


    }

    private void carregarItensEmpresaEdicao() {

        Intent intent = getIntent();
        if (intent.hasExtra("empresa")) {
            empresaEdicao = (Empresa) intent.getSerializableExtra("empresa");

            nomeFantasia.setText(empresaEdicao.getNomeFantasia());
            razaoSocial.setText(empresaEdicao.getRazaoSocial());
            endereco.setText(empresaEdicao.getEndereco());
            bairro.setText(empresaEdicao.getBairro());
            cep.setText(empresaEdicao.getCep());
            pais.setText(empresaEdicao.getPais());
            cidade.setText(empresaEdicao.getCidade());
            telefone.setText(empresaEdicao.getTelefone());
            fax.setText(empresaEdicao.getFax());
            cnpj.setText(empresaEdicao.getCidade());
            ie.setText(empresaEdicao.getIE());

        }


    }

    private void salvarEmpresa() {

        btnCadastrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeFantasiaString = nomeFantasia.getText().toString();
                String razaoSocialString = razaoSocial.getText().toString();
                String enderecoString = endereco.getText().toString();
                String bairroString = bairro.getText().toString();
                String cepString = cep.getText().toString();
                String paisString = pais.getText().toString();
                String cidadeString = cidade.getText().toString();
                String telefoneString = telefone.getText().toString();
                String faxString = fax.getText().toString();
                String cnpjString = cnpj.getText().toString();
                String ieString = ie.getText().toString();


                banco = new Banco(getApplicationContext());
                if (empresaEdicao != null) {

                    Empresa empresaEditada = new Empresa(empresaEdicao.getEmpresaId(),
                            nomeFantasiaString,
                            razaoSocialString,
                            enderecoString,
                            bairroString,
                            cepString,
                            paisString,
                            cidadeString,
                            telefoneString,
                            faxString,
                            cnpjString,
                            ieString);


                    sucesso = banco.updateEmpresa(empresaEditada);

                    if (sucesso) {

                        nomeFantasia.setText("");
                        razaoSocial.setText("");
                        endereco.setText("");
                        bairro.setText("");
                        cep.setText("");
                        pais.setText("");
                        cidade.setText("");
                        telefone.setText("");
                        fax.setText("");
                        cnpj.setText("");
                        ie.setText("");

                        Toast.makeText(getApplicationContext(), "Empresa Editada com Sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), EmpresaActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro ao editar a Empresa!", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Empresa empresaNova = new Empresa(0,
                            nomeFantasiaString,
                            razaoSocialString,
                            enderecoString,
                            bairroString,
                            cepString,
                            paisString,
                            cidadeString,
                            telefoneString,
                            faxString,
                            cnpjString,
                            ieString);


                    sucesso = banco.criarEmpresa(empresaNova);


                    if (sucesso) {


                        Toast.makeText(getApplicationContext(), "Empresa Salva com Sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), EmpresaActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro ao salvar a Empresa!", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });


    }

    private void iniciarItens() {

        nomeFantasia = findViewById(R.id.nomeFantasia);
        razaoSocial = findViewById(R.id.razaoSocial);
        endereco = findViewById(R.id.endereco);
        bairro = findViewById(R.id.bairro);
        cep = findViewById(R.id.cep);
        pais = findViewById(R.id.pais);
        cidade = findViewById(R.id.cidade);
        telefone = findViewById(R.id.telefone);
        fax = findViewById(R.id.fax);
        cnpj = findViewById(R.id.cnpj);
        ie = findViewById(R.id.ie);
        btnCadastrarEmpresa = findViewById(R.id.btnSalvarEmpresa);

    }


    private void apiBuscaCep(){

    }


}
