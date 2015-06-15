package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Categoria;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Rombo on 14/06/2015.
 */
public class CategoriaPreferenciaAdapter extends BaseAdapter {

    private List<Categoria> categorias;
    private Context context;

    public CategoriaPreferenciaAdapter(List<Categoria> categorias, Context context){
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
            convertView = layoutInflater.inflate(R.layout.lista_categoria_pref_fila , parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView Nombre = (TextView) convertView.findViewById(R.id.categoria_pref_lst_nombre);
        final CheckBox favorito = (CheckBox) convertView.findViewById(R.id.categoria_pref_lst_fav);

        //aqui esta el objeto categoria
        final Categoria categoria = categorias.get(position);

        //LLenar aqui todos los parametros TODO
        Nombre.setText(categoria.getNombre());
        favorito.setChecked( categoria.isPref() );

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isCheked = favorito.isChecked();
                categoria.setPref(isCheked);
            }
        });

        return convertView;
    }
}