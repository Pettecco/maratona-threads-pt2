import java.util.concurrent.ConcurrentHashMap;

public class Placar {
    private final ConcurrentHashMap<String, EstatisticasEquipe> placar = new ConcurrentHashMap<>();

    public synchronized void registrar(String nome_equipe, Problema problema, long tempo_gasto) {
        placar.putIfAbsent(nome_equipe, new EstatisticasEquipe());
        EstatisticasEquipe estat = placar.get(nome_equipe);
        estat.pontos += problema.getPontos();
        estat.problemas_resolvidos++;
        estat.tempo_total_resolucao += tempo_gasto;
        System.out.println("\033[1;33m" + nome_equipe + " ganhou " + problema.getPontos() + "!" + "\033[0;37m");
    }

    public void imprimir() {
        System.out.println("<----------------------->");
        System.out.println("\033[1;96m" + "      Placar Final       ");
        System.out.println("<----------------------->");
        placar.forEach((nome, estat) -> {
            System.out.println("Equipe: " + nome);
            System.out.println(estat.toString());
        });
    }
}
