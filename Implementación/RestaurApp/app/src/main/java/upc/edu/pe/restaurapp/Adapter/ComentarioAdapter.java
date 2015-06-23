package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Comentario;
import upc.edu.pe.restaurapp.Entidades.Distrito;
import upc.edu.pe.restaurapp.R;

public class ComentarioAdapter extends BaseAdapter {

    private List<Comentario> comentarios;
    private Context context;

    public ComentarioAdapter(List<Comentario> comentarios, Context context){
        this.comentarios = comentarios;
        this.context = context;

    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_comentario_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView Nombre = (TextView) convertView.findViewById(R.id.Comentariolst_nombre);

        //aqui esta el objeto distrito
        Comentario comentario = comentarios.get(position);

        //LLenar aqui todos los parametros TODO
        Nombre.setText(comentario.getComentario());

        return convertView;
    }
}
