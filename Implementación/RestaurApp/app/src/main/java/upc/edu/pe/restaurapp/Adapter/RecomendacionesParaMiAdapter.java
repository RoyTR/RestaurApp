package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Recomendaciones;
import upc.edu.pe.restaurapp.Entidades.Restaurante;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Rombo on 22/05/2015.
 */
public class RecomendacionesParaMiAdapter extends BaseAdapter {

    private List<Recomendaciones> recomendaciones;
    private Context context;

    public RecomendacionesParaMiAdapter(List<Recomendaciones> recomendaciones, Context context){
        this.recomendaciones = recomendaciones;
        this.context = context;

    }

    @Override
    public int getCount() {
        return recomendaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return recomendaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_recom_parami_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView NombreRest = (TextView) convertView.findViewById(R.id.recomparami_nombrerest);
        TextView Comentario = (TextView) convertView.findViewById(R.id.recomparami_comentrest);
        TextView Puntaje = (TextView) convertView.findViewById(R.id.recomparami_puntrest);
        TextView NombreUsu = (TextView) convertView.findViewById(R.id.recomparami_nomusu);

        Recomendaciones recomendacion = recomendaciones.get(position);

        //LLenar aqui todos los parametros TODO
        NombreRest.setText(recomendacion.getNombreRest());
        Comentario.setText(recomendacion.getComentario());
        Puntaje.setText(recomendacion.getPuntuacion());
        NombreUsu.setText(recomendacion.getNombreUsuario());

        //se comen el click D:
        //Button boton = (Button) convertView.findViewById(R.id.restaurantelst_btn);
        //boton.setFocusable(false);
        //boton.setFocusableInTouchMode(false);
        //boton.setClickable(true);
        //CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.restaurantelst_chkbx);
        //checkBox.setFocusable(false);
        //checkBox.setFocusableInTouchMode(false);
        //checkBox.setClickable(true);

        return convertView;
    }
}
