import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Juiz implements Runnable {
    private final BlockingQueue<Submissao> fila = new LinkedBlockingQueue<>();
    private final Placar placar;
    private volatile boolean encerrado = false;

    public Juiz(Placar placar) {
        this.placar = placar;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void submeter(Submissao submissao) {
        System.out.println("\033[0;35m" + "Juiz recebeu a submissão do problema " + submissao.problema.getId() + " da equipe " + submissao.equipe.getNome() + "\033[0;37m");
        fila.offer(submissao);
    }

    public void encerrar(){
        this.encerrado = true;
        fila.offer(new Submissao(null, null));
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Submissao s = fila.take();

                if (encerrado && (s.equipe == null || s.problema == null)) {
                    System.out.println("\033[0;35m" + "Juiz terminou as avaliações " + "\033[0;37m");
                    break;
                }

                if (s.equipe != null && s.problema != null) {
                    Problema p = s.problema;
                    System.out.println("\033[0;35m" + "Juiz avaliando submissão da equipe " + s.equipe.getNome() + "\033[0;37m");
                    Thread.sleep(500);
                    System.out.println("\033[0;35m" + "Equipe " + s.equipe.getNome() + " marcou! Atualizando placar" + "\033[0;37m");
                    placar.registrar(s.equipe.getNome(), p, p.getTempoResolucao());
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Juiz foi interrompido inesperadamente");
            Thread.currentThread().interrupt();
        }
    }
}
