package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admi_entrar_crear extends AppCompatActivity implements View.OnClickListener{
    Button entrar,crear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_entrar_crear);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        entrar = (Button) findViewById(R.id.entrar);
        crear = (Button) findViewById(R.id.crear);

        entrar.setOnClickListener(this);
        crear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.entrar:
                Intent intent = new Intent(admi_entrar_crear.this,login_empresa.class);
                startActivity(intent);
            break;
            case R.id.crear:
                Intent intent1 = new Intent(admi_entrar_crear.this,formulario_empresa.class);
                startActivity(intent1);
            break;
        }

    }
}
