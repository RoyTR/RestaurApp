package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Categoria;
import upc.edu.pe.restaurapp.Entidades.Distrito;
import upc.edu.pe.restaurapp.R;

public class CategoriaAdapter extends BaseAdapter {

    private List<Categoria> categorias;
    private Context context;

    public CategoriaAdapter(List<Categoria> categorias, Context context){
        this.categorias = categorias;
        this.context = context;

    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lista_categoria_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView Nombre = (TextView) convertView.findViewById(R.id.Categorialst_nombre);
        ImageView IconoComida = (ImageView) convertView.findViewById(R.id.cat_img);
        //TextView Descripcion = (TextView) convertView.findViewById(R.id.Categorialst_descripcion);

        //aqui esta el objeto categoria
        Categoria categoria = categorias.get(position);

        //LLenar aqui todos los parametros TODO
        Nombre.setText(categoria.getNombre());
        switch (categoria.getId()){
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
        //Descripcion.setText(categoria.getDescripcion());

        return convertView;
    }
}
