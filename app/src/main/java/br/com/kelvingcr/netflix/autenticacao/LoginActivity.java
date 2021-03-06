package br.com.kelvingcr.netflix.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.kelvingcr.netflix.R;
import br.com.kelvingcr.netflix.activity.MainActivity;
import br.com.kelvingcr.netflix.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {


    private EditText edt_email, edt_senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        configCliques();
    }

    private void configCliques(){
        findViewById(R.id.btn_continuar).setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });
    }


    private void initComponents() {
        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
    }
    
    public void validaDados(View view) {

        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                loginFirebase(email, senha);

            } else {
                edt_senha.requestFocus();
                edt_senha.setError("Campo obrigatório.");
            }

        } else {
            edt_email.requestFocus();
            edt_email.setError("Campo obrigatório.");
        }

    }

    private void loginFirebase(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this, FirebaseHelper.validaErros(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

}