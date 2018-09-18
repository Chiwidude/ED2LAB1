package android.estructurasii.lab1ed2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileChooser extends ListActivity
{
    private File currentDir;
    private FileArrayAdapter adapter;

    public static String nombreArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        currentDir=new File("/sdcard/");

    }

    public void fill(File f)
    {
        File[]dirs=f.listFiles();
        List<Option> dir = new ArrayList<Option>();
        List<Option> fls = new ArrayList<Option>();
        try
        {
            for (File ff:dirs)
            {
                if (ff.isDirectory())
                {
                    dir.add(new Option(ff.getName(), "Folder", ff.getAbsolutePath()));
                }
                else
                {
                    fls.add(new Option(ff.getName(), "File Size: "+ff.length(), ff.getAbsolutePath()));
                }
            }
        }
        catch (Exception e)
        {
        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if (!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Option(". .","Parent Directory", f.getParent()));
        adapter = new FileArrayAdapter(FileChooser.this, R.layout.activity_fill__view, dir);
        this.setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        if (o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory"))
        {
            currentDir = new File(o.getPath());
            fill(currentDir);
        }
        else
        {
            onFileClick(o);
        }
    }

    private void onFileClick(Option o)
    {
        Toast.makeText(this,"FileClicked: ", Toast.LENGTH_LONG).show();
        nombreArchivo = o.getName();
        Intent ventana = new Intent(this, LeerArchivo.class);
        startActivity(ventana);
    }

}
