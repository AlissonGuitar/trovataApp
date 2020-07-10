package com.example.trovataapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.trovataapp.Adapter.ProdutoRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.Model.Sessao;

import java.util.ArrayList;
import java.util.List;

public class ActivityCadastrarProduto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Produto produtoEditar = null;
    private Spinner spinnerEmpresa, spinnerGrupoProduto;
    private EditText descricaoProduto, apelidoProduto, situacao, pesoLiquido, classificacaoFiscal, codigoBarras, colecao;
    private Banco banco;
    private Sessao sessao;
    private Button btnCadastrar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);
        sessao = new Sessao(this);


        Intent intent = getIntent();
        if (intent.hasExtra("produto")) {
            produtoEditar = (Produto) intent.getSerializableExtra("produto");


            spinnerEmpresa = findViewById(R.id.spinner_empresa);
            descricaoProduto = findViewById(R.id.descricaoProduto);
            apelidoProduto = findViewById(R.id.apelidoProduto);
            spinnerGrupoProduto = findViewById(R.id.spinner_grupo_produto);
            situacao = findViewById(R.id.situacao);
            pesoLiquido = findViewById(R.id.pesoLiquido);
            classificacaoFiscal = findViewById(R.id.classificacaoFiscal);
            codigoBarras = findViewById(R.id.codigoBarras);
            colecao = findViewById(R.id.colecao);
            btnCadastrar = findViewById(R.id.btnCadastrar);

            spinnerEmpresa.setOnItemSelectedListener(this);

            ArrayList<String> empresasSpinner = new ArrayList<>();
            empresasSpinner.add(String.valueOf(produtoEditar.getEmpresaProdutoId()));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    empresasSpinner);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerEmpresa.setAdapter(adapter);


            descricaoProduto.setText(produtoEditar.getDescricaoProduto());
            apelidoProduto.setText(produtoEditar.getApelidoProduto());
            situacao.setText(produtoEditar.getSituacao());
            pesoLiquido.setText(produtoEditar.getPesoLiquido());
            classificacaoFiscal.setText(produtoEditar.getClassificacaoFiscal());
            codigoBarras.setText(produtoEditar.getCodigoBarras());
            colecao.setText(produtoEditar.getColecao());

            salvarProduto();


        }
    }

    private void salvarProduto() {

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empresa = spinnerEmpresa.getSelectedItem().toString();
                String descricaoProdutoString = descricaoProduto.getText().toString();
                String apelidoProdutoString = apelidoProduto.getText().toString();
                String situacaoString = situacao.getText().toString();
                String pesoLiquidoString = pesoLiquido.getText().toString();
                String classificacaoFiscalString = classificacaoFiscal.getText().toString();
                String codigoBarrasString = codigoBarras.getText().toString();
                String colecaoString = colecao.getText().toString();

                banco = new Banco(v.getContext());
                boolean sucesso;
                if (produtoEditar != null) {
                    Produto produtoEditado = new Produto(produtoEditar.getEmpresaProdutoId(), produtoEditar.getProdutoId(),
                            produtoEditar.getDescricaoProduto(),
                            produtoEditar.getApelidoProduto(),
                            produtoEditar.getGrupoProduto(),
                            produtoEditar.getSubgrupoProduto(),
                            produtoEditar.getSituacao(),
                            produtoEditar.getPesoLiquido(),
                            produtoEditar.getClassificacaoFiscal(),
                            produtoEditar.getCodigoBarras(),
                            produtoEditar.getColecao());
                    banco.criarProduto(produtoEditado);
                    sucesso = banco.updateProduto(produtoEditado);
                } else {
                    Produto produtoNovo = new Produto(produtoEditar.getEmpresaProdutoId(), produtoEditar.getProdutoId(),
                            produtoEditar.getDescricaoProduto(),
                            produtoEditar.getApelidoProduto(),
                            produtoEditar.getGrupoProduto(),
                            produtoEditar.getSubgrupoProduto(),
                            produtoEditar.getSituacao(),
                            produtoEditar.getPesoLiquido(),
                            produtoEditar.getClassificacaoFiscal(),
                            produtoEditar.getCodigoBarras(),
                            produtoEditar.getColecao());
                    sucesso = banco.criarProduto(produtoNovo);

                    if (sucesso) {
                        Produto produto = banco.retornarUltimaProdutoInserido();
                        ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter =
                                new ProdutoRecyclerViewAdapter(getApplicationContext(),
                                        banco.buscarProdutoEmpresa(produtoEditar.getEmpresaProdutoId()));

                        if (produtoEditar != null) {
                            produtoRecyclerViewAdapter.atualizarProduto(produto);
                        } else {
                            produtoRecyclerViewAdapter.adicionarProduto(produto);
                        }

                        descricaoProduto.setText("");
                        apelidoProduto.setText("");
                        situacao.setText("");
                        pesoLiquido.setText("");
                        classificacaoFiscal.setText("");
                        codigoBarras.setText("");
                        colecao.setText("");

                        Toast.makeText(ActivityCadastrarProduto.this, "Produto Salvo com Sucesso!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ActivityCadastrarProduto.this, "Erro ao salvar produto!", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}