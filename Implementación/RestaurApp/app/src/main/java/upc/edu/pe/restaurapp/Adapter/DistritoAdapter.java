package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Distrito;
import upc.edu.pe.restaurapp.Entidades.Restaurante;
import upc.edu.pe.restaurapp.R;

public class DistritoAdapter extends BaseAdapter {

    private List<Distrito> distritos;
    private Context context;

    public DistritoAdapter(List<Distrito> distritos, Context context){
        this.distritos = distritos;
        this.context = context;

    }

    @Override
    public int getCount() {
        return distritos.size();
    }

    @Override
    public Object getItem(int position) {
        return distritos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_distrito_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView Nombre = (TextView) convertView.findViewById(R.id.Distritolst_nombre);

        //aqui esta el objeto distrito
        Distrito distrito = distritos.get(position);

        //LLenar aqui todos los parametros TODO
        Nombre.setText(distrito.getNombre());

        return convertView;
    }
}
