public class SalariosTest2 {
    public static final int MAX=23_000;
    public static final int UNO = 1;
    public static final char Y ='Y' , N = 'N';
    private static int suma;

    public static void main(String[] args) {
        SalariosTest2 calculo = new SalariosTest2();
        //Obtener data
        char [][] datosAleatorios = obtenerData();
        //Realizar el calculo
        calculo.calcularSalarios(datosAleatorios);
    }

    public static char[][] obtenerData(){
        char [] valores = {N, Y};
        char[][] datosAleatorios = new char[MAX][MAX];
        for (int i = 0; i < MAX; i++){
            for(int j = 0; j < MAX; j ++){
                int numRandom = (int) Math.round(Math.random()) ;
                datosAleatorios[i][j] = valores[numRandom];
            }
        }
        return datosAleatorios;
    }
    public void calcularSalarios(char[][] empleados){
        for (int i = 0; i < empleados.length; i++) {
            int total = 0;
            for (int j = 0; j < empleados.length; j++) {
                char valor = empleados[i][j];
                switch (valor) {
                    case Y:
                        total += 1;
                }
            }
            switch (total) {
                case 0:
                    total = UNO;
            }
            /*System.out.println("El salario de " + i + " es: " + total);*/
            suma += total;
        }
        System.out.println(/*"La suma total de los salarios es: " + */suma);
    }
}