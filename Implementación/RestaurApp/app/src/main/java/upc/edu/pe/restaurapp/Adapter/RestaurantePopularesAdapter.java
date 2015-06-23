package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Restaurante;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Rombo on 22/05/2015.
 */
public class RestaurantePopularesAdapter extends BaseAdapter {

    private List<Restaurante> restaurantes;
    private Context context;

    public RestaurantePopularesAdapter(List<Restaurante> restaurantes, Context context){
        this.restaurantes = restaurantes;
        this.context = context;

    }

    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_restaurante_populares_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView Nombre = (TextView) convertView.findViewById(R.id.restaurantelst_nombre);
        TextView Descripcion = (TextView) convertView.findViewById(R.id.restaurantelst_descripcion);
        TextView Recomendaciones = (TextView) convertView.findViewById(R.id.restaurantelst_recomendaciones);

        Restaurante restaurante = restaurantes.get(position);

        //LLenar aqui todos los parametros TODO
        Nombre.setText(restaurante.getNombre());
        Descripcion.setText(restaurante.getDescripcion());
        Recomendaciones.setText(restaurante.getNumeroReomendaciones().toString());

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
