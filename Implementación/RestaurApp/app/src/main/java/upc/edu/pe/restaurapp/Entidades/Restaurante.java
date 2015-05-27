package upc.edu.pe.restaurapp.Entidades;

/**
 * Created by Rombo on 22/05/2015.
 */
public class Restaurante {
    private Integer IdRestaurante;
    private String Nombre;
    private String Distrito;
    private String TipoComida;
    private Double Latitud;
    private Double Longitud;
    private String Descripcion;
    private Integer Foto_id;
    private Double PuntuacionTotal;
    private Boolean EsFavorito;

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

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
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

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getTipoComida() {
        return TipoComida;
    }

    public void setTipoComida(String tipoComida) {
        TipoComida = tipoComida;
    }

    public Boolean getEsFavorito() {
        return EsFavorito;
    }

    public void setEsFavorito(Boolean esFavorito) {
        EsFavorito = esFavorito;
    }
}
