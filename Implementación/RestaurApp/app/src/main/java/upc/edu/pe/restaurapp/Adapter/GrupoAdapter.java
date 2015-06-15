package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Grupo;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Roy on 14/06/2015.
 */
public class GrupoAdapter extends BaseAdapter {

    private List<Grupo> grupos;
    private Context context;

    public GrupoAdapter(List<Grupo> grupos, Context context) {
        this.grupos = grupos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return grupos.size();
    }

    @Override
    public Object getItem(int position) {
        return grupos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_grupo_fila, parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView tvGrupo = (TextView) convertView.findViewById(R.id.contactos_tv_grupo_nombre);

        //aqui esta el objeto usuario
        Grupo grupo = grupos.get(position);

        //LLenar aqui todos los parametros TODO
        tvGrupo.setText(grupo.getNombre());
        //Descripcion.setText(categoria.getDescripcion());

        return convertView;
    }
}
