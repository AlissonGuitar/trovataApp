package com.example.trovataapp.Activity;

import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.trovataapp.Adapter.ProdutoRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Filtro.FiltroProdutos;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.Model.Sessao;
import com.example.trovataapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {

    private Banco banco;
    private List<Produto> produtos;
    public static int idEmpresa;
    public static String nomeEmpresa;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;
    private Sessao sessao;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_produtos);

        sessao = new Sessao(this);
        sessao.setIdEmpresa(String.valueOf(idEmpresa));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(nomeEmpresa);
        }
        carregarProdutosEmpresa();

        iniciarSpinnerOrdenacao();


    }

    private void carregarProdutosEmpresa() {
        banco = new Banco(this);
        produtos = new ArrayList<>();
        produtos = banco.buscarProdutoEmpresa(idEmpresa);
        recyclerView = findViewById(R.id.recyclerViewProdutos);
        produtoRecyclerViewAdapter = new ProdutoRecyclerViewAdapter(this, produtos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(produtoRecyclerViewAdapter);
    }

    private void iniciarSpinnerOrdenacao() {
        spinner = findViewById(R.id.spinnerTiposOrdenacao);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipo_ordenacao_produto,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == 0) {
                    ordenarPorCodigo();
                } else {
                    ordenarPorDescricao();
                }
                produtoRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ordenarPorDescricao() {
        Collections.sort(produtos, (p1, p2) -> p1.getDescricaoProduto().compareTo(p2.getDescricaoProduto()));
    }

    private void ordenarPorCodigo() {
        Collections.sort(produtos, (p1, p2) -> {
            if (p1.getProdutoId() > p2.getProdutoId()) {
                return 1;
            } else if (p1.getProdutoId() < p2.getProdutoId()) {
                return -1;
            } else {
                return 0;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pesquisa_produto, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                produtoRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                produtoRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.filtrarPor) {
            dialogFiltrarPor();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogFiltrarPor() {

        String[] options = {"Grupo Produto", "Tipo Complemento Grupo Produto", "Descrição", "Apelido", "Código"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Filtrar por:");
        builder.setIcon(R.drawable.ic_action_sort);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 1:
                        FiltroProdutos.tipoFiltroProduto = "Grupo Produto";
                        break;
                    case 2:
                        FiltroProdutos.tipoFiltroProduto = "Tipo Complemento Grupo Produto";
                        break;
                    case 3:
                        FiltroProdutos.tipoFiltroProduto = "Descrição";
                        break;
                    case 4:
                        FiltroProdutos.tipoFiltroProduto = "Apelido";
                        break;
                    case 5:
                        FiltroProdutos.tipoFiltroProduto = "Código";
                        break;

                }


            }
        });

        builder.create().show();
    }
}