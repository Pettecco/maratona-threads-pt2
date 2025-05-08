import java.util.ArrayList;
import java.util.List;

public class Equipe implements Runnable {
    private final String nome;
    private final Escalonador escalonador;
    private final Juiz juiz;
    private final List<Problema> problemas;

    public Equipe(String nome, Escalonador escalonador, Juiz juiz, List<Problema> problemas) {
        this.nome = nome;
        this.escalonador = escalonador;
        this.juiz = juiz;
        this.problemas = new ArrayList<>(problemas);
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Equipe " + nome + " usará o escalonador " + escalonador.toString();
    }

    @Override
    public void run() {
        while (!problemas.isEmpty() && !Thread.currentThread().isInterrupted()) {
            Problema p = escalonador.selecionarProblema(problemas);
            if (p != null) {
                try {
                    System.out.println("\033[0;92m" + "Equipe " + this.nome + " começou a resolver o problema " + p.getId() + "\033[0;37m");
                    Thread.sleep(p.getTempoResolucao());
                    System.out.println("\033[0;92m" + "Equipe " + this.nome + " submeteu o problema " + p.getId() + " ao juíz" + "\033[0;37m");
                    juiz.submeter(new Submissao(this, p));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Equipe " + this.nome + " não pode mais submeter");
                    break;
                }
            }
        }
    }
}