package upc.edu.pe.restaurapp.Entidades;

/**
 * Created by Rombo on 05/07/2015.
 */
public class Recomendaciones {

    private String Comentario;
    private String NombreRest;
    private String NombreUsuario;
    private String Puntuacion;

    private Integer RestId;
    private Integer UsuaId;

    public Integer getRestId() {
        return RestId;
    }

    public void setRestId(Integer restId) {
        RestId = restId;
    }

    public Integer getUsuaId() {
        return UsuaId;
    }

    public void setUsuaId(Integer usuaId) {
        UsuaId = usuaId;
    }


    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getNombreRest() {
        return NombreRest;
    }

    public void setNombreRest(String nombreRest) {
        NombreRest = nombreRest;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        Puntuacion = puntuacion;
    }



}
