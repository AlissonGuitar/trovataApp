package com.example.trovataapp.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sessao {

    private SharedPreferences prefs;

    public Sessao(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setIdEmpresa(String id) {
        prefs.edit().putString("id", id).commit();
    }

    public String getIdEmpresa() {
        String id = prefs.getString("id", "");
        return id;
    }

    public void setNomeEmpresaLogado(String nomeEmpresaLogado) {
        prefs.edit().putString("nomeEmpresaLogado", nomeEmpresaLogado).commit();
    }

    public String getNomeEmpresaLogado() {
        String nomeEmpresaLogado = prefs.getString("nomeEmpresaLogado", "");
        return nomeEmpresaLogado;
    }


}
