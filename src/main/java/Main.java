import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("<----------------------->");
        System.out.println("   Iniciando problemas   ");
        System.out.println("<----------------------->");
        List<Problema> problemas = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Problema problema = new Problema(i);
            System.out.println(problema);
            problemas.add(problema);
        }

        System.out.println("<----------------------->");
        System.out.println(" Iniciando Placar e Juiz ");
        System.out.println("<----------------------->");
        Placar placar = new Placar();
        Juiz juiz = new Juiz(placar);

        System.out.println("<----------------------->");
        System.out.println("   Iniciando equipes     ");
        System.out.println("<----------------------->");
        Escalonador escalonadorfcfs = new EscalonadorFCFS(); //First Come, First Served
        Escalonador escalonadorpsd = new EscalonadorPSD(); //Priority Scheduling Dificulty
        Escalonador escalonadorpsp = new EscalonadorPSP(); //Priority Scheduling Points
        Escalonador escalonadorrr = new EscalonadorRR(problemas); //Round Robin
        List<Equipe> equipes = new ArrayList<>();
        equipes.add(new Equipe(("Equipe Alpha"), escalonadorfcfs, juiz, problemas));
        equipes.add(new Equipe(("Equipe Beta"), escalonadorpsd, juiz, problemas));
        equipes.add(new Equipe(("Equipe Gama"), escalonadorpsp, juiz, problemas));
        equipes.add(new Equipe(("Equipe Delta"), escalonadorrr, juiz, problemas));
        for(Equipe equipe : equipes){
            System.out.println(equipe.toString());
        }

        Maratona maratona = new Maratona(equipes, juiz, placar, 30);

        try{
            maratona.iniciar();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
