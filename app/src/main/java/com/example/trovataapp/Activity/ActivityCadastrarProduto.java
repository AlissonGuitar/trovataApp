package com.example.trovataapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Model.GrupoProduto;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.Model.Sessao;
import com.example.trovataapp.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ActivityCadastrarProduto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Produto produtoEdicao = null;
    private Spinner spinnerEmpresa, spinnerGrupoProduto;
    private EditText descricaoProduto, apelidoProduto, subGrupoProduto, situacao, pesoLiquido, classificacaoFiscal, codigoBarras, colecao;
    private Banco banco;
    private Sessao sessao;
    private static Button btnCadastrarProduto;
    private static List<Empresa> empresas;
    private static List<GrupoProduto> grupoProdutos;
    private static int idGrupoProduto;
    private boolean sucesso = false;
    private Button btnImagemProduto;
    private final int REQUEST_CODE_GALLERY = 999;
    private ImageView imagemProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);
        sessao = new Sessao(this);


        iniciarItens();
        carregarspinnersNovoProduto();
        salvarProduto();
        carregarItensProdutoEdicao();


    }

    private void carregarItensProdutoEdicao() {

        Intent intent = getIntent();
        if (intent.hasExtra("produto")) {
            produtoEdicao = (Produto) intent.getSerializableExtra("produto");


            if (produtoEdicao.getImagemProduto() == null) {
                imagemProduto.setImageResource(R.mipmap.ic_launcher);
            } else {
                byte[] imagemProdutoByte = produtoEdicao.getImagemProduto();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagemProdutoByte, 0, imagemProdutoByte.length);
                imagemProduto.setImageBitmap(bitmap);
            }

            descricaoProduto.setText(produtoEdicao.getDescricaoProduto());
            apelidoProduto.setText(produtoEdicao.getApelidoProduto());
            situacao.setText(produtoEdicao.getSituacao());
            pesoLiquido.setText(produtoEdicao.getPesoLiquido());
            classificacaoFiscal.setText(produtoEdicao.getClassificacaoFiscal());
            codigoBarras.setText(produtoEdicao.getCodigoBarras());
            colecao.setText(produtoEdicao.getColecao());
            carregarspinnersProdutoEdicao();


        }
    }

    private void iniciarItens() {

        imagemProduto = findViewById(R.id.imagemProduto);
        spinnerEmpresa = findViewById(R.id.spinner_empresa);
        spinnerGrupoProduto = findViewById(R.id.spinner_grupo_produto);
        descricaoProduto = findViewById(R.id.descricaoProdutoLista);
        apelidoProduto = findViewById(R.id.apelidoProduto);
        subGrupoProduto = findViewById(R.id.subGrupoProduto);
        situacao = findViewById(R.id.situacao);
        pesoLiquido = findViewById(R.id.pesoLiquido);
        classificacaoFiscal = findViewById(R.id.classificacaoFiscal);
        codigoBarras = findViewById(R.id.codigoBarras);
        colecao = findViewById(R.id.colecao);
        btnCadastrarProduto = findViewById(R.id.btnCadastrar);
        btnImagemProduto = findViewById(R.id.btnImagemProduto);


        btnImagemProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ActivityCadastrarProduto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
    }

    private void carregarspinnersProdutoEdicao() {

        ArrayList<String> grupoProdutosSpinner = new ArrayList<>();
        banco = new Banco(this);
        grupoProdutos = banco.buscarGrupoProdutoEmpresa(Integer.parseInt(sessao.getIdEmpresa()));
        for (GrupoProduto grupoProduto : grupoProdutos) {
            if (grupoProduto.getGrupoProdutoId() == produtoEdicao.getGrupoProdutoId()) {
                grupoProdutosSpinner.add(grupoProduto.getDescricaoGrupoProduto());

            }

        }

        ArrayAdapter<String> adapterGrupoProduto = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                grupoProdutosSpinner);

        adapterGrupoProduto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGrupoProduto.setAdapter(adapterGrupoProduto);


    }

    private void carregarspinnersNovoProduto() {

        spinnerEmpresa.setOnItemSelectedListener(this);
        spinnerGrupoProduto.setOnItemSelectedListener(this);

        ArrayList<String> grupoProdutosSpinner = new ArrayList<>();
        banco = new Banco(this);
        grupoProdutos = banco.buscarGrupoProdutoEmpresa(Integer.parseInt(sessao.getIdEmpresa()));
        for (GrupoProduto grupoProduto : grupoProdutos) {
            grupoProdutosSpinner.add(grupoProduto.getDescricaoGrupoProduto());

        }

        ArrayAdapter<String> adapterGrupoProduto = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                grupoProdutosSpinner);

        adapterGrupoProduto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGrupoProduto.setAdapter(adapterGrupoProduto);


        ArrayList<String> empresasSpinner = new ArrayList<>();
        banco = new Banco(this);
        empresas = banco.buscarEmpresa();
        for (Empresa empresa : empresas) {
            if (empresa.getEmpresaId() == Integer.parseInt(sessao.getIdEmpresa())) {
                empresasSpinner.add(empresa.getRazaoSocial());
            }
        }

        ArrayAdapter<String> adapterEmpresa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                empresasSpinner);

        adapterEmpresa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEmpresa.setAdapter(adapterEmpresa);
    }

    private void salvarProduto() {


        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descricaoProdutoString = descricaoProduto.getText().toString();
                String apelidoProdutoString = apelidoProduto.getText().toString();
                String subGrupoProdutoString = subGrupoProduto.getText().toString();
                String situacaoString = situacao.getText().toString();
                String pesoLiquidoString = pesoLiquido.getText().toString();
                String classificacaoFiscalString = classificacaoFiscal.getText().toString();
                String codigoBarrasString = codigoBarras.getText().toString();
                String colecaoString = colecao.getText().toString();

                banco = new Banco(getApplicationContext());
                if (produtoEdicao != null) {
                    Produto produtoEditado = new Produto(Integer.parseInt(sessao.getIdEmpresa()), produtoEdicao.getProdutoId(),
                            imageViewToByte(imagemProduto),
                            descricaoProdutoString,
                            apelidoProdutoString,
                            idGrupoProduto,
                            subGrupoProdutoString,
                            situacaoString,
                            pesoLiquidoString,
                            classificacaoFiscalString,
                            codigoBarrasString,
                            colecaoString);
                    sucesso = banco.updateProduto(produtoEditado);

                    if (sucesso) {

                        descricaoProduto.setText("");
                        apelidoProduto.setText("");
                        situacao.setText("");
                        pesoLiquido.setText("");
                        classificacaoFiscal.setText("");
                        codigoBarras.setText("");
                        colecao.setText("");
                        imagemProduto.setImageResource(R.mipmap.ic_launcher);
                        Toast.makeText(getApplicationContext(), "Produto Editado com Sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro ao editar o Produto!", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Produto produtoNovo = new Produto(Integer.parseInt(sessao.getIdEmpresa()), 0,
                            imageViewToByte(imagemProduto),
                            descricaoProdutoString,
                            apelidoProdutoString,
                            idGrupoProduto,
                            subGrupoProdutoString,
                            situacaoString,
                            pesoLiquidoString,
                            classificacaoFiscalString,
                            codigoBarrasString,
                            colecaoString);

                    sucesso = banco.criarProduto(produtoNovo);

                    if (sucesso) {

                        descricaoProduto.setText("");
                        apelidoProduto.setText("");
                        situacao.setText("");
                        pesoLiquido.setText("");
                        classificacaoFiscal.setText("");
                        codigoBarras.setText("");
                        colecao.setText("");
                        imagemProduto.setImageResource(R.mipmap.ic_launcher);


                        Toast.makeText(getApplicationContext(), "Produto Salvo com Sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro ao salvar o Produto!", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        spinnerGrupoProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idGrupoProduto = grupoProdutos.get(position).getGrupoProdutoId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int getIndex(Spinner spinner, String itemSelecionadoEdicao) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(itemSelecionadoEdicao)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "Sem permissão para acessar esse endereco de imagem!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imagemProduto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}