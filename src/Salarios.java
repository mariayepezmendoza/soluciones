import java.util.Random;

public class Salarios {
    public static final int UNO = 1/*, CERO = 0*/;
    public static final char Y = 'Y', N = 'N';
    private static int suma;

    public static void main(String[] args) {
        char empleados[][] = {{N, N, Y, N}, {N, N, Y, N}, {N, N, N, N}, {N, Y, Y, N}};
        /*int Salario[][];*/
        Salarios calculo = new Salarios();
        suma = calculo.calcularSalario(empleados.length,empleados);
        System.out.println("La suma total de los salarios es: " + suma);
    }

    public int calcularSalario(int cantidad, char[][] empleados) {
        for (int i = 0; i < cantidad; i++) {
            int /*salario = 0,*/ total = 0;
            /*Salario = new int [cantidad][cantidad];*/
            for (int j = 0; j < cantidad; j++) {
                char valor = empleados[i][j];
                switch (valor) {
                    case Y:
                        //salario = UNO;
                        total += 1;
                        /*break;
                    default:
                        salario = CERO;
                        break;*/
                }
                /*Salario[i][j] = salario;
                System.out.print(Salario[i][j]);
                total += salario;*/
            }
            switch (total) {
                case 0:
                    total = UNO;
                /*break;
                default:
                    break;*/
            }
            System.out.println("     El salario de " + i + " es: " + total);
            suma += total;
        }
        return suma;
    }
}