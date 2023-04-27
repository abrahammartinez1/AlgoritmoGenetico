import java.util.Random;

public class AlgoritmoGenetico {
    private static final int LONGITUD_CROMOSOMA = 11; // 1 mas para el fitness
    private static final int ELEMENTOS_POBLACION = 10;
    private static final int MAX_ITERACIONES = 1000;
    private static final int NUM_VARIACION_GENES = 10;

    private static final int[] CODIGO_OBJETIVO = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // Función de cruzamiento
    public static int[] cruzar(int[] padre1, int[] padre2) {
        Random random = new Random();
        int puntoCorte = random.nextInt(padre1.length); // Seleccionar un punto de corte aleatorio

        // Crear hijo combinando los genes de ambos padres
        int[] hijo = new int[padre1.length];
        for (int i = 0; i < puntoCorte; i++) {
            hijo[i] = padre1[i];
        }
        for (int i = puntoCorte; i < hijo.length; i++) {
            hijo[i] = padre2[i];
        }

        return hijo;
    }

    public static int calcularFitness(int[] cromosoma) {
        Integer fitness = 10; //numero de cifras que difieren del objetivo
            for (int j = 0; j < LONGITUD_CROMOSOMA-1; j++) {
                if (cromosoma[j]==CODIGO_OBJETIVO[j]){fitness = fitness-1;};
            }
            return fitness;
    }


    // Ejemplo de uso
    public static void main(String[] args) {
        // FILAS = ELEMENTOS_POBLACION
        //COLUMNAS = LONGITUD_CROMOSOMA

        int[][] poblacion = new int[ELEMENTOS_POBLACION][LONGITUD_CROMOSOMA];

        //Crear primera generacion de poblacion de ELEMENTOS_POBLACION
        for (int i = 0; i < ELEMENTOS_POBLACION; i++) {
            for (int j = 0; j < LONGITUD_CROMOSOMA-1; j++) {
                Random rd = new Random();
                poblacion[i][j] = rd.nextInt(NUM_VARIACION_GENES);
            }
        }

        //Mostrar por pantalla los valores de la poblacion inicial
        System.out.println("POBLACION INICIAL : ");
        for ( int i = 0; i < ELEMENTOS_POBLACION; i++) {
            for (int j = 0; j < LONGITUD_CROMOSOMA; j++) {
                System.out.print(poblacion[i][j] + " ");
            }
            System.out.println();
        }

        // Definir la matriz para almacenar los resultados del fitness
        //int[] VectorConFitness = new int[ELEMENTOS_POBLACION];

        // Calcular el fitness para cada cromosoma
        for (int i = 0; i < ELEMENTOS_POBLACION  ; i++) {
             //   VectorConFitness[i] = calcularFitness(poblacion[i]);
                 poblacion[i][LONGITUD_CROMOSOMA-1] = calcularFitness(poblacion[i]);
        }

        //Mostrar por pantalla la matriz de FITNESS
       /* System.out.println("VECTOR DE FITNESS: ");
        for ( int i = 0; i < ELEMENTOS_POBLACION; i++) {
                System.out.println(VectorConFitness[i]);
        }
*/

        //ORDENAMOS LA POBLACION SEGUN SU FITNESS
        //CADA CROMOSOMA TIENE UN FITNESS Y LOS ORDENAREMOS DE MENOR FITNESS A MAYOR
        //FITNESS = NUMERO DE GENES DIFERENTES DEL OBJETIVO



        /*
        // Crear dos padres aleatorios
        int[] padre1 = new int[10];
        int[] padre2 = new int[10];
        Random random = new Random();
        for (int i = 0; i < padre1.length; i++) {
            padre1[i] = random.nextInt(100) + 1;
            padre2[i] = random.nextInt(100) + 1;
        }


        // Cruzar los padres para obtener un hijo
        int[] hijo = cruzar(padre1, padre2);

        // Imprimir los padres y el hijo resultante
        System.out.println("Padre 1: " + Arrays.toString(padre1));
        System.out.println("Padre 2: " + Arrays.toString(padre2));
        System.out.println("Hijo:    " + Arrays.toString(hijo));
        */
         
    }


}
