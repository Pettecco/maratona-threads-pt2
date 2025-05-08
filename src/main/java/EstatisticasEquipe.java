public class EstatisticasEquipe {
    public int pontos = 0;
    public int problemas_resolvidos = 0;
    public long tempo_total_resolucao = 0;

    @Override
    public String toString() {
        return "pontos=" + pontos +
                ", problemas_resolvidos=" + problemas_resolvidos +
                ", tempo_total_resolucao=" + tempo_total_resolucao;
    }
}
