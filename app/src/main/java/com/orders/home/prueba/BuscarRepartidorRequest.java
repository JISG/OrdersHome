package com.orders.home.prueba;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuscarRepartidorRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL="https://sgvshop.000webhostapp.com/buscarRepartidor.php";
    private Map<String,String> params;

    public BuscarRepartidorRequest(String sucursal, String repartidor, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("sucursal",sucursal);
        params.put("repartidor",repartidor);
    }
}
