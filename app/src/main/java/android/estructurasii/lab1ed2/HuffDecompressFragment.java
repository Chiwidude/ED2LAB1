package android.estructurasii.lab1ed2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.estructurasii.lab1ed2.Huffman.Huffman;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HuffDecompressFragment extends Fragment {
    private static final int WRITE_REQUEST_CODE = 43;
    Huffman Algorithm;
    TextView fileview;
    Button btnRuta;
    Button btnSave;
    TextView Ruta;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_decompress,container,false);
        final Button debcompress = view.findViewById(R.id.button1);
        btnRuta = (Button)view.findViewById(R.id.bAbrir);
        btnSave = (Button)view.findViewById(R.id.btnGuardar);
        fileview = view.findViewById(R.id.textcompress);
        Ruta = view.findViewById(R.id.tvRuta);
        Algorithm = new Huffman();
        debcompress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);


                intent.addCategory(Intent.CATEGORY_OPENABLE);


                intent.setType("*/*");

                startActivityForResult(Intent.createChooser(intent,"Seleccione Archivo"), WRITE_REQUEST_CODE);

            }
        });

        return view;
    }

    @TargetApi(VERSION_CODES.N)
    @RequiresApi(api = VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri_ = resultData.getData();
            try {
                InputStream input = getContext().getContentResolver().openInputStream(uri_);
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String caracteres = br.readLine();
                ArrayList<String> stringsarray = new ArrayList<>();
                Algorithm.DecomTable(caracteres);
                String linea;
                while((linea = br.readLine()) != null){
                    stringsarray.add(linea);
                }
                String[] resultsBinary = new String[stringsarray.size()];
                for(int i = 0; i<stringsarray.size();i++) {
                    resultsBinary[i] = Algorithm.ConvertToBinary(stringsarray.get(i));
                }
                ArrayList<String> resultArray = new ArrayList<>();
                for(int i = 0; i<resultsBinary.length;i++){
                    resultArray.add(Algorithm.Decompress(resultsBinary[i]));
                }
                File storage = new File(Environment.getExternalStorageDirectory(),"Descompresión");
                if(!storage.exists()){
                    storage.mkdirs();
                }
                File path = new File(storage,"Documents"+".txt");
                //Agregado
                Ruta.setText(path.getPath().toString());
                //
                FileOutputStream outputStream = new FileOutputStream(path);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                for(int i = 0; i<resultArray.size();i++){
                    bufferedWriter.write(resultArray.get(i));
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                Toast.makeText(getContext(),"Guardado en Descompresiones"+"/"+"Documents"+".txt", Toast.LENGTH_LONG).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

   // public void CambiarActividad(View view)
    //{
     //   Intent ventana = new Intent(this, FileChooser.class);
     //   startActivity(ventana);
   // }

}
