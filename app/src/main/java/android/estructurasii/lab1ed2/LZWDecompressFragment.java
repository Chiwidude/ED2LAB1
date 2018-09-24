package android.estructurasii.lab1ed2;

import android.app.Activity;
import android.content.Intent;
import android.estructurasii.lab1ed2.Huffman.pathProvider;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class LZWDecompressFragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
    String solver =  "/storage/emulated/0/Descompresiones/";
    String NameToSave = "";
    Registros register;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         final View view = inflater.inflate(R.layout.fragment_decompresslzw,container,false);
         final Button btnruta = view.findViewById(R.id.buttonselectpath_);
         final Button selectFile = view.findViewById(R.id.buttonlzw_);
         final EditText NombreArchivo = view.findViewById(R.id.edNombre);
         register = new Registros();
         btnruta.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);

                 startActivityForResult(Intent.createChooser(intent,"Seleccione Ruta"),READ_SELECT_CODE);
             }
         });
         selectFile.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 if(!NombreArchivo.getText().toString().isEmpty()){
                     NameToSave = NombreArchivo.getText().toString();
                     Intent intent = new Intent();
                     intent.setAction(Intent.ACTION_OPEN_DOCUMENT);


                     intent.addCategory(Intent.CATEGORY_OPENABLE);


                     intent.setType("*/*");

                     startActivityForResult(Intent.createChooser(intent,"Seleccione Archivo"), READ_REQUEST_CODE);
                 } else{
                     Toast.makeText(getContext(), "Debe Ingresar Nombre de Archivo", Toast.LENGTH_LONG).show();

                 }
             }
         });
        return view;
    }
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedFile = resultData.getData();
            // se convierte el Uri a file para obtener el nombre del archivo
             // nombre de nuevo archivo igual al archivo elegido
            // archivo a escribir
            File newfile = new File(solver, NameToSave + ".txt");
            Toast.makeText(getContext(), "Guardado en" + solver + NameToSave + ".txt", Toast.LENGTH_LONG).show();
            pathProvider provider = new pathProvider();
            //se guarda en bitácora
            register.AddRegister(newfile.getPath(), provider.getPath(getContext(), selectedFile), "LZW");


        }
        if (requestCode == READ_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            Uri selected = resultData.getData();
            pathProvider provider = new pathProvider();
            // se convierte el Uritree en un path normal
            solver = provider.getFullPathFromTreeUri(selected, getContext());

        }
    }
}
