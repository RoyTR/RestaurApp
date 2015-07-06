package upc.edu.pe.restaurapp.Entidades;

/**
 * Created by Rombo on 22/06/2015.
 */
public class Comentario {

    private Integer id;
    private String comentario;

    private String nomusuario;
    private String fecha;

    private String puntuacion;
    private Integer restaurante_id;
    private Integer grupo_id;

    public String getNomusuario() {
        return nomusuario;
    }

    public void setNomusuario(String nomusuario) {
        this.nomusuario = nomusuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Integer getGrupo_id() {
        return grupo_id;
    }

    public void setGrupo_id(Integer grupo_id) {
        this.grupo_id = grupo_id;
    }

    public Integer getRestaurante_id() {
        return restaurante_id;
    }

    public void setRestaurante_id(Integer restaurante_id) {
        this.restaurante_id = restaurante_id;
    }
}
