# T6 - Implementación de un árbol binario en Java

**Estudiante:** Kleber Moyota  
**Universidad:** Universidad Técnica de Ambato  
**Carrera:** Ingeniería de Software  
**Asignatura:** Estructura de Datos  
**Nivel:** Tercero  
**Modalidad:** Individual

## Descripción del problema

La práctica representa una organización de dependencias universitarias mediante un árbol binario genérico. Cada nodo almacena el nombre de una dependencia y puede tener como máximo dos hijos.

La aplicación permite construir el árbol inicial, visualizarlo, contar sus nodos y hojas, calcular su altura y consultar el grado o la condición de hoja de un nodo. Es un árbol de jerarquía, no un árbol binario de búsqueda: los nombres no se insertan siguiendo un orden alfabético.

## Estructura del árbol

```text
             UNIVERSIDAD
             /         \
       FISEI             DIDE
       /   \             /   \
   SOFTWARE  TI     INVEST.  VINCULACIÓN
    /   \
  NIVEL1 NIVEL3
```

El árbol tiene **9 nodos, 5 hojas y una altura de 3 aristas**. Las hojas son NIVEL1, NIVEL3, TI, INVEST. y VINCULACIÓN.

## Organización del código

| Archivo | Responsabilidad |
|---|---|
| `src/modelo/Nodo.java` | Clase genérica `Nodo<T>`, dato, referencias a hijos, getters/setters, `esHoja()` y `grado()`. |
| `src/negocio/ArbolBinario.java` | Raíz, inserciones validadas, búsqueda, visualización y operaciones recursivas. |
| `src/app/Main.java` | Construcción del caso universitario, menú con `Scanner` y pruebas. |

Las operaciones `contarNodos()`, `contarHojas()` y `altura()` se implementan con métodos auxiliares recursivos. La altura del árbol vacío es -1 y la de una raíz sin hijos es 0.

No se utilizan `TreeSet`, `TreeMap`, `PriorityQueue`, `NavigableSet` ni `NavigableMap`.

## Compilación y ejecución en Visual Studio Code

1. Tener un JDK instalado. Comprobar en la terminal que `java -version` y `javac -version` funcionan.
2. Abrir en VS Code la carpeta del repositorio, donde están `src` y este README.
3. Abrir **Terminal > Nueva terminal**. En PowerShell de Windows, configurar UTF-8 para mostrar correctamente las tildes:

```powershell
chcp 65001
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new()
```

4. Compilar desde la raíz del proyecto:

```powershell
javac -encoding UTF-8 -d bin src/modelo/Nodo.java src/negocio/ArbolBinario.java src/app/Main.java
```

5. Ejecutar:

```powershell
java "-Dfile.encoding=UTF-8" -cp bin app.Main
```

La carpeta `bin` contiene los archivos compilados y se genera al compilar. No se necesitan librerías externas.

## Menú de opciones

| Opción | Función |
|---|---|
| 1 | Crear o cargar el árbol inicial. |
| 2 | Visualizar el árbol. `[I]` indica hijo izquierdo y `[D]` hijo derecho. |
| 3 | Consultar el total de nodos. |
| 4 | Consultar el total de hojas. |
| 5 | Consultar la altura en aristas. |
| 6 | Consultar el grado de un nodo por su nombre. |
| 7 | Verificar si un nodo es hoja. |
| 8 | Salir. |
| 9 | Ejecutar los casos de prueba sobre árboles independientes. |

Se conserva la opción 8 para salir, según la guía del aula. La opción 9 permite demostrar los casos de prueba sin modificar el árbol que se consulta desde el menú.

## Casos de prueba y resultados

| Caso | Cómo comprobarlo | Resultado obtenido |
|---|---|---|
| Árbol vacío | Opciones 3, 4 y 5 antes de cargar, o la opción 9. | 0 nodos, 0 hojas y altura -1. |
| Raíz única | Opción 9, resultados de «Raiz unica». | 1 nodo, 1 hoja, altura 0 y grado 0. |
| Árbol inicial | Opción 1 y luego opciones 3, 4 y 5. | 9 nodos, 5 hojas y altura 3. |
| Grado de FISEI | Opción 6 e ingresar `FISEI`. | 2 hijos. |
| Grado de SOFTWARE | Opción 6 e ingresar `SOFTWARE`. | 2 hijos. |
| Verificación de hoja | Opción 7 e ingresar `NIVEL1`. | Es hoja: SI. |
| Posición izquierda ocupada | Opción 9. | Se rechaza la inserción y se conserva el hijo original. |
| Posición derecha ocupada | Opción 9. | Se rechaza la inserción y se conserva el hijo original. |

La opción 9 muestra `[OK]` cuando el resultado obtenido coincide con el esperado. Los mensajes «Rechazo esperado» corresponden a las validaciones de posiciones ocupadas y no a un fallo del programa. Después de esos intentos, el árbol de prueba conserva sus nueve nodos.
