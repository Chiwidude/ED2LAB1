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
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuffDecompressFragment extends Fragment {
    private static final int WRITE_REQUEST_CODE = 43;
    private static int Action = 0;
    private String NameToSave = "";
    Huffman Algorithm;
    TextView fileview;
    Button btnRuta;
    Button btnSave;
    TextView Ruta;
    EditText NombreArchivo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_decompress,container,false);
        final Button debcompress = view.findViewById(R.id.button1);
        final Button btnRuta = view.findViewById(R.id.btnRutaSv);
        final Button btnSave = view.findViewById(R.id.btnGuardar);
        final EditText NombreArchivo = view.findViewById(R.id.etNombre);
        fileview = view.findViewById(R.id.textcompress);
        Ruta = view.findViewById(R.id.tvRuta);
        Algorithm = new Huffman();
        debcompress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action = 1;
                if (!NombreArchivo.getText().toString().isEmpty() && !Ruta.getText().toString().isEmpty())
                {
                    NameToSave = NombreArchivo.getText().toString();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);


                    intent.addCategory(Intent.CATEGORY_OPENABLE);


                    intent.setType("*/*");

                    startActivityForResult(Intent.createChooser(intent,"Seleccione Archivo"), WRITE_REQUEST_CODE);
                }
                else
                {
                    Toast.makeText(getContext(), "Debe Ingresar Nombre de Archivo y Seleccionar Ruta", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnRuta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vw) {
                Action = 2;
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                intent2.setType("*/*");
                startActivityForResult(Intent.createChooser(intent2,"Seleccione Ruta"), WRITE_REQUEST_CODE);
            }
        });

        //btnSave.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
             //   Action = 3;
             //   if (NombreArchivo.getText().toString().isEmpty())
             //   {
             //       Toast.makeText(getContext(), "Debe Ingresar Nombre de Archivo", Toast.LENGTH_LONG).show();
             //   }
             //   else
             //   {

             //   }
           // }
       // });

        return view;
    }

    @TargetApi(VERSION_CODES.N)
    @RequiresApi(api = VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (Action == 1)
        {
            if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
                Uri uri_ = resultData.getData();
                try {
                    InputStream input = getContext().getContentResolver().openInputStream(uri_);
                    BufferedReader br = new BufferedReader(new InputStreamReader(input));
                    String caracteres = br.readLine();
                    ArrayList<String> stringsarray = new ArrayList<>();
                    Algorithm.DecomTable(caracteres);
                    StringBuilder str = new StringBuilder();
                    String linea;
                    while((linea = br.readLine()) != null){

                        linea = RevertChanges(linea);
                        str.append(linea);
                    }
                    String[] lines = str.toString().split("σ");
                    for(int i = 0; i<lines.length; i++){
                        stringsarray.add(lines[i]);
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
                    //File path = new File(storage,"Documents"+".txt");
                    //Agregado
                    File path = new File(storage,NameToSave+".txt");
                    //Ruta.setText(path.getPath().toString());
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
                    //Toast.makeText(getContext(),"Guardado en Descompresiones"+"/"+"Documents"+".txt", Toast.LENGTH_LONG).show();
                    //Agregado
                    Toast.makeText(getContext(),"Guardado en Descompresiones"+"/"+NameToSave+".txt", Toast.LENGTH_LONG).show();
                    //

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
        else if (Action == 2)
        {
            if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
                try {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Ruta.setText(path.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }

    private String RevertChanges(String revert){
        if(revert.contains("ε")) {
            revert = revert.replaceAll(Pattern.quote("ε"), Matcher.quoteReplacement("\n"));
        }
        if(revert.contains("η")) {
            revert = revert.replaceAll(Pattern.quote("η"), Matcher.quoteReplacement("\r"));
        }
        if(revert.contains("Φ")) {
            revert = revert.replaceAll(Pattern.quote("Φ"), Matcher.quoteReplacement("\t"));
        }
        if(revert.contains("θ")) {
            revert = revert.replaceAll(Pattern.quote("θ"), Matcher.quoteReplacement("\f"));
        }
        if(revert.contains("μ")) {
            revert = revert.replaceAll(Pattern.quote("μ"), Matcher.quoteReplacement("\b"));
        }
        if(revert.contains("φ")) {
            revert = revert.replaceAll(Pattern.quote("φ"), Matcher.quoteReplacement("\""));
        }
        if(revert.contains("λ")){
            revert = revert.replaceAll(Pattern.quote("λ"), Matcher.quoteReplacement("\'"));
        }

        return revert;
    }



}
