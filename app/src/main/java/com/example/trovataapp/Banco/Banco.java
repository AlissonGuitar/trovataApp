package com.example.trovataapp.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Model.Produto;

import java.util.ArrayList;

import java.util.List;

public class Banco extends SQLiteOpenHelper {

    private SQLiteDatabase db = this.getWritableDatabase();


    // Database Version
    private static final int DATABASE_VERSION = 30;

    // Database Name
    private static final String DATABASE_NAME = "trovata.db";

    // Tabelas
    private static final String TABELA_EMPRESA = "empresa";
    private static final String TABELA_PRODUTO = "produto";
    private static final String TABELA_GRUPO_PRODUTO = "grupo_produto";

    // colunas tabela Empresa

    private static final String EMPRESA_ID = "empresa_id";
    private static final String NOME_FANTASIA = "nome_fantasia";
    private static final String RAZAO_SOCIAL = "razao_social";
    private static final String ENDERECO = "endereco";
    private static final String BAIRRO = "bairro";
    private static final String CEP = "cep";
    private static final String CIDADE = "cidade";
    private static final String TELEFONE = "telefone";
    private static final String FAX = "fax";
    private static final String CNPJ = "cnpj";
    private static final String IE = "ie";

    //colunas tabela produto

    private static final String EMPRESA_PRODUTO_ID = "empresa_produto_id";
    private static final String PRODUTO_ID = "produto_id";
    private static final String DESCRICAO_PRODUTO = "descricao_produto";
    private static final String APELIDO_PRODUTO = "apelido_produto";
    private static final String GRUPO_PRODUTO_ID_PRODUTO = "grupo_produto_id_produto";
    private static final String SUBGRUPO_PRODUTO = "subgrupo_produto";
    private static final String SITUACAO = "situacao";
    private static final String PESO_LIQUIDO = "peso_liquido";
    private static final String CLASSIFICACAO_FISCAL = "classificacao_fiscal";
    private static final String CODIGO_BARRAS = "codigo_barras";
    private static final String COLECAO = "colecao";

    //colunas tabela grupoProduto

    private static final String EMPRESA_GRUPO_PRODUTO_ID = "emprsa_grupo_produto_id";
    private static final String GRUPO_PRODUTO_ID = "grupo_produto_id";
    private static final String DESCRICAO_GRUPO_PRODUTO = "descricao_grupo_produto";
    private static final String PERC_DESCONTO = "perc_desconto";
    private static final String TIPO_COMPLEMENTO = "tipo_complemento";


    // tabela empresa
    private static final String CREATE_TABLE_EMPRESA = "CREATE TABLE " + TABELA_EMPRESA + " ("
            + EMPRESA_ID + " INTEGER PRIMARY KEY autoincrement,"
            + NOME_FANTASIA + " TEXT,"
            + RAZAO_SOCIAL + " TEXT,"
            + ENDERECO + " TEXT,"
            + BAIRRO + " TEXT,"
            + CEP + " TEXT,"
            + CIDADE + " TEXT,"
            + TELEFONE + " TEXT,"
            + FAX + " TEXT,"
            + CNPJ + " TEXT,"
            + IE + " TEXT" + ")";

    //tabela produto

    private static final String CREATE_TABLE_PRODUTO = "CREATE TABLE " + TABELA_PRODUTO + "("
            + EMPRESA_PRODUTO_ID + " INTEGER,"
            + PRODUTO_ID + " INTEGER PRIMARY KEY autoincrement,"
            + DESCRICAO_PRODUTO + " TEXT,"
            + APELIDO_PRODUTO + " TEXT,"
            + GRUPO_PRODUTO_ID_PRODUTO + " INTEGER,"
            + SUBGRUPO_PRODUTO + " TEXT,"
            + SITUACAO + " TEXT,"
            + PESO_LIQUIDO + " INTEGER,"
            + CLASSIFICACAO_FISCAL + " TEXT,"
            + CODIGO_BARRAS + " TEXT,"
            + COLECAO + " TEXT,"
            + " FOREIGN KEY (" + GRUPO_PRODUTO_ID_PRODUTO + ") REFERENCES " + TABELA_GRUPO_PRODUTO + "(" + GRUPO_PRODUTO_ID + "),"
            + " FOREIGN KEY (" + EMPRESA_PRODUTO_ID + ") REFERENCES " + TABELA_EMPRESA + "(" + EMPRESA_ID + "));";


    //tabela grupoProduto

    private static final String CREATE_TABLE_GRUPO_PRODUTO = "CREATE TABLE " + TABELA_GRUPO_PRODUTO + "("
            + EMPRESA_GRUPO_PRODUTO_ID + " INTEGER,"
            + GRUPO_PRODUTO_ID + " INTEGER PRIMARY KEY autoincrement,"
            + DESCRICAO_GRUPO_PRODUTO + " TEXT,"
            + PERC_DESCONTO + " INTEGER,"
            + TIPO_COMPLEMENTO + " TEXT,"
            + " FOREIGN KEY (" + EMPRESA_GRUPO_PRODUTO_ID + ") REFERENCES " + TABELA_EMPRESA + "(" + EMPRESA_ID + "));";


    public Banco(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMPRESA);
        db.execSQL(CREATE_TABLE_PRODUTO);
        db.execSQL(CREATE_TABLE_GRUPO_PRODUTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop tabela antiga
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_GRUPO_PRODUTO);

