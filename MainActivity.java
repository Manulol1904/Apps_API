package com.example.api_calcu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText id;
    private Button boton;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.Numero);
        boton = findViewById(R.id.Buscar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = id.getText().toString();
                url = "http://192.168.1.109/class/consulta.php?Dato=";

                // Iniciar la tarea AsyncTask para realizar la solicitud de red
                new ApiRequestTask().execute(idText);
            }
        });
    }

    private class ApiRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String idText = params[0];
            String result = null;

            try {
                result = obtenerDatos(idText);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {

                    JSONArray jsonArray = new JSONArray(result);

                    String elemento1 = jsonArray.getString(0);
                    String elemento2 = jsonArray.getString(1);
                    String elemento3 = jsonArray.getString(2);
                    String elemento4 = jsonArray.getString(3);
                    String elemento5 = jsonArray.getString(4);

                    TextView t1 = findViewById(R.id.t1);
                    t1.setText(elemento2);

                    TextView t2 = findViewById(R.id.t2);
                    t2.setText(elemento3);

                    TextView t3 = findViewById(R.id.t3);
                    t3.setText(elemento4);

                    TextView t4 = findViewById(R.id.t4);
                    t4.setText(elemento5);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Log.e("Error", "No se pudo obtener la respuesta de la API.");
            }
        }
    }

    public String obtenerDatos(String Dato) throws IOException {
        URL urlConexion = new URL("http://192.168.1.109/class/consulta.php?Dato=" + Dato);
        HttpURLConnection conexion = (HttpURLConnection) urlConexion.openConnection();

        int codigoRespuesta = conexion.getResponseCode();
        if (codigoRespuesta == 200) {
            InputStream respuesta = conexion.getInputStream();
            return new Scanner(respuesta).useDelimiter("\\A").next();
        } else {
            throw new IOException("Error al obtener los datos de la API: " + codigoRespuesta);
        }
    }
}
