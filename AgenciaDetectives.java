import java.util.InputMismatchException;
import java.util.Scanner;

public class AgenciaDetectives {

    private static Scanner scanner = new Scanner(System.in);
    private static Caso caso;

    public static void main(String[] args) {
        System.out.println("===== AGENCIA DE DETECTIVES - CASO MISTERIOSO =====");
        caso = crearCaso();

        int opcion = 0;
        try {
            while (opcion != 13) {
                mostrarMenu();
                opcion = leerEntero("Seleccione una opcion: ");

                try {
                    switch (opcion) {
                        case 1:
                            caso = crearCaso();
                            break;
                        case 2:
                            registrarUbicacion();
                            break;
                        case 3:
                            System.out.println(caso.listarUbicaciones());
                            break;
                        case 4:
                            consultarUbicacion();
                            break;
                        case 5:
                            modificarUbicacion();
                            break;
                        case 6:
                            descartarUbicacion();
                            break;
                        case 7:
                            registrarPista();
                            break;
                        case 8:
                            System.out.println(caso.listarPistas());
                            break;
                        case 9:
                            buscarPista();
                            break;
                        case 10:
                            modificarPista();
                            break;
                        case 11:
                            eliminarPista();
                            break;
                        case 12:
                            mostrarReporte();
                            break;
                        case 13:
                            System.out.println("Saliendo del sistema...");
                            break;
                        default:
                            System.out.println("Opcion invalida. Ingrese un numero del 1 al 13.");
                    }
                } catch (IllegalArgumentException e) {
                
                    System.out.println("Error: " + e.getMessage());
                } catch (IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } finally {
        
            scanner.close();
            System.out.println("Programa finalizado.");
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("---------------- MENU ----------------");
        System.out.println(caso.toString());
        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicacion");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicacion");
        System.out.println("5. Modificar ubicacion");
        System.out.println("6. Descartar ubicacion");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");
        System.out.println("12. Mostrar reporte de investigacion");
        System.out.println("13. Salir");
    }


    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero.");
            } finally {
                scanner.nextLine(); 
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerPosicion() {
        return leerEntero("Ingrese la posicion (0 a " + (Caso.MAX_UBICACIONES - 1) + "): ");
    }


    private static Caso crearCaso() {
        while (true) {
            try {
                System.out.println("\n--- Informacion del caso ---");
                String nombre = leerTexto("Nombre del caso: ");
                String codigo = leerTexto("Codigo de identificacion: ");
                String detective = leerTexto("Detective responsable: ");
                Caso nuevo = new Caso(nombre, codigo, detective);
                System.out.println("Caso creado sin ubicaciones ni pistas.");
                return nuevo;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.");
            }
        }
    }

    private static void registrarUbicacion() {
        if (caso.contarEspaciosDisponibles() == 0) {
            System.out.println("No hay espacios disponibles. Descarte una ubicacion primero.");
            return;
        }
        int posicion = leerPosicion();
 
        if (!caso.posicionDisponible(posicion)) {
            System.out.println("La posicion " + posicion + " ya esta ocupada.");
            return;
        }
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        String direccion = leerTexto("Direccion o descripcion: ");
        int riesgo = leerEntero("Nivel de riesgo (1-10): ");
        String estado = leerTexto("Estado (ej. Pendiente, En investigacion, Investigada): ");

        Ubicacion ubicacion = new Ubicacion(codigo, nombre, direccion, riesgo, estado);
        caso.registrarUbicacion(posicion, ubicacion);
        System.out.println("Ubicacion registrada en la posicion " + posicion + ".");
    }

    private static void consultarUbicacion() {
        int posicion = leerPosicion();
        Ubicacion ubicacion = caso.obtenerUbicacion(posicion);
        System.out.println("[" + posicion + "] " + ubicacion);
    }

    private static void modificarUbicacion() {
        int posicion = leerPosicion();
        Ubicacion ubicacion = caso.obtenerUbicacion(posicion);
        System.out.println("Actual: " + ubicacion);
        int riesgo = leerEntero("Nuevo nivel de riesgo (1-10): ");
        String estado = leerTexto("Nuevo estado: ");
        caso.modificarUbicacion(posicion, riesgo, estado);
        System.out.println("Ubicacion modificada.");
    }

    private static void descartarUbicacion() {
        int posicion = leerPosicion();
        Ubicacion eliminada = caso.descartarUbicacion(posicion);
        System.out.println("Ubicacion " + eliminada.getCodigo() + " descartada. Posicion "
                + posicion + " disponible.");
    }

    private static void registrarPista() {
        String codigo = leerTexto("Codigo: ");
        if (caso.buscarPista(codigo) != null) {
            System.out.println("Ya existe una pista con ese codigo.");
            return;
        }
        String descripcion = leerTexto("Descripcion: ");
        String tipo = leerTexto("Tipo de evidencia: ");
        int importancia = leerEntero("Nivel de importancia (1-10): ");
        int confiabilidad = leerEntero("Nivel de confiabilidad (0-100): ");

        Pista pista = new Pista(codigo, descripcion, tipo, importancia, confiabilidad);
        caso.registrarPista(pista);
        System.out.println("Pista registrada.");
    }

    private static void buscarPista() {
        if (!caso.hayPistas()) {
            System.out.println("Todavia no hay pistas registradas.");
            return;
        }
        String codigo = leerTexto("Codigo de la pista: ");
        Pista pista = caso.buscarPista(codigo);
        if (pista == null) {
            System.out.println("No se encontro una pista con ese codigo.");
        } else {
            System.out.println(pista);
        }
    }

    private static void modificarPista() {
        if (!caso.hayPistas()) {
            System.out.println("Todavia no hay pistas registradas.");
            return;
        }
        String codigo = leerTexto("Codigo de la pista a modificar: ");
        Pista pista = caso.buscarPista(codigo);
        if (pista == null) {
            System.out.println("No se encontro una pista con ese codigo.");
            return;
        }
        System.out.println("Actual: " + pista);
        String descripcion = leerTexto("Nueva descripcion: ");
        String tipo = leerTexto("Nuevo tipo de evidencia: ");
        int importancia = leerEntero("Nuevo nivel de importancia (1-10): ");
        int confiabilidad = leerEntero("Nuevo nivel de confiabilidad (0-100): ");
        caso.modificarPista(codigo, descripcion, tipo, importancia, confiabilidad);
        System.out.println("Pista modificada.");
    }

    private static void eliminarPista() {
        if (!caso.hayPistas()) {
            System.out.println("Todavia no hay pistas registradas.");
            return;
        }
        String codigo = leerTexto("Codigo de la pista a eliminar: ");
        Pista eliminada = caso.eliminarPista(codigo);
        System.out.println("Pista " + eliminada.getCodigo() + " eliminada.");
    }

    private static void mostrarReporte() {
        System.out.println("\n===== REPORTE DE INVESTIGACION =====");
        System.out.println(caso);
        System.out.println("Ubicaciones registradas: " + caso.contarUbicaciones());
        System.out.println("Espacios disponibles: " + caso.contarEspaciosDisponibles());

        Ubicacion mayorRiesgo = caso.ubicacionMayorRiesgo();
        if (mayorRiesgo == null) {
            System.out.println("Ubicacion con mayor riesgo: no hay ubicaciones registradas.");
        } else {
            System.out.println("Ubicacion con mayor riesgo: " + mayorRiesgo);
        }

        System.out.println("Pistas registradas: " + caso.contarPistas());
   
        if (caso.hayPistas()) {
            System.out.println("Pista con mayor importancia: " + caso.pistaMayorImportancia());
            System.out.println("Pista con mayor confiabilidad: " + caso.pistaMayorConfiabilidad());
            System.out.printf("Promedio de importancia: %.2f%n", caso.promedioImportancia());
        } else {
            System.out.println("No hay pistas registradas, no se pueden calcular estadisticas de pistas.");
        }
    }
}