        // criar nova tabela
        onCreate(db);

    }

    public boolean criarProduto(Produto produto) {
        ContentValues values = new ContentValues();

        values.put(EMPRESA_PRODUTO_ID, produto.getEmpresaProdutoId());
        values.put(DESCRICAO_PRODUTO, produto.getDescricaoProduto());
        values.put(APELIDO_PRODUTO, produto.getApelidoProduto());
        values.put(GRUPO_PRODUTO_ID_PRODUTO, produto.getGrupoProduto());
        values.put(SUBGRUPO_PRODUTO, produto.getSubgrupoProduto());
        values.put(SITUACAO, produto.getSituacao());
        values.put(PESO_LIQUIDO, produto.getPesoLiquido());
        values.put(CLASSIFICACAO_FISCAL, produto.getClassificacaoFiscal());
        values.put(CODIGO_BARRAS, produto.getCodigoBarras());
        values.put(COLECAO, produto.getColecao());


        return db.insert(TABELA_PRODUTO, null, values) > 0;
    }

    public boolean updateProduto(Produto produto) {

        ContentValues values = new ContentValues();

        values.put(EMPRESA_PRODUTO_ID, produto.getEmpresaProdutoId());
        values.put(DESCRICAO_PRODUTO, produto.getDescricaoProduto());
        values.put(APELIDO_PRODUTO, produto.getApelidoProduto());
        values.put(GRUPO_PRODUTO_ID_PRODUTO, produto.getGrupoProduto());
        values.put(SUBGRUPO_PRODUTO, produto.getSubgrupoProduto());
        values.put(SITUACAO, produto.getSituacao());
        values.put(PESO_LIQUIDO, produto.getPesoLiquido());
        values.put(CLASSIFICACAO_FISCAL, produto.getClassificacaoFiscal());
        values.put(CODIGO_BARRAS, produto.getCodigoBarras());
        values.put(COLECAO, produto.getColecao());


        return db.update(TABELA_PRODUTO, values, PRODUTO_ID + " = ?", new String[]{"" + produto.getProdutoId()}) > 0;


    }

    public boolean deletarProduto(int idProduto) {
        return db.delete(TABELA_PRODUTO, PRODUTO_ID + " = " + idProduto, null) > 0;
    }

    public List<Produto> buscarProdutoEmpresa(int idEmpresa) {
        List<Produto> produtos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PRODUTO + " WHERE " + EMPRESA_PRODUTO_ID + " = " + idEmpresa, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Produto produto = new Produto(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10));


                produtos.add(produto);

            } while (cursor.moveToNext());
        }


        return produtos;
    }

    public Produto retornarUltimaProdutoInserido() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PRODUTO + " ORDER BY " + PRODUTO_ID + " DESC ", null);
        if (cursor.moveToFirst()) {

            return new Produto(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10));

        }

        return null;
    }

    public void criarEmpresa(Empresa empresa) {

        ContentValues values = new ContentValues();

        values.put(NOME_FANTASIA, empresa.getNomeFantasia());
        values.put(RAZAO_SOCIAL, empresa.getRazaoSocial());
        values.put(ENDERECO, empresa.getEndereco());
        values.put(BAIRRO, empresa.getBairro());
        values.put(CEP, empresa.getCep());
        values.put(CIDADE, empresa.getCidade());
        values.put(TELEFONE, empresa.getTelefone());
        values.put(FAX, empresa.getFax());
        values.put(CNPJ, empresa.getCNPJ());
        values.put(IE, empresa.getIE());


        db.insert(TABELA_EMPRESA, null, values);

    }

    public void updateEmpresa(Empresa empresa) {
        ContentValues values = new ContentValues();
        values.put(NOME_FANTASIA, empresa.getNomeFantasia());
        values.put(RAZAO_SOCIAL, empresa.getRazaoSocial());
        values.put(ENDERECO, empresa.getEndereco());
        values.put(BAIRRO, empresa.getBairro());
        values.put(CEP, empresa.getCep());
        values.put(CIDADE, empresa.getCidade());
        values.put(TELEFONE, empresa.getTelefone());
        values.put(FAX, empresa.getFax());
        values.put(CNPJ, empresa.getCNPJ());
        values.put(IE, empresa.getIE());


        db.update(TABELA_EMPRESA, values, EMPRESA_ID + " = ?", new String[]{"" + empresa.getEmpresaId()});
    }

    public void deletarEmpresa(Empresa empresa) {
        db.delete(TABELA_EMPRESA, EMPRESA_ID + " = " + empresa.getEmpresaId(), null);
    }


    public List<Empresa> buscarEmpresa() {
        List<Empresa> empresas = new ArrayList<>();

        //Cursor cursor = db.query(TABELA_EMPRESA, colunas, null, null, null, null, RAZAO_SOCIAL + " ASC");
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_EMPRESA, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Empresa empresa = new Empresa(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10));


                empresas.add(empresa);

            } while (cursor.moveToNext());
        }


        return empresas;
    }

    public Empresa retornarUltimaEmpresaInserida() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_EMPRESA + " ORDER BY " + EMPRESA_ID + " DESC ", null);
        if (cursor.moveToFirst()) {

            return new Empresa(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10));

        }

        return null;
    }


}
