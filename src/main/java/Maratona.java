import java.util.ArrayList;
import java.util.List;

public class Maratona {
    private final List<Equipe> equipes;
    private final Juiz juiz;
    private final Placar placar;
    private final int duracaoSegundos;

    public Maratona(List<Equipe> equipes, Juiz juiz, Placar placar, int duracaoSegundos) {
        this.equipes = equipes;
        this.juiz = juiz;
        this.placar = placar;
        this.duracaoSegundos = duracaoSegundos;
    }

    public void iniciar() throws InterruptedException {
        System.out.println("\033[1;97m" + "Inicializando maratona..." + "\033[0;37m");
        Thread juizThread = new Thread(juiz);
        juizThread.start();

        System.out.println("\033[1;97m" + "Preparando equipes..." + "\033[0;37m");
        List<Thread> equipeThreads = new ArrayList<>();
        for (Equipe e : equipes) {
            Thread t = new Thread(e);
            equipeThreads.add(t);
            t.start();
        }

        System.out.println("\033[1;97m" + "Começou!" + "\033[0;37m");
        Thread.sleep(duracaoSegundos * 1000L);

        System.out.println("\033[1;97m" + "Tempo esgotado! Equipes não podem mais submeter. Verificando submissões..." + "\033[0;37m");
        for (Thread t : equipeThreads) t.interrupt();
        for (Thread t : equipeThreads) t.join();

        juiz.encerrar();
        juizThread.join();

        System.out.println("Maratona finalizada! Imprimindo placar final" + "\033[0;37m");
        placar.imprimir();
    }
}
