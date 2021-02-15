import java.util.*;

public class DistanciasTest2 {
    private static double MAX_DISTANCIA=10_000_000_000_000_000_000.0;
    private static int MAX_DISTANCIA_PRUEBA=12000;
    private static int MAX_AMIGOS=100;
    private static int MAX_AMIGOS_PRUEBA=50;
    private static double MAX_DISTANCIA_AMIGOS=MAX_DISTANCIA;
    private static int MAX_OTROS=2 * 1_000_000_000;
    private static int MAX_OTROS_PRUEBA=50;
    private static double MAX_DISTANCIA_OTROS= MAX_DISTANCIA_AMIGOS;

    public static void main(String[] args) {
        DistanciasTest2 distancias = new DistanciasTest2();
        double d = MAX_DISTANCIA_PRUEBA;
        List<Personas>amigos=new ArrayList();
        List<Personas>otros=new ArrayList();

        amigos = distancias.generarData(MAX_AMIGOS_PRUEBA);
        otros = distancias.generarData(MAX_OTROS_PRUEBA);

        double dt = d * 2;
        int rf = 0, m = amigos.size(), n = otros.size();

        rf = distancias.estimar(m,dt,amigos,rf,distancias,n,otros);
        System.out.println(rf);
    }

    public List<Personas> generarData(int cantidad){
        List<Personas>amigos=new ArrayList();
        for(int i = 0; i < cantidad; i++){
            Personas amigo = new Personas(i+1, Math.random()*(MAX_DISTANCIA_AMIGOS-1)+1);
            amigos.add(amigo);
        }
        for(Personas a : amigos){
            System.out.println(a.getId() + " "  +a.getDistancia());
        }
        return amigos;
    }

    public int estimar(int m, double dt, List<Personas> amigos,int rf, DistanciasTest2 distancias, int n, List<Personas> otros){
        if(m > 0){
            //Estimar si solo un mejor amigo puede hacer el recorrido
            for(int i = 0; i < m; i++ ){
                if(dt <= amigos.get(i).getDistancia()){//La distancia total es menor o igual a la distancia que puede recorrer el amigo(i)
                    rf = 1;
                    break;
                }
            }
            //Estimar si solo los mejores amigos pueden completar el recorrido
            if(rf == 0 && m > 1){//Ningún amigo puede hacer el recorrido solo
                rf = distancias.estimarGrupo(distancias, amigos, dt, rf);
                if(rf > 0){//El número de personas es mayor a 0?
                    rf = 0;
                }
            }

            if(rf <= 0){
                //Estimar si entre amigos y otros pueden hacer el recorrido
                if( n > 0){//Existen otros disponibles?
                    distancias.ordenar(otros); //Ordenar de menor a mayor
                    rf = distancias.estimarGrupos(n, otros, dt, distancias, rf, amigos);
                }else{
                    //Los amigos no pueden completar el recorrido y no tienen otros que ayuden
                    rf = -1;
                }
            }
        }//Existen amigos disponibles?
        else if(n > 0){
            //Estimar si pueden hacer el recorrido solo entre los otros
            rf = distancias.estimarGrupo(distancias, otros, dt, rf);
            if(rf <= 0){//No pueden hacer el recorrido solo entre los otros?
                rf = -1;
            }
        }//Existen otros?
        else{
            rf = -1;
        }//No tiene ni mejores amigos ni otros disponibles?
        return rf;
    }

    public List<Personas> ordenar(List<Personas> lista){
        Comparator<Personas> comparator = Comparator.comparingDouble(Personas::getDistancia);
        /*System.out.println("\nOrden ascendente:");
        for (Personas persona : lista) {
            System.out.println(persona.getDistancia());
        }*/
        return lista;
    }

    public double calcularDistancia(double dt, double distancia){
        double dp;
        dp = dt - distancia; //Calcular la distancia pendiente o faltante
        dt = dp * 2; //La distancia pendiente o faltante ahora será la distancia total para el resto de personas
        return dt; //Retornar la distantica total actual
    }

    public int estimarGrupo(DistanciasTest2 distancias, List<Personas> personas, double dt, int rf){
        double d = dt / 2;
        distancias.ordenar(personas); //Orden ascendente
        for(int i = 0; i < personas.size(); i++ ){
            if(personas.get(i).getDistancia() > d){//la distancia que puede recorrer es mayor a la distancia de ida?
                dt = distancias.calcularDistancia(dt, personas.get(i).getDistancia());
                if(dt <= 0){//Ya no hay distancia que recorrer?
                    rf += i + 1; //Sumarle al número de personas, la cantidad encontrada de personas que puden ayudar
                    break;
                }else if(i == personas.size()-1 && dt > 0){
                    rf = -1;
                }
                else{//Aún falta recorrer y hay personas disponibles que pueden ayudar
                    continue;
                }
            }else if(i == personas.size()-1 && dt > 0){//Si es la última persona y aún hay distancia que recorrer
                rf = -1;
            }
            else{//No puede recorrer la distancia de ida y aún faltan validar personas
                continue;
            }
        }
        return rf;//Retornar el numero de personas que lograron recorrer la distancia
    }

    public int estimarGrupos(int n,List<Personas> otros, double dt, DistanciasTest2 distancias, int rf, List<Personas> amigos){
        for(int i = 0; i < n; i++){
            if(otros.get(i).getDistancia() > dt/2){//El otro puede recorrer más de la distancia de ida?
                //Calcular la distancia que faltaría recorrer
                dt = distancias.calcularDistancia(dt, otros.get(i).getDistancia());
                rf = i + 1;
                //Estimar si entre los amigos pueden recorrer la distancia faltante
                rf = distancias.estimarGrupo(distancias, amigos, dt, rf);
                if(rf > 0){//Se encontraron personas que terminen de recorrer la distancia?
                    break;
                }else if(i == n -1 && rf <= 0 ){ //Es el último validado y el rf sigue menor o igual a 0?
                    rf = -1;
                }else{//El rf no es mayor a 0 y se debe calcular nuevamente con el siguiente
                    continue;
                }
            }else if(i == n -1 && rf <= 0){//Es el último validado, no puede recorrer la distancia de ida y el rf sigue menor o igual a 0?
                rf = -1;
            }else{//El otro no puede recorrer la distancia de ida, sigue el rf menor o igual a 0 y aún falta calcular con otros?
                continue;
            }
        }
        return rf;
    }
}
