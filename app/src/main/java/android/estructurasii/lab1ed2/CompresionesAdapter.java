package android.estructurasii.lab1ed2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CompresionesAdapter extends RecyclerView.Adapter<CompresionesAdapter.CompresionesViewHolder> {
    private Context myContext;


    @NonNull
    @Override
    public CompresionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CompresionesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CompresionesViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1;
        public TextView textView2;
        public TextView textView03;
        public TextView textView4;
        public TextView textView5;

        public CompresionesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.TextView01);
            textView2 = itemView.findViewById(R.id.TextView02);
            textView03 = itemView.findViewById(R.id.TextView03);
            textView4 = itemView.findViewById(R.id.TextView04);
            textView5 = itemView.findViewById(R.id.TextView05);
        }
    }
}
