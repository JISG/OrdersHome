package com.example.israelgutierrez.prueba;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestRepartidor extends StringRequest{
    private static final String LOGIN_REQUEST_URL="https://sgvshop.000webhostapp.com/login_repartidor.php";
    private Map<String,String> params;

    public LoginRequestRepartidor(String username, String password, Response.Listener<String> listener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
