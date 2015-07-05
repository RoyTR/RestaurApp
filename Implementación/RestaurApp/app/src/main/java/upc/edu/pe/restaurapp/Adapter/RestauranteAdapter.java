package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Restaurante;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Rombo on 22/05/2015.
 */
public class RestauranteAdapter extends BaseAdapter {

    private List<Restaurante> restaurantes;
    private Context context;

    public RestauranteAdapter(List<Restaurante> restaurantes,Context context){
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
            convertView = layoutInflater.inflate(R.layout.lista_restaurante_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView Nombre = (TextView) convertView.findViewById(R.id.restaurantelst_nombre);
        TextView Descripcion = (TextView) convertView.findViewById(R.id.restaurantelst_descripcion);
        TextView Puntuacion = (TextView) convertView.findViewById(R.id.restaurantelst_puntuacion);
        ImageView IconoComida = (ImageView) convertView.findViewById(R.id.rest_row_foodico);

        Restaurante restaurante = restaurantes.get(position);

        //LLenar aqui todos los parametros TODO
        Nombre.setText(restaurante.getNombre());
        Descripcion.setText(restaurante.getDescripcion());
        Puntuacion.setText(restaurante.getPuntuacionTotal().toString());

        switch (restaurante.getCategoria()){
            case 1: IconoComida.setImageResource(R.drawable.fd_ico_criolla);
                break;
            case 2: IconoComida.setImageResource(R.drawable.fd_ico_mariscos);
                break;
            case 3: IconoComida.setImageResource(R.drawable.fd_ico_chifa);
                break;
            case 4: IconoComida.setImageResource(R.drawable.fd_ico_italiana);
                break;
            case 5: IconoComida.setImageResource(R.drawable.fd_ico_indu);
                break;
            case 6: IconoComida.setImageResource(R.drawable.fd_ico_carnes);
                break;
            case 7: IconoComida.setImageResource(R.drawable.fd_ico_vegetarianos);
                break;
            case 8: IconoComida.setImageResource(R.drawable.fd_ico_ensaladas);
                break;
            case 9: IconoComida.setImageResource(R.drawable.fd_ico_light);
                break;
            case 10: IconoComida.setImageResource(R.drawable.fd_ico_parrillas);
                break;
            default: IconoComida.setImageResource(R.drawable.fd_ico_def);
                break;
        }

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
