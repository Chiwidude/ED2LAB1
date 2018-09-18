package android.estructurasii.lab1ed2;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class LeerArchivo extends AppCompatActivity {

    Button Buscar;
    Button Abrir;
    EditText Texto;
    EditText direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_archivo);

        Buscar = (Button)findViewById(R.id.bLeerArchivo);
        Abrir = (Button)findViewById(R.id.bAbrir);
        Texto = (EditText)findViewById(R.id.etTexto);

        direccion.setText(FileChooser.nombreArchivo);

        Buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CambiarActividad(view);
            }
        });
        Abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File ruta_sd = Environment.getExternalStorageDirectory();
                File f = new File(ruta_sd.getAbsolutePath(), direccion.getText().toString());
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String linea = br.readLine();
                    String todo = "";
                    while (linea != null)
                    {
                        todo+=linea + "\n";
                        linea = br.readLine();
                    }
                    Texto.setText(todo);
                }
                catch(Exception e)
                {
                }
            }
        });
    }

    public void CambiarActividad(View view)
    {
        Intent ventana = new Intent(this, FileChooser.class);
        startActivity(ventana);
    }


}
