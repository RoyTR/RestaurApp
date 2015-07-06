package upc.edu.pe.restaurapp.Entidades;

public class Foto {
    private Integer id;
    private String nombre;
    private String url;
    private String url_min;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_min() {
        return url_min;
    }

    public void setUrl_min(String url_min) {
        this.url_min = url_min;
    }
}
