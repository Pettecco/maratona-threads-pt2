import java.util.List;

public class EscalonadorFCFS implements Escalonador {
    @Override
    public Problema selecionarProblema(List<Problema> problemas) {
        return problemas.isEmpty() ? null : problemas.remove(0);
    }

    @Override
    public String toString() {
        return "FCFS";
    }
}
