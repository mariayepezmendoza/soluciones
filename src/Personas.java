import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public class Personas{
    int id;
    double distancia;
    public Personas() {}

    public Personas(int id, double distancia) {
        this.id = id;
        this.distancia = distancia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
