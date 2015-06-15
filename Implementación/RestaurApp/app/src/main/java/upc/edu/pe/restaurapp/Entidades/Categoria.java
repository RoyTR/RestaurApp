package upc.edu.pe.restaurapp.Entidades;

public class Categoria {
    private Integer id;
    private String nombre;
    private String descripcion;

    //solo se usara para las preferencias de usuario
    private boolean pref = false;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    //solo se usara para las preferencias de usuario
    public boolean isPref() {
        return pref;
    }
    public void setPref(boolean pref) {
        this.pref = pref;
    }




}
