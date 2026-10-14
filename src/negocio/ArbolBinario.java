package negocio;

import java.util.Objects;
import modelo.Nodo;

/**
 * Arbol binario generico, no ordenado: no es un arbol binario de busqueda.
 * Las inserciones se realizan mediante esta clase para validar la estructura.
 */
public class ArbolBinario<T> {
    private Nodo<T> raiz;

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public Nodo<T> crearRaiz(T dato) {
        if (!esVacio()) {
            throw new IllegalStateException("El arbol ya tiene una raiz.");
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("El padre ya tiene hijo izquierdo.");
        }
        Nodo<T> hijo = new Nodo<>(dato);
        padre.setIzquierdo(hijo);
        return hijo;
    }

    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("El padre ya tiene hijo derecho.");
        }
        Nodo<T> hijo = new Nodo<>(dato);
        padre.setDerecho(hijo);
        return hijo;
    }

    private void validarPadre(Nodo<T> padre) {
        if (padre == null) {
            throw new IllegalArgumentException("El padre no puede ser null.");
        }
        if (!contieneReferencia(raiz, padre)) {
            throw new IllegalArgumentException("El padre no pertenece a este arbol.");
        }
    }

    private boolean contieneReferencia(Nodo<T> actual, Nodo<T> buscado) {
        if (actual == null) {
            return false;
        }
        return actual == buscado || contieneReferencia(actual.getIzquierdo(), buscado) || contieneReferencia(actual.getDerecho(), buscado);
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo())
                + contarNodos(nodo.getDerecho());
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo())
                + contarHojas(nodo.getDerecho());
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    /** Devuelve la primera coincidencia en preorden, o null si no existe. */
    public Nodo<T> buscarNodo(T dato) {
        return buscarNodo(raiz, dato);
    }

    private Nodo<T> buscarNodo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        if (Objects.equals(nodo.getDato(), dato)) {
            return nodo;
        }
        Nodo<T> encontrado = buscarNodo(nodo.getIzquierdo(), dato);
        if (encontrado != null) {
            return encontrado;
        }
        return buscarNodo(nodo.getDerecho(), dato);
    }

    public void mostrarArbol() {
        if (esVacio()) {
            System.out.println("(arbol vacio)");
            return;
        }
        System.out.println(raiz.getDato());
        mostrarHijos(raiz, "");
    }

    private void mostrarHijos(Nodo<T> padre, String prefijo) {
        if (padre.getIzquierdo() != null) {
            mostrarRama(padre.getIzquierdo(), prefijo, "I",
                    padre.getDerecho() == null);
        }
        if (padre.getDerecho() != null) {
            mostrarRama(padre.getDerecho(), prefijo, "D", true);
        }
    }

    private void mostrarRama(Nodo<T> nodo, String prefijo, String lado,
            boolean ultimo) {
        System.out.println(prefijo + (ultimo ? "\\-- " : "+-- ")
                + "[" + lado + "] " + nodo.getDato());
        mostrarHijos(nodo, prefijo + (ultimo ? "    " : "|   "));
    }
}
