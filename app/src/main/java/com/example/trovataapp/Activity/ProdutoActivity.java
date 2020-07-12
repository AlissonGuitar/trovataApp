package com.example.trovataapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.trovataapp.Adapter.EmpresaLoginRecyclerViewAdapter;
import com.example.trovataapp.Adapter.ProdutoRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Filtro.FiltroProdutos;
import com.example.trovataapp.Model.Produto;
import com.example.trovataapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Banco banco;
    private List<Produto> produtos;
    public static int idEmpresa;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;
    private FloatingActionButton btnCadastrarNovoProduto;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView nomeEmpresaLogadaNavHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_produtos);

        btnCadastrarNovoProduto = findViewById(R.id.btnCadastrarNovoProduto);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navViewProduto);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.produtos).setVisible(false);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Produtos Cadastrados");
        View headerView = navigationView.getHeaderView(0);
        nomeEmpresaLogadaNavHeader = headerView.findViewById(R.id.nomeEmpresaLogadaNavHeader);

        nomeEmpresaLogadaNavHeader.setText(EmpresaLoginRecyclerViewAdapter.nomeEmpresa);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        carregarProdutosEmpresa();
        produtoRecyclerViewAdapter.notifyDataSetChanged();

        iniciarSpinnerOrdenacao();

        btnCadastrarNovoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCadastrarProduto.class);
                startActivity(intent);
            }
        });


    }

    private void carregarProdutosEmpresa() {
        banco = new Banco(this);
        produtos = banco.buscarProdutoEmpresa(idEmpresa);
        recyclerView = findViewById(R.id.recyclerViewProdutosCadastrados);
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
                    case 0:
                        FiltroProdutos.tipoFiltroProduto = "Grupo Produto";
                        break;
                    case 1:
                        FiltroProdutos.tipoFiltroProduto = "Tipo Complemento Grupo Produto";
                        break;
                    case 2:
                        FiltroProdutos.tipoFiltroProduto = "Descrição";
                        break;
                    case 3:
                        FiltroProdutos.tipoFiltroProduto = "Apelido";
                        break;
                    case 4:
                        FiltroProdutos.tipoFiltroProduto = "Código";
                        break;


                }


            }
        });

        builder.create().show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.empresas: {
                Intent intent = new Intent(getApplicationContext(), EmpresaActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.sair: {
                dialogLogoutEmpresa();
                break;
            }


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    private void dialogLogoutEmpresa() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Deseja mesmo sair da empresa?")
                .setPositiveButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProdutoActivity.this, EmpresaActivityLogin.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .show();

    }

}
