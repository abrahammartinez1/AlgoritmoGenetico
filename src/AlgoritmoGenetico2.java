import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class AlgoritmoGenetico {
    private static final int LONGITUD_CROMOSOMA = 11; // 1 mas para el fitness
    private static final int PORCENTAJE_ELITE_SIGUIENTE_GENERACION = 10; // 1 mas para el fitness
    private static final int ELEMENTOS_POBLACION = 100;

    private static final int MAX_ITERACIONES = 1000;
    private static final int NUM_VARIACION_GENES = 10; //CANTIDAD DE ENTEROS QUE PODRA TENER UN GEN DE 1 A X
    private static final int[] CODIGO_OBJETIVO = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static int calcularFitness(int[] cromosoma) {
        Integer fitness = 10; //numero de cifras que difieren del objetivo
            for (int j = 0; j < LONGITUD_CROMOSOMA-1; j++) {
                if (cromosoma[j]==CODIGO_OBJETIVO[j]){fitness = fitness-1;};
            }
            return fitness;
    }
    public static int[] cruzarPadreHijo(int[] padre1, int[] padre2) {
        int[] hijo = new int[LONGITUD_CROMOSOMA];
        return hijo;
    }

    public static int[][] ordenarPorUltimaColumna(int[][] arr) {
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[a.length - 1], b[b.length - 1]);
            }
        };
        Arrays.sort(arr, comparator);
        return arr;
    }

    public static void mostrarPoblacion(int[][] poblacion){
        for ( int i = 0; i < ELEMENTOS_POBLACION; i++) {
            System.out.print("CROMOSOMA " + i + "-->");
            for (int j = 0; j < LONGITUD_CROMOSOMA; j++) {
                System.out.print(poblacion[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Función para generar siguiente generación de población
    public static int[][] generarSiguienteGeneracion(int[][] poblacion) {
        // Ordenar población actual por fitness
        Arrays.sort(poblacion, Comparator.comparingInt(a -> a[LONGITUD_CROMOSOMA - 1]));

        // Seleccionar el 10% de la población con mejor fitness para el cruce
        int numSeleccionados = poblacion.length / PORCENTAJE_ELITE_SIGUIENTE_GENERACION;
        int[][] seleccionados = new int[numSeleccionados][LONGITUD_CROMOSOMA];
        for (int i = 0; i < numSeleccionados; i++) {
            seleccionados[i] = Arrays.copyOf(poblacion[i], LONGITUD_CROMOSOMA);
        }

        // Generar nueva población cruzando los seleccionados con el resto
        int[][] nuevaPoblacion = new int[ELEMENTOS_POBLACION][LONGITUD_CROMOSOMA];
        Random rnd = new Random();
        int idx = 0;
        int numElite = ELEMENTOS_POBLACION / 10;
        int numNoElite = ELEMENTOS_POBLACION - numElite;

        //recorre los individuos ELITE uno a uno
        //RECORREMOS CADA UNO DE LOS INDIV. ELITE Y LOS METEMOS EN ARRAY NUEVAPOBLACION
        for (int i = 0; i < numSeleccionados; i++) {
            int[] padre = seleccionados[i]; //
            for (int j = 0; j < LONGITUD_CROMOSOMA; j++) {
                nuevaPoblacion[idx][j] = padre[j];
            }

            for (int j = 0; j < LONGITUD_CROMOSOMA; j += 10) {
                int[] hijo = poblacion[numSeleccionados + rnd.nextInt(numNoElite)];
                for (int k = 5; k < 10; k++) {
                    nuevaPoblacion[idx][k] = hijo[k];
                }
            }
            nuevaPoblacion[idx][10] = 0; // Inicializar fitness a cero
            idx++;
        }

        //RESTO DE INDIVIDUOS NO ELITE LOS MEZCLAMOS CON INDIVIDUOS ELITE DE FORMA ALEATORIA


        // Completar la nueva población con individuos aleatorios
        while (idx < ELEMENTOS_POBLACION) { //RECORREMOS RESTO DE POBLACION

            //TOMAMOS UN CROMOSOMA ELITE AL AZAR
            int cromosomaPadre[] = seleccionados[rnd.nextInt(seleccionados.length)];
            //TOMAMOS EL CROMOSOMA HIJO A MEZCLAR CON ELITE
            int cromosomaHijo[] = poblacion[idx];
            //MEZCLAMOS PADRE E HIJO, padre tiene mayor peso que es ELITE
            int hijo[] = new int[LONGITUD_CROMOSOMA];
            int puntoCorte = LONGITUD_CROMOSOMA / 2;
            for (int j = 0; j < LONGITUD_CROMOSOMA; j++) {
                //hijo[j] = cromosomaPadre[j]+cromosomaHijo[j];
                if (j <= puntoCorte) {
                    hijo[j] = cromosomaPadre[j];
                } else {
                    hijo[j] = cromosomaHijo[j];
                }
            }
            nuevaPoblacion[idx] = hijo;
            idx = idx + 1;
        }
            return nuevaPoblacion;
        }

        public static void main (String[]args){

            int[][] poblacion = new int[ELEMENTOS_POBLACION][LONGITUD_CROMOSOMA];

            //Crear primera generacion de poblacion de ELEMENTOS_POBLACION
            for (int i = 0; i < ELEMENTOS_POBLACION; i++) {
                for (int j = 0; j < LONGITUD_CROMOSOMA - 1; j++) {
                    Random rd = new Random();
                    poblacion[i][j] = rd.nextInt(NUM_VARIACION_GENES);
                }
            }

            System.out.println("POBLACION ALEATORIA : ");
            mostrarPoblacion(poblacion);

            // Calcular el fitness para cada cromosoma
            for (int i = 0; i < ELEMENTOS_POBLACION; i++) {
                //   VectorConFitness[i] = calcularFitness(poblacion[i]);
                poblacion[i][LONGITUD_CROMOSOMA - 1] = calcularFitness(poblacion[i]);
            }

            //Mostrar por pantalla los valores de la poblacion inicial + fitness
            System.out.println("POBLACION ALEATORIA + FITNESS : ");
            mostrarPoblacion(poblacion);

            // Ordenar el array utilizando el comparador personalizado
            //poblacion = ordenarPoblacion(poblacion);
            poblacion = ordenarPorUltimaColumna(poblacion);

            //Mostrar POBLACION ORDENADA POR FITNESS
            System.out.println("POBLACION ORDENADA : ");
            mostrarPoblacion(poblacion);

            //Creamos siguiente generacion cruzando el 10% de la elite con el resto
            poblacion = generarSiguienteGeneracion(poblacion);

            //Mostrar POBLACION ORDENADA POR FITNESS
            System.out.println("SIGUIENTE GENERACION : ");

            // Calcular el fitness para cada cromosoma
            for (int i = 0; i < ELEMENTOS_POBLACION; i++) {
                //   VectorConFitness[i] = calcularFitness(poblacion[i]);
                poblacion[i][LONGITUD_CROMOSOMA - 1] = calcularFitness(poblacion[i]);
            }
            poblacion = ordenarPorUltimaColumna(poblacion);
            mostrarPoblacion(poblacion);

            //A CONTINUAR:   FALTA RELLENAR EN POBLACION EL RESTO DE INDIVIDUOS QUE NO SON ELITE

            //Mostrar POBLACION ORDENADA POR FITNESS
            //System.out.println("SIGUIENTE GENERACION : ");
            //mostrarPoblacion(poblacion);


        }
    }


