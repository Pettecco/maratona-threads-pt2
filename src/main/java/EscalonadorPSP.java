import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EscalonadorPSP implements Escalonador {
    @Override
    public Problema selecionarProblema(List<Problema> problemas) {
        if (problemas.isEmpty()) return null;
        Problema mais_pontos = Collections.max(problemas, Comparator.comparingInt(Problema::getPontos));
        problemas.remove(mais_pontos);
        return mais_pontos;
    }

    @Override
    public String toString() {
        return "PS com foco em pontos";
    }
}
