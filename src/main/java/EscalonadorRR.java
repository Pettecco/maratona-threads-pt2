import java.util.*;

public class EscalonadorRR implements Escalonador{
    private final Queue<Problema> fila;
    private final int quantum = 3000;
    public EscalonadorRR(List<Problema> problemas) {
        this.fila = new LinkedList<>(problemas);
    }

    @Override
    public Problema selecionarProblema(List<Problema> problemas) {
        if (fila.isEmpty()) return null;

        Problema problema = fila.poll();
        System.out.println("\033[0;96m" + "Problema atual: " + problema.getId() + "\033[0;37m");
        try {
            System.out.println("\033[0;96m" + "simulação da resolução..." + "\033[0;37m");
            Thread.sleep(Math.min(quantum, problema.getTempoRestante()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        problema.reduzirTempo(quantum);
        System.out.println("\033[0;96m" + "Tempo do problema reduzido" + "\033[0;37m");
        System.out.println("\033[0;96m" + "Tempo inicial: " + problema.getTempoResolucao() + "\033[0;37m");
        System.out.println("\033[0;96m" + "Tempo restante: " + problema.getTempoRestante() + "\033[0;37m");
        if (problema.estaConcluido()) {
            System.out.println("\033[0;96m" + "Problema " + problema.getId() + " foi resolvido" + "\033[0;37m");
            return problema;
        } else {
            System.out.println("\033[0;96m" + "Problema " + problema.getId() + " voltou para fila" + "\033[0;37m");
            fila.offer(problema); // ainda não terminou, volta pro fim da fila
            return null; // não retorna submissão ainda
        }
    }

    public String toString() {
        return "RR";
    }
}
