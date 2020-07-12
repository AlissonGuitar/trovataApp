package com.example.trovataapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
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
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.Model.Sessao;
import com.example.trovataapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class EmpresaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private EmpresaCadastradaRecyclerViewAdapter empresaCadastradaRecyclerViewAdapter;
    private Banco banco;
    private Sessao sessao;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_empresa);

        sessao = new Sessao(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navViewEmpresa);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Empresas Cadastradas");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        carregarEmpresas();
    }


    private void carregarEmpresas() {
        banco = new Banco(this);
        List<Empresa> empresas = banco.buscarEmpresa();
        recyclerView = findViewById(R.id.recyclerViewEmpresasCadastradas);
        empresaCadastradaRecyclerViewAdapter = new EmpresaCadastradaRecyclerViewAdapter(this, empresas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empresaCadastradaRecyclerViewAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.empresas: {
                Intent intent = new Intent(getApplicationContext(), EmpresaActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.produtos: {
                Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                startActivity(intent);
                this.finish();
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
                        sessao.setIdEmpresa("");
                        Intent intent = new Intent(EmpresaActivity.this, EmpresaActivityLogin.class);
                        startActivity(intent);

                    }
                })
                .show();

    }
}