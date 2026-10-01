import java.util.ArrayList;


public class Caso {

    public static final int MAX_UBICACIONES = 5;

    private String nombre;
    private String codigo;
    private String detective;
    private Ubicacion[] ubicaciones;   
    private ArrayList<Pista> pistas;   

    public Caso(String nombre, String codigo, String detective) {
        validarTexto(nombre, "nombre del caso");
        validarTexto(codigo, "codigo del caso");
        validarTexto(detective, "nombre del detective");

        this.nombre = nombre.trim();
        this.codigo = codigo.trim();
        this.detective = detective.trim();
        this.ubicaciones = new Ubicacion[MAX_UBICACIONES];
        this.pistas = new ArrayList<Pista>();
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El " + campo + " no puede estar vacio.");
        }
    }


    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IllegalArgumentException("Posicion invalida. Debe estar entre 0 y "
                    + (ubicaciones.length - 1) + ".");
        }
    }

    public boolean posicionDisponible(int posicion) {
        validarPosicion(posicion);
        return ubicaciones[posicion] == null;
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] != null) {
            throw new IllegalArgumentException("La posicion " + posicion + " ya esta ocupada.");
        }
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicacion no tiene informacion valida.");
        }
    
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null
                    && ubicaciones[i].getCodigo().equalsIgnoreCase(ubicacion.getCodigo())) {
                throw new IllegalArgumentException("Ya existe una ubicacion con el codigo "
                        + ubicacion.getCodigo() + " (posicion " + i + ").");
            }
        }
        ubicaciones[posicion] = ubicacion;
    }

    public Ubicacion obtenerUbicacion(int posicion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] == null) {
            throw new IllegalArgumentException("La posicion " + posicion + " esta vacia.");
        }
        return ubicaciones[posicion];
    }

    public void modificarUbicacion(int posicion, int nuevoRiesgo, String nuevoEstado) {
        Ubicacion ubicacion = obtenerUbicacion(posicion); 
        ubicacion.modificar(nuevoRiesgo, nuevoEstado);
    }

    public Ubicacion descartarUbicacion(int posicion) {
        Ubicacion ubicacion = obtenerUbicacion(posicion);
        ubicaciones[posicion] = null; 
        return ubicacion;
    }

    public int contarUbicaciones() {
        int contador = 0;
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                contador++;
            }
        }
        return contador;
    }

    public int contarEspaciosDisponibles() {
        return ubicaciones.length - contarUbicaciones();
    }


    public Ubicacion ubicacionMayorRiesgo() {
        Ubicacion mayor = null;
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                if (mayor == null || ubicaciones[i].getNivelRiesgo() > mayor.getNivelRiesgo()) {
                    mayor = ubicaciones[i];
                }
            }
        }
        return mayor;
    }

    public String listarUbicaciones() {
        if (contarUbicaciones() == 0) {
            return "No hay ubicaciones registradas.";
        }
        String texto = "";
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                texto += "[" + i + "] " + ubicaciones[i].toString() + "\n";
            }
        }
        return texto;
    }


    public Pista buscarPista(String codigoPista) {
        if (codigoPista == null) {
            return null;
        }
        for (int i = 0; i < pistas.size(); i++) {
            if (pistas.get(i).getCodigo().equalsIgnoreCase(codigoPista.trim())) {
                return pistas.get(i);
            }
        }
        return null;
    }

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista no tiene informacion valida.");
        }
        if (buscarPista(pista.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una pista con el codigo " + pista.getCodigo() + ".");
        }
        pistas.add(pista);
    }

    public void modificarPista(String codigoPista, String descripcion, String tipoEvidencia,
                               int importancia, int confiabilidad) {
        Pista pista = buscarPista(codigoPista);
        if (pista == null) {
            throw new IllegalArgumentException("No existe una pista con el codigo " + codigoPista + ".");
        }
        pista.modificar(descripcion, tipoEvidencia, importancia, confiabilidad);
    }

    public Pista eliminarPista(String codigoPista) {
        Pista pista = buscarPista(codigoPista);
        if (pista == null) {
            throw new IllegalArgumentException("No existe una pista con el codigo " + codigoPista + ".");
        }
        pistas.remove(pista);
        return pista;
    }

    public int contarPistas() {
        return pistas.size();
    }

    public boolean hayPistas() {
        return !pistas.isEmpty();
    }


    public Pista pistaMayorImportancia() {
        if (pistas.isEmpty()) {
            return null;
        }
        Pista mayor = pistas.get(0);
        for (int i = 1; i < pistas.size(); i++) {
            if (pistas.get(i).getNivelImportancia() > mayor.getNivelImportancia()) {
                mayor = pistas.get(i);
            }
        }
        return mayor;
    }


    public Pista pistaMayorConfiabilidad() {
        if (pistas.isEmpty()) {
            return null;
        }
        Pista mayor = pistas.get(0);
        for (int i = 1; i < pistas.size(); i++) {
            if (pistas.get(i).getNivelConfiabilidad() > mayor.getNivelConfiabilidad()) {
                mayor = pistas.get(i);
            }
        }
        return mayor;
    }

    public double promedioImportancia() {
        if (pistas.isEmpty()) {
            throw new IllegalStateException("No hay pistas para calcular el promedio.");
        }
        int suma = 0;
        for (int i = 0; i < pistas.size(); i++) {
            suma += pistas.get(i).getNivelImportancia();
        }
        return (double) suma / pistas.size();
    }

    public String listarPistas() {
        if (pistas.isEmpty()) {
            return "Todavia no hay pistas registradas.";
        }
        String texto = "";
        for (int i = 0; i < pistas.size(); i++) {
            texto += (i + 1) + ". " + pistas.get(i).toString() + "\n";
        }
        return texto;
    }


    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDetective() {
        return detective;
    }

    @Override
    public String toString() {
        return "Caso: " + nombre + " | Codigo: " + codigo + " | Detective: " + detective;
    }
}
