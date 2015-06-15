package upc.edu.pe.restaurapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Usuario;
import upc.edu.pe.restaurapp.R;

/**
 * Created by Roy on 14/06/2015.
 */
public class AgregarUsuarioAdapter extends BaseAdapter {
    private List<Usuario> usuarios;
    private Context context;
    private List<Integer> usuarioMarcado;
    public AgregarUsuarioAdapter(List<Usuario> usuarios,Context context){
        this.usuarios = usuarios;
        this.usuarioMarcado = new ArrayList<Integer>();
        this.context = context;
    }
    public AgregarUsuarioAdapter(List<Usuario> usuarios, List<Integer> usuarioMarcado,Context context){
        this.usuarios = usuarios;
        this.usuarioMarcado = usuarioMarcado;
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
            convertView = layoutInflater.inflate(R.layout.lista_crear_grupo_fila, parent, false);
        }

        //Usar aqui todos los parametros necesarios a mostrar TODO
        TextView tvAmigo = (TextView) convertView.findViewById(R.id.contactos_crear_grupo_tv_usuario);
        final CheckBox chkAmigo = (CheckBox) convertView.findViewById(R.id.contactos_crear_grupo_chk_usuario);

        //aqui esta el objeto usuario
        Usuario usuario = usuarios.get(position);

        //LLenar aqui todos los parametros TODO
        tvAmigo.setText(usuario.getNombres() + " " + usuario.getApellidos());
        if(usuarioMarcado.contains(usuario.getId())) {
            chkAmigo.setChecked(true);
        } else {
            chkAmigo.setChecked(false);
        }
        /*
        chkAmigo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chkAmigo.setChecked(false);
                } else {
                    chkAmigo.setChecked(true);
                }
            }
        });
        */

        return convertView;
    }

    class Holder
    {
        TextView textViewTitle;
        CheckBox checkBox;

        public TextView getTextViewTitle()
        {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle)
        {
            this.textViewTitle = textViewTitle;
        }

        public CheckBox getCheckBox()
        {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox)
        {
            this.checkBox = checkBox;
        }

    }
}
