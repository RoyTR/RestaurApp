package upc.edu.pe.restaurapp.Entidades;

/**
 * Created by Rombo on 22/05/2015.
 */
public class Restaurante {
    private Integer IdRestaurante;
    private String Nombre;
    private String Latitud;
    private String Longitud;
    private String Descripcion;
    private Integer Foto_id;
    private Double PuntuacionTotal;
    private Integer NumeroReomendaciones;
    private String Distrito;

    //categoria invalida para que use default y no se caiga al listar
    private Integer Categoria = 11;

    public Integer getCategoria() {
        return Categoria;
    }

    public void setCategoria(Integer categoria) {
        Categoria = categoria;
    }



    public Integer getIdRestaurante() {
        return IdRestaurante;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        IdRestaurante = idRestaurante;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Integer getFoto_id() {
        return Foto_id;
    }

    public void setFoto_id(Integer foto_id) {
        Foto_id = foto_id;
    }

    public Double getPuntuacionTotal() {
        return PuntuacionTotal;
    }

    public void setPuntuacionTotal(Double puntuacionTotal) {
        PuntuacionTotal = puntuacionTotal;
    }

    public Integer getNumeroReomendaciones() {
        return NumeroReomendaciones;
    }

    public void setNumeroReomendaciones(Integer numeroReomendaciones) {
        NumeroReomendaciones = numeroReomendaciones;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }


}
