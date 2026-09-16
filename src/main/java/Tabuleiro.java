public class Tabuleiro {
    Celula[][] TabuleiroLogico;
    Dificuldade Dificuldade;

    public Tabuleiro(Dificuldade dificuldade){
        this.TabuleiroLogico = createTabuleiroLogico(dificuldade);
        this.Dificuldade = dificuldade;
    }

    static Celula[][] createTabuleiroLogico(Dificuldade dificuldade){
        Celula[][] tabuleiroLogico = new Celula[dificuldade.Tamanho][dificuldade.Tamanho];

        for (int i = 0; i < dificuldade.Tamanho; i++) {
            for (int j = 0; j < dificuldade.Tamanho; j++) {
                tabuleiroLogico[i][j] = new Celula("campo", "");
            }
        }

        return tabuleiroLogico;
    }
}
