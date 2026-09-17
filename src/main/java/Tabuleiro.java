public class Tabuleiro {
    Celula[][] TabuleiroLogico;
    Dificuldade Dificuldade;
    boolean IsFirstClick;

    public Tabuleiro(Dificuldade dificuldade){
        this.TabuleiroLogico = createTabuleiroLogico(dificuldade);
        this.Dificuldade = dificuldade;
        this.IsFirstClick = true;
    }

    static Celula[][] createTabuleiroLogico(Dificuldade dificuldade){
        Celula[][] tabuleiroLogico = new Celula[dificuldade.Tamanho][dificuldade.Tamanho];

        for (int i = 0; i < dificuldade.Tamanho; i++) {
            for (int j = 0; j < dificuldade.Tamanho; j++) {
                tabuleiroLogico[i][j] = new Celula("Campo", "");
            }
        }

        return tabuleiroLogico;
    }
}
