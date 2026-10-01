public class Ubicacion {

    private String codigo;
    private String nombre;
    private String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion, int nivelRiesgo, String estado) {
        validarTexto(codigo, "codigo");
        validarTexto(nombre, "nombre");
        validarTexto(direccion, "direccion");
        validarRiesgo(nivelRiesgo);
        validarTexto(estado, "estado");

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
        this.nivelRiesgo = nivelRiesgo;
        this.estado = estado.trim();
    }

    private void validarRiesgo(int nivelRiesgo) {
        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new IllegalArgumentException("El nivel de riesgo debe estar entre 1 y 10.");
        }
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacio.");
        }
    }

    public void modificar(int nuevoRiesgo, String nuevoEstado) {
        validarRiesgo(nuevoRiesgo);
        validarTexto(nuevoEstado, "estado");
        this.nivelRiesgo = nuevoRiesgo;
        this.estado = nuevoEstado.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getEstado() {
        return estado;
    }

    public void setNivelRiesgo(int nivelRiesgo) {
        validarRiesgo(nivelRiesgo);
        this.nivelRiesgo = nivelRiesgo;
    }

    public void setEstado(String estado) {
        validarTexto(estado, "estado");
        this.estado = estado.trim();
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + " | Nombre: " + nombre
                + " | Direccion: " + direccion
                + " | Riesgo: " + nivelRiesgo
                + " | Estado: " + estado;
    }
}
