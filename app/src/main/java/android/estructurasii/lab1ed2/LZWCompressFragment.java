package android.estructurasii.lab1ed2;

import android.app.Activity;
import android.content.Intent;
import android.estructurasii.lab1ed2.Huffman.pathProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class LZWCompressFragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
   String resolver =  "/storage/emulated/0/Compresiones/";
    Registros register;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_compresslzw,container,false);
        final Button selectFile = view.findViewById(R.id.buttonlzw);
        final Button selectpath = view.findViewById(R.id.buttonselectpath);
        register = new Registros();

        selectFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);


                intent.addCategory(Intent.CATEGORY_OPENABLE);


                intent.setType("text/plain");

                startActivityForResult(Intent.createChooser(intent,"Seleccione Archivo"), READ_REQUEST_CODE);
            }
        });
        selectpath.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);

                startActivityForResult(Intent.createChooser(intent,"Seleccione Ruta"),READ_SELECT_CODE);

            }
        });
        return view;
    }
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedFile = resultData.getData();
            // se convierte el Uri a file para obtener el nombre del archivo
            File tempfile = new File(selectedFile.getPath());
            String name = tempfile.getName().replace('.','_'); // nombre de nuevo archivo igual al archivo elegido
           // archivo a escribir
           File newfile = new File(resolver,name+".lzw");
            Toast.makeText(getContext(),"Guardado en" +resolver+name+".lzw", Toast.LENGTH_LONG).show();
            pathProvider provider = new pathProvider();
            //se guarda en bitácora
            register.AddRegister(newfile.getPath(),provider.getPath(getContext(),selectedFile),"LZW");


        }
        if(requestCode == READ_SELECT_CODE && resultCode == Activity.RESULT_OK){
            Uri selected = resultData.getData();
            pathProvider provider = new pathProvider();
            // se convierte el Uritree en un path normal
            resolver = provider.getFullPathFromTreeUri(selected,getContext());

        }


    }
}
