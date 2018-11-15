package com.example.israelgutierrez.prueba;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class notificacion_pedido extends AppCompatActivity implements View.OnClickListener{
    Button mNotificar;
    Button mverDireccion;
    private static final String TAG = notificacion_pedido.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_pedido);

        mNotificar=(Button) findViewById(R.id.notificar);
        mverDireccion =(Button) findViewById(R.id.verDireccion);

        mverDireccion.setOnClickListener(this);
        mNotificar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.verDireccion:

                intent = new Intent(this, verDireccion.class);
                startActivity(intent);
                break;
            case R.id.notificar:

                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                int icono = R.mipmap.ic_launcher;
                intent = new Intent(notificacion_pedido.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(notificacion_pedido.this, 0,intent, 0);
                mBuilder = new NotificationCompat.Builder(getApplicationContext());
                mBuilder.setContentIntent (pendingIntent);
                mBuilder.setSmallIcon(icono);
                mBuilder.setContentTitle("OrdersHome pedido enviado");
                mBuilder.setContentText("Su pedido de tortillas ha zarpado, ¡En hora buena!");
                mBuilder.setVibrate(new long[] {100, 250, 100, 500});
                mBuilder.setAutoCancel(true);
                mNotifyMgr.notify(1, mBuilder.build());
                break;
        }
    }
}
