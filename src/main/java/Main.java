import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Main extends Application {

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

        Stage telaDoPrograma = new Stage();
        Label tituloInicio = new Label("Campo Minado");
        //tituloInicio.setStyle(font-size:30px);
        Label tituloMenu = new Label("Escolha a dificuldade:");
        //tituloMenu.setStyle(font-size:20px);
        Button[] botoesDificuldade = new Button[dificuldades.length];
        for (int i = 0; i < dificuldades.length; i++) {
            botoesDificuldade[i] = new Button(dificuldades[i].NomeDificuldade);
        }

        VBox coluna = new VBox(tituloMenu);
        coluna.getChildren().addAll(botoesDificuldade);

        VBox menu = new VBox(tituloInicio, coluna);
        Scene telaMenu = new Scene(menu);
        telaDoPrograma.setTitle("Campo Minado");
        telaDoPrograma.setScene(telaMenu);
        telaDoPrograma.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}