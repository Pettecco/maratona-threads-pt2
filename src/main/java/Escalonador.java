import java.util.List;

public interface Escalonador {
    Problema selecionarProblema(List<Problema> fila);
}
