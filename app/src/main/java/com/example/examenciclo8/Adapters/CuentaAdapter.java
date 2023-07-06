package com.example.examenciclo8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenciclo8.Clases.Cuenta;
import com.example.examenciclo8.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuentaAdapter extends RecyclerView.Adapter {

    private List<Cuenta> cuentas;
    private Context context;

    public CuentaAdapter(List<Cuenta> cuentas, Context context) {
        this.cuentas = cuentas;
        this.context =context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NameViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == 1) {
            View view = inflater.inflate(R.layout.cuenta_string, parent, false);
            viewHolder = new NameViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.cuenta_progressbar, parent, false);
            viewHolder = new NameViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cuenta item = cuentas.get(position);

        if(item == null) return;

        View view = holder.itemView;

        TextView tvName = view.findViewById(R.id.tvName);
        Button bttnRegistrar = view.findViewById(R.id.Registrar);
        Button bttnMostrar = view.findViewById(R.id.Mostrar);
        Button bttnSincronizar = view.findViewById(R.id.Sincronizar);


        tvName.setText(item.getNombre());

    }

    @Override
    public int getItemCount() {
        return cuentas.size();
    }
    @Override
    public int getItemViewType(int position) {
        Cuenta item = cuentas.get(position);
        return item == null ? 0 : 1;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
