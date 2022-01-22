package modelo;

public class Archivos {
    private String nombre, fecha, url;

    public Archivos() {
    }

    public Archivos(String nombre, String fecha, String url) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
