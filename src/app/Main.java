package app;

import java.util.Locale;
import java.util.Scanner;
import modelo.Nodo;
import negocio.ArbolBinario;

/** Interaccion por consola y construccion del caso universitario. */
public class Main {
    public static void main(String[] args) {
        ArbolBinario<String> arbol = new ArbolBinario<>();
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                if (!scanner.hasNextLine()) {
                    System.out.println("Fin de la entrada.");
                    break;
                }
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        // Se crea otra instancia: nunca se sobrescribe la raiz existente.
                        arbol = crearArbolUniversitario();
                        System.out.println("Arbol inicial cargado: 9 nodos, 5 hojas, altura 3.");
                        break;
                    case "2":
                        arbol.mostrarArbol();
                        break;
                    case "3":
                        System.out.println("Total de nodos: " + arbol.contarNodos());
                        break;
                    case "4":
                        System.out.println("Total de hojas: " + arbol.contarHojas());
                        break;
                    case "5":
                        System.out.println("Altura (aristas): " + arbol.altura());
                        break;
                    case "6":
                        consultarNodo(scanner, arbol, true);
                        break;
                    case "7":
                        consultarNodo(scanner, arbol, false);
                        break;
                    case "8":
                        continuar = false;
                        System.out.println("Programa finalizado.");
                        break;
                    case "9":
                        probarCasosEspeciales();
                        break;
                    default:
                        System.out.println("Opcion invalida. Ingrese un numero del 1 al 9.");
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== T6 - ARBOL BINARIO UNIVERSITARIO ===");
        System.out.println("1. Crear/cargar arbol inicial");
        System.out.println("2. Visualizar arbol");
        System.out.println("3. Consultar total de nodos");
        System.out.println("4. Consultar total de hojas");
        System.out.println("5. Consultar altura");
        System.out.println("6. Consultar grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Salir");
        System.out.println("9. Probar casos especiales (adicional)");
        System.out.print("Opcion: ");
    }

    private static ArbolBinario<String> crearArbolUniversitario() {
        ArbolBinario<String> arbol = new ArbolBinario<>();
        Nodo<String> universidad = arbol.crearRaiz("UNIVERSIDAD");
        Nodo<String> fisei = arbol.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = arbol.agregarDerecho(universidad, "DIDE");
        Nodo<String> software = arbol.agregarIzquierdo(fisei, "SOFTWARE");
        arbol.agregarDerecho(fisei, "TI");
        arbol.agregarIzquierdo(dide, "INVEST.");
        arbol.agregarDerecho(dide, "VINCULACIÓN");
        arbol.agregarIzquierdo(software, "NIVEL1");
        arbol.agregarDerecho(software, "NIVEL3");
        return arbol;
    }

    private static void consultarNodo(Scanner scanner, ArbolBinario<String> arbol,
            boolean consultarGrado) {
        if (arbol.esVacio()) {
            System.out.println("El arbol esta vacio. Cargue el arbol con la opcion 1.");
            return;
        }
        System.out.print("Nombre de la dependencia (con tildes y punto si corresponde): ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String nombre = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
        Nodo<String> nodo = arbol.buscarNodo(nombre);
        if (nodo == null) {
            System.out.println("No existe un nodo con el nombre: " + nombre);
        } else if (consultarGrado) {
            System.out.println("Grado de " + nodo.getDato() + ": " + nodo.grado());
        } else {
            System.out.println(nodo.getDato() + " es hoja: " + (nodo.esHoja() ? "SI" : "NO"));
        }
    }

    private static void probarCasosEspeciales() {
        System.out.println("\n=== PRUEBAS SOBRE ARBOLES INDEPENDIENTES ===");
        ArbolBinario<String> vacio = new ArbolBinario<>();
        verificar("Vacio: esVacio", true, vacio.esVacio());
        verificar("Vacio: nodos", 0, vacio.contarNodos());
        verificar("Vacio: hojas", 0, vacio.contarHojas());
        verificar("Vacio: altura", -1, vacio.altura());

        ArbolBinario<String> unico = new ArbolBinario<>();
        Nodo<String> raizUnica = unico.crearRaiz("UNIVERSIDAD");
        verificar("Raiz unica: nodos", 1, unico.contarNodos());
        verificar("Raiz unica: hojas", 1, unico.contarHojas());
        verificar("Raiz unica: altura", 0, unico.altura());
        verificar("Raiz unica: grado", 0, raizUnica.grado());
        verificar("Raiz unica: esHoja", true, raizUnica.esHoja());

        ArbolBinario<String> inicial = crearArbolUniversitario();
        verificar("Inicial: nodos", 9, inicial.contarNodos());
        verificar("Inicial: hojas", 5, inicial.contarHojas());
        verificar("Inicial: altura", 3, inicial.altura());
        Nodo<String> fisei = inicial.buscarNodo("FISEI");
        Nodo<String> software = inicial.buscarNodo("SOFTWARE");
        verificar("Grado FISEI", 2, fisei.grado());
        verificar("Grado SOFTWARE", 2, software.grado());
        verificar("SOFTWARE es hoja", false, software.esHoja());
        verificar("NIVEL1 es hoja", true, inicial.buscarNodo("NIVEL1").esHoja());

        Nodo<String> izquierdoOriginal = fisei.getIzquierdo();
        Nodo<String> derechoOriginal = fisei.getDerecho();
        boolean rechazoIzquierdo = false;
        try {
            inicial.agregarIzquierdo(fisei, "REEMPLAZO");
        } catch (IllegalStateException e) {
            rechazoIzquierdo = true;
            System.out.println("Rechazo esperado: " + e.getMessage());
        }
        verificar("Rechaza sobreescritura izquierda", true, rechazoIzquierdo);
        verificar("Conserva hijo izquierdo", true, fisei.getIzquierdo() == izquierdoOriginal);

        boolean rechazoDerecho = false;
        try {
            inicial.agregarDerecho(fisei, "REEMPLAZO");
        } catch (IllegalStateException e) {
            rechazoDerecho = true;
            System.out.println("Rechazo esperado: " + e.getMessage());
        }
        verificar("Rechaza sobreescritura derecha", true, rechazoDerecho);
        verificar("Conserva hijo derecho", true, fisei.getDerecho() == derechoOriginal);
        verificar("Nodos tras rechazos", 9, inicial.contarNodos());

        ArbolBinario<Integer> cadena = new ArbolBinario<>();
        Nodo<Integer> primero = cadena.crearRaiz(10);
        Nodo<Integer> segundo = cadena.agregarDerecho(primero, 20);
        cadena.agregarDerecho(segundo, 30);
        verificar("Cadena Integer: grado de raiz", 1, primero.grado());
        verificar("Cadena Integer: hojas", 1, cadena.contarHojas());
        verificar("Cadena Integer: altura", 2, cadena.altura());
        verificar("Busqueda inexistente", true, inicial.buscarNodo("OTRO") == null);
        System.out.println("FIN DE PRUEBAS. El arbol del menu no fue modificado.");
    }

    private static void verificar(String caso, Object esperado, Object obtenido) {
        boolean correcto = esperado.equals(obtenido);
        System.out.println((correcto ? "[OK] " : "[ERROR] ") + caso
                + " | esperado=" + esperado + " | obtenido=" + obtenido);
    }
}
