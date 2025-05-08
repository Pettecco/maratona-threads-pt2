import java.util.Random;

public class Problema {
    private int id;
    private int dificuldade;
    private int tempo_resolucao_ms;
    private int pontos;
    private int tempo_restante;
    private Random random = new Random();

    public Problema(int id){
        this.id = id;
        this.dificuldade = random.nextInt(1,6);
        this.tempo_resolucao_ms = random.nextInt(1,6) * 1000;
        this.pontos = random.nextInt(1,6) * 10;
        this.tempo_restante = tempo_resolucao_ms;
    }

    public int getId() { return id; }
    public int getDificuldade() { return dificuldade; }
    public int getTempoResolucao() { return tempo_resolucao_ms; }
    public int getPontos() { return pontos; }
    public int getTempoRestante() {
        return tempo_restante;
    }
    public void reduzirTempo(int quantum) {
        this.tempo_restante = Math.max(0, this.tempo_restante - quantum);
    }
    public boolean estaConcluido() {
        return this.tempo_restante <= 0;
    }

    @Override
    public String toString() {
        return "Problema: " +
                "id=" + id +
                ", dificuldade=" + dificuldade +
                ", tempo_resolucao_ms=" + tempo_resolucao_ms +
                ", pontos=" + pontos;
    }
}
