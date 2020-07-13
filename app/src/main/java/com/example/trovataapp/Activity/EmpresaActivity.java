package com.example.trovataapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trovataapp.Adapter.EmpresaCadastradaRecyclerViewAdapter;
import com.example.trovataapp.Adapter.EmpresaLoginRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class EmpresaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private EmpresaCadastradaRecyclerViewAdapter empresaCadastradaRecyclerViewAdapter;
    private Banco banco;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton btnCadastrarNovaEmpresa;
    private TextView nomeEmpresaLogadaNavHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_empresa);


        iniciarItens();
        carregarEmpresas();

    }

    private void iniciarItens() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navViewEmpresa);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.empresas).setVisible(false);
        btnCadastrarNovaEmpresa = findViewById(R.id.btnCadastrarNovaEmpresa);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Empresas Cadastradas");

        View headerView = navigationView.getHeaderView(0);
        nomeEmpresaLogadaNavHeader = headerView.findViewById(R.id.nomeEmpresaLogadaNavHeader);
        nomeEmpresaLogadaNavHeader.setText(EmpresaLoginRecyclerViewAdapter.nomeEmpresa);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        btnCadastrarNovaEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpresaActivity.this, ActivityCadastrarEmpresa.class);
                startActivity(intent);
            }
        });

    }


    private void carregarEmpresas() {
        banco = new Banco(this);
        List<Empresa> empresas = banco.buscarEmpresa();
        recyclerView = findViewById(R.id.recyclerViewEmpresasCadastradas);
        empresaCadastradaRecyclerViewAdapter = new EmpresaCadastradaRecyclerViewAdapter(this, empresas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empresaCadastradaRecyclerViewAdapter);
        empresaCadastradaRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.produtos: {
                Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
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
            this.finish();
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
                        Intent intent = new Intent(EmpresaActivity.this, EmpresaActivityLogin.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .show();

    }
}