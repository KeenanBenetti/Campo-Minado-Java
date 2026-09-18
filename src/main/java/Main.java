import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends Application {

    public static Stage telaDoPrograma = new Stage();

    @Override
    public void start(Stage primaryStage) {
        // Menu facil medio e dificil
            //facil 9x9 10 bombas
            //medio 16x16 40 bombas
            //dificil 24x24 115 bombas
        //tabuleiro
            // cria tabuleiro visual e logico javafx.
            // visual - png estaticos.
        // gerar bombas após primeiro clique, evitar derrota instantanea.
        // bombas geradas com rand.

        //passo a passo logico:
            //criar uma função que gere o grid do tabuleiro com javafx, conectando eles a uma matriz logica
            //quando iniciar o jogo verificamos o clique inicial e já marcamos como uma exceção pra geração das bombas
            //aí geramos as bombas de forma randomica na tabela logica
            //o usuario clica em cada local que desejar, se não tiver bomba reduz o numero de campos livres do status
            //se clicar com botão esquerdo coloca uma bandeira (sendo possivel desfazer)
            // se em algum momento o campos livres zerar, usuario venceu + status
            // se clicou em bomba tela de derrota + status
        //regras pra implementação da leitura e escrita da matriz do tabuleiro:
            // fazer leitura logo apos da geração das bombas para verificar buracos e torna-los grupos
            //assim o clique em um abre todos
                //a implementação poderia ser primeiro indo bomba a bomba e pegar os 7 vizinhos da bomba,
                //e contar as bombas vizinhas desse quadrado, ao finalizar de fazer isso em todas as bombas os
                //quadrados vazios podem ser agrupados com um mapeamento e liberados de forma conjunta no clique de
                //qualquer um
        //outras implementações que quero:
            //timer e best time por dificuldade.
            //ranking dos melhores 10 com nome e por dificuldade.
            //botão de voltar ao menu em meio a partida
            //primeiro clique num local do tabuleiro abre 3 opções, pá, fechar e bandeira.
            //efeitos sonoros para cada ação no tabuleiro



        //implementação:


        //criar as dificuldades:

        Dificuldade[] dificuldades = {
                new Dificuldade("Fácil", 9, 10),
                new Dificuldade("Médio", 16, 40),
                new Dificuldade("Dificil", 24, 115)
        };

        //Criar menu e conectar com as dificuldades e criação do tabuleiro


        Label tituloInicio = new Label("Campo Minado");
        //tituloInicio.setStyle(font-size:30px);
        Label tituloMenu = new Label("Escolha a dificuldade:");
        //tituloMenu.setStyle(font-size:20px);
        Button[] botoesDificuldade = new Button[dificuldades.length];
        for (int i = 0; i < dificuldades.length; i++) {
            botoesDificuldade[i] = new Button(dificuldades[i].NomeDificuldade);
            int FinalI = i;
            botoesDificuldade[i].setOnAction(e -> comecarJogo(dificuldades[FinalI]));
        }

        VBox coluna = new VBox(tituloMenu);
        coluna.getChildren().addAll(botoesDificuldade);

        VBox menu = new VBox(tituloInicio, coluna);
        Scene telaMenu = new Scene(menu, 600, 800);
        telaDoPrograma.setTitle("Campo Minado");
        telaDoPrograma.setScene(telaMenu);
        telaDoPrograma.show();
    }

    static void comecarJogo(Dificuldade dificuldade){
        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);

        Random random = new Random();

        int numBombasPlaced = 0;
        int coluna = 0;
        int linha = 0;
        while (numBombasPlaced != dificuldade.NumBombas){
            coluna = random.nextInt(dificuldade.Tamanho);
            linha = random.nextInt(dificuldade.Tamanho);

            if(tabuleiro.TabuleiroLogico[linha][coluna].getTipo().equals("Campo")){
                tabuleiro.TabuleiroLogico[linha][coluna].setTipo("Bomba");
                numBombasPlaced+=1;
            }

        }

        Button[][] tabelaVisivel = new Button[dificuldade.Tamanho][dificuldade.Tamanho];
        GridPane grid = new GridPane();

        for (int i = 0; i < dificuldade.Tamanho; i++) {
            for (int j = 0; j < dificuldade.Tamanho; j++) {
                tabelaVisivel[i][j] = new Button();
                tabelaVisivel[i][j].setPrefSize(40,40);
                tabelaVisivel[i][j].setStyle(
                        "-fx-background-image: url('/Campo.png');" +
                                "-fx-background-size: cover;" +
                                "-fx-background-position: center;"
                );
                tabelaVisivel[i][j].setUserData(tabuleiro.TabuleiroLogico[i][j]);

                int FinalI = i;
                int FinalJ = j;
                tabelaVisivel[i][j].setOnAction(e -> {
                    if(tabuleiro.isFirstClick()){
                        if(checkarBomba(tabelaVisivel[FinalI][FinalJ])){
                            trocarBomba(tabelaVisivel, FinalI, FinalJ);
                            revelar(tabelaVisivel, FinalI, FinalJ);
                        } else{
                            calcularNum(tabelaVisivel);
                            revelar(tabelaVisivel, FinalI, FinalJ);
                            tabuleiro.setFirstClick(false);
                        }
                    } else{
                        if(checkarBomba(tabelaVisivel[FinalI][FinalJ])){
                            revelarBombas(tabelaVisivel);
                            //new PauseTransition(Duration.seconds(3)).setOnFinished(e -> derrota());
                        } else {
                            revelar(tabelaVisivel, FinalI, FinalJ);
                            checkarVitoria(tabelaVisivel);
                        }

                    }
                });
                grid.add(tabelaVisivel[i][j], i, j);
            }
        }


        Scene tabuleiroVisual = new Scene(grid, 600, 800);
        telaDoPrograma.setScene(tabuleiroVisual);
    }

    static boolean checkarBomba(Button botao){
        Celula celula = (Celula) botao.getUserData();
        if (celula.getTipo().equals("Bomba")){
            return true;
        } else{
            return false;
        }
    }

    static void calcularNum(Button[][] tabuleiroVisual ){
        for (int i = 0; i < tabuleiroVisual.length; i++) {
            for (int j = 0; j < tabuleiroVisual.length; j++) {
                //iteração de cada botão

                Button botao = tabuleiroVisual[i][j];
                Celula celula = (Celula) botao.getUserData();

                //apenas se for campo
                if (celula.getTipo().equals("Campo")){
                    int L = i;
                    int C = j;
                    int bombasAoRedor = 0;
                    // [L-1, C-1] [L-1, C] [L-1, C+1]
                    // [L, C-1] [L,C] [L, C+1]
                    // [L+1, C-1] [L+1, C] [L+1, C+1]

                    for (int k = L-1; k < L-1+3; k++) {
                        for (int l = C-1; l < C-1+3; l++) {
                            if(indiceExiste(k, l, tabuleiroVisual)){
                                Button botaoTeste = tabuleiroVisual[k][l];
                                if (((Celula)(botaoTeste.getUserData())).getTipo().equals("Bomba")){
                                    bombasAoRedor+=1;
                                }
                            }
                        }
                    }

                    celula.setNumBomProx(bombasAoRedor);
                }
            }
        }
    }

    static boolean indiceExiste(int L, int C, Button[][] tabuleiroVisual){
        if (L > tabuleiroVisual.length-1|| L<0){
            return false;
        } else if (C > tabuleiroVisual[0].length-1 || C < 0) {
            return false;
        }
        return true;
    }

    static void revelar(Button[][] tabelaVisivel, int i, int j, ArrayList<int[]> posicoesConectadasVazias){
        Button botao = tabelaVisivel[i][j];
        Celula celula = (Celula) botao.getUserData();
        int numBombProx = celula.NumBomProx;
        if (numBombProx == 0){
            //buscar os vizinhos dele, e se algum for zero, colocar num array de L, C.
            //Se achar um vizinho zero, fazer o mesmo com ele, verificando pelo array pra deduplicar.
            //[L-1, C-1][L-1, C][L-1, C+1]
            //[L, C-1][L, C][L, C+1]
            //[L+1, C-1][L+1, C][L+1, C+1]
            int L = i;
            int C = j;

            posicoesConectadasVazias.add(new int[]{L,C});

            for (int k = L-1; k < L-1+3; k++) {
                for (int l = C-1; l < C-1+3; l++) {
                    if(indiceExiste(k, l, tabelaVisivel)){
                        Button botaoVizinho = tabelaVisivel[k][l];
                        Celula celulaVizinha = (Celula) botaoVizinho.getUserData();

                        int[] atual = {k, l};
                        boolean jaFoi = false;

                        for (int[] item : posicoesConectadasVazias) {
                            if (Arrays.equals(item, atual)) {
                                jaFoi = true;
                            }
                        }

                        if(!jaFoi) {
                            if(celulaVizinha.NumBomProx == 0) {
                                revelar(tabelaVisivel, k, l, posicoesConectadasVazias);
                            } else {
                                String imagem = String.valueOf(celulaVizinha.NumBomProx);
                                botaoVizinho.setStyle(
                                        "-fx-background-image: url('/"+ imagem +".png');" +
                                                "-fx-background-size: cover;" +
                                                "-fx-background-position: center;"
                                );
                                celulaVizinha.setTipo("CampoUsado");
                            }
                        }
                    }
                }
            }

            for (int[] item : posicoesConectadasVazias) {
                tabelaVisivel[item[0]][item[1]].setStyle(
                        "-fx-background-image: url('/CampoUsado.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;"
                );
                ((Celula) tabelaVisivel[item[0]][item[1]].getUserData()).setTipo("CampoUsado");
            }

        } else {
            String imagem = String.valueOf(numBombProx);
            botao.setStyle(
                    "-fx-background-image: url('/"+ imagem +".png');" +
                            "-fx-background-size: cover;" +
                            "-fx-background-position: center;"
            );
            celula.setTipo("CampoUsado");
        }
    }

    static void revelar(Button[][] tabelaVisivel, int i, int j){
        ArrayList<int[]> posicoesConectadasVazias = new ArrayList<>();
        revelar(tabelaVisivel, i, j, posicoesConectadasVazias);
    }

    static void revelarBombas(Button[][] tabelaVisivel){
        for (int i = 0; i < tabelaVisivel.length; i++) {
            for (int j = 0; j < tabelaVisivel[i].length; j++) {
                if(((Celula)tabelaVisivel[i][j].getUserData()).Tipo.equals("Bomba")){
                    tabelaVisivel[i][j].setStyle(
                            "-fx-background-image: url('/Bomba.png');" +
                                    "-fx-background-size: cover;" +
                                    "-fx-background-position: center;"
                    );
                }
            }
        }
    }

    static void checkarVitoria(Button[][] tabelaVisivel){
        boolean vitoria = true;
        for (int i = 0; i < tabelaVisivel.length; i++) {
            for (int j = 0; j < tabelaVisivel[i].length; j++) {
                if (((Celula) tabelaVisivel[i][j].getUserData()).Tipo.equals("Campo")){
                    vitoria = false;
                }
            }
        }
        if(vitoria){
            revelarBombas(tabelaVisivel);
        }
    }

    static void trocarBomba(Button[][] tabelaVisivel, int i, int j){
        Random random = new Random();
        ((Celula) tabelaVisivel[i][j].getUserData()).setTipo("Campo");

        boolean done = false;

        while (!done){
            int L = random.nextInt(tabelaVisivel.length);
            int C = random.nextInt(tabelaVisivel[L].length);
            Celula celula = (Celula) tabelaVisivel[i][j].getUserData();

            if (celula.getTipo().equals("Campo")
                    && (L != i || C != j)){
                celula.setTipo("Bomba");
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
