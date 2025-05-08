import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EscalonadorPSD implements Escalonador {
    @Override
    public Problema selecionarProblema(List<Problema> problemas) {
        if (problemas.isEmpty()) return null;
        Problema mais_facil = Collections.min(problemas, Comparator.comparingInt(Problema::getDificuldade));
        problemas.remove(mais_facil);
        return mais_facil;
    }

    @Override
    public String toString() {
        return "PS com foco em dificuldade";
    }
}
