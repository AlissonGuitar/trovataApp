package com.example.trovataapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trovataapp.Adapter.EmpresaLoginRecyclerViewAdapter;
import com.example.trovataapp.Banco.Banco;
import com.example.trovataapp.Mascara.Mask;
import com.example.trovataapp.Model.Empresa;
import com.example.trovataapp.R;
import com.example.trovataapp.Validacao.CNPJValidator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ActivityCadastrarEmpresa extends AppCompatActivity {

    private static EmpresaLoginRecyclerViewAdapter empresaRecyclerViewAdapter;
    private static List<Empresa> empresas;
    private Banco banco;
    private Empresa empresaEdicao = null;
    private EditText nomeFantasia, razaoSocial, endereco, bairro, cep, pais, cidade, telefone, fax, cnpj, ie;
    private FloatingActionButton btnCadastrarEmpresa;
    private boolean sucesso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_empresa);


        iniciarItens();
        carregarItensEmpresaEdicao();
        salvarEmpresa();
        mascaraCep();
        mascaraCNPJ();
        mascaraTelefone();


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
            cnpj.setText(empresaEdicao.getCNPJ());
            ie.setText(empresaEdicao.getIE());

        }


    }

    private void salvarEmpresa() {
        btnCadastrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {

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

        cep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    apiBuscaCep(cep.getText().toString());
                }
            }
        });

    }


    private void apiBuscaCep(String cep) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://viacep.com.br/ws/" + cep + "/json/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            if (!response.equals("erro")) {
                                JSONObject cep = new JSONObject(response);


                                endereco.setText(cep.getString("logradouro"));
                                bairro.setText(cep.getString("bairro"));
                                cidade.setText(cep.getString("localidade"));


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);


    }

    private boolean validarCampos() {

        String cnpjSemMascara = cnpj.getText().toString().replaceAll("\\D", "");


        if (nomeFantasia.getText().toString().length() == 0) {
            nomeFantasia.setError("Digite nome Fantasia!");
            return false;
        }


        if (razaoSocial.getText().toString().length() == 0) {
            razaoSocial.setError("Digite Razão Social");
            return false;
        }

        if (endereco.getText().toString().length() == 0) {
            endereco.setError("Digite endereco!");
            return false;
        }

        if (bairro.getText().toString().length() == 0) {
            bairro.setError("Digite bairro!");
            return false;
        }

        if (cep.getText().toString().length() == 0) {
            cep.setError("Digite cep!");
            return false;
        }


        if (pais.getText().toString().length() == 0) {
            pais.setError("Digite País!");
            return false;
        }
        if (cidade.getText().toString().length() == 0) {
            cidade.setError("Digite cidade!");
            return false;
        }

        if (telefone.getText().toString().length() == 0) {
            telefone.setError("Digite telefone!");
            return false;
        }
        if (cnpj.getText().toString().length() == 0) {
            cnpj.setError("Digite CNPJ!");
            return false;
        }
        if (!CNPJValidator.isCNPJ(cnpjSemMascara)) {
            cnpj.setError("CNPJ inválido!");
            return false;
        }
        if (ie.getText().toString().length() == 0) {
            ie.setError("Digite IE!");
            return false;
        }


        return true;
    }

    private void mascaraCep() {
        cep.addTextChangedListener(Mask.insert("#####-###", cep));
    }

    private void mascaraCNPJ() {
        cnpj.addTextChangedListener(Mask.insert("##.###.###/####-##", cnpj));
    }

    private void mascaraTelefone() {
        telefone.addTextChangedListener(Mask.insert("(##)####-####", telefone));

    }


}
