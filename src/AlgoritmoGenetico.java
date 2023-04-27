import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class AlgoritmoGenetico {
    private static final int LONGITUD_CROMOSOMA = 11; // 1 mas para el fitness
    private static final int PORCENTAJE_ELITE_SIGUIENTE_GENERACION = 10; // 1 mas para el fitness
    private static final int PUNTO_DE_CORTE = 50; //PORCENTAJE DONDE SE CORTA EL PADRE CON EL HIJO, NOS QUEDAMOS
    //DEL PADRE CON EL % INDICADO AQUI
    private static final int ELEMENTOS_POBLACION = 10;

    private static final int MAX_ITERACIONES = 1000;
    private static final int NUM_VARIACION_GENES = 10;
    private static final int[] CODIGO_OBJETIVO = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // Función que cruza 2 padres y devuelve el hijo
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

    public static int[][] ordenarPoblacion(int[][] poblacion) {
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[10], b[10]);
            }
        };

        // Ordenar el array utilizando el comparador personalizado
        Arrays.sort(poblacion, comparator);
        return poblacion;
    }

    public static void mostrarPoblacion(int[][] poblacion){
        for ( int i = 0; i < ELEMENTOS_POBLACION; i++) {
            for (int j = 0; j < LONGITUD_CROMOSOMA; j++) {
                System.out.print(poblacion[i][j] + " ");
            }
            System.out.println();
        }
    }

    //CRUCE
    public static int[][] SiguienteGeneracion(int[][] poblacion) {
        int numElementosElite = (LONGITUD_CROMOSOMA-1) / 10;
        int numElementosNOElite = ELEMENTOS_POBLACION - numElementosElite ;
        int puntoCorte = PUNTO_DE_CORTE;

        //RECORREMOS CADA CROMOSOMA DE LA POBLACION NO ELITE Y LO MEZCLAMOS CON UNO DE LA ELITE
        //LOS MEZCLAREMOS CON TODOS LOS DE LA ELITE QUE PODAMOS, ALTERNANDOLOS
        for (int i = numElementosNOElite; i > numElementosElite  ; i--) {
            poblacion[i][LONGITUD_CROMOSOMA-1] = calcularFitness(poblacion[i]);
        }


        return poblacion;
    }

    public static void main(String[] args) {

        int[][] poblacion = new int[ELEMENTOS_POBLACION][LONGITUD_CROMOSOMA];

        //Crear primera generacion de poblacion de ELEMENTOS_POBLACION
        for (int i = 0; i < ELEMENTOS_POBLACION; i++) {
            for (int j = 0; j < LONGITUD_CROMOSOMA-1; j++) {
                Random rd = new Random();
                poblacion[i][j] = rd.nextInt(NUM_VARIACION_GENES);
            }
        }

        // Calcular el fitness para cada cromosoma
        for (int i = 0; i < ELEMENTOS_POBLACION  ; i++) {
             //   VectorConFitness[i] = calcularFitness(poblacion[i]);
                 poblacion[i][LONGITUD_CROMOSOMA-1] = calcularFitness(poblacion[i]);
        }

        //Mostrar por pantalla los valores de la poblacion inicial + fitness
        System.out.println("POBLACION 1 : ");
        mostrarPoblacion(poblacion);

        // Ordenar el array utilizando el comparador personalizado
        poblacion = ordenarPoblacion(poblacion);

        //Mostrar POBLACION ORDENADA POR FITNESS
        System.out.println("POBLACION ORDENADA : ");
        mostrarPoblacion(poblacion);

        //Creamos siguiente generacion cruzando el 10% de la elite con el resto
        poblacion = SiguienteGeneracion(poblacion);





    }


}
