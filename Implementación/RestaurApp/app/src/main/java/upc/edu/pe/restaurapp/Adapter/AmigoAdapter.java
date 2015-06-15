package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Usuario;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Roy on 14/06/2015.
 */
public class AmigoAdapter extends BaseAdapter {

    private List<Usuario> usuarios;
    private Context context;

    public AmigoAdapter(List<Usuario> usuarios,Context context){
        this.usuarios = usuarios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_amigo_fila, parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView tvAmigo = (TextView) convertView.findViewById(R.id.contactos_tv_amigo_nombres_apellidos);

        //aqui esta el objeto usuario
        Usuario usuario = usuarios.get(position);

        //LLenar aqui todos los parametros TODO
        tvAmigo.setText(usuario.getNombres() + " " + usuario.getApellidos());
        //Descripcion.setText(categoria.getDescripcion());

        return convertView;
    }
}
