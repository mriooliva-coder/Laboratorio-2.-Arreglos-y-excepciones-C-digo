public class Pista {

    private String codigo;
    private String descripcion;
    private String tipoEvidencia;
    private int nivelImportancia;
    private int nivelConfiabilidad;

    public Pista(String codigo, String descripcion, String tipoEvidencia,
                 int nivelImportancia, int nivelConfiabilidad) {
        validarTexto(codigo, "codigo");
        validarTexto(descripcion, "descripcion");
        validarTexto(tipoEvidencia, "tipo de evidencia");
        validarImportancia(nivelImportancia);
        validarConfiabilidad(nivelConfiabilidad);

        this.codigo = codigo.trim();
        this.descripcion = descripcion.trim();
        this.tipoEvidencia = tipoEvidencia.trim();
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    private void validarImportancia(int nivelImportancia) {
        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new IllegalArgumentException("El nivel de importancia debe estar entre 1 y 10.");
        }
    }

    private void validarConfiabilidad(int nivelConfiabilidad) {
        if (nivelConfiabilidad < 0 || nivelConfiabilidad > 100) {
            throw new IllegalArgumentException("El nivel de confiabilidad debe estar entre 0 y 100.");
        }
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacio.");
        }
    }

    public void modificar(String descripcion, String tipoEvidencia,
                          int nivelImportancia, int nivelConfiabilidad) {
        validarTexto(descripcion, "descripcion");
        validarTexto(tipoEvidencia, "tipo de evidencia");
        validarImportancia(nivelImportancia);
        validarConfiabilidad(nivelConfiabilidad);

        this.descripcion = descripcion.trim();
        this.tipoEvidencia = tipoEvidencia.trim();
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + " | Descripcion: " + descripcion
                + " | Tipo: " + tipoEvidencia
                + " | Importancia: " + nivelImportancia
                + " | Confiabilidad: " + nivelConfiabilidad + "%";
    }
}
