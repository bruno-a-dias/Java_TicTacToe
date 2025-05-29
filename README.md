# Java_TicTacToe
Jogo da velha em Java

# Java Tic Tac Toe

Um projeto completo de Jogo da Velha (Tic Tac Toe) em Java com interface gráfica (Swing), sons, placar, escolha de modo de jogo (humano x humano ou humano x computador) e seleção de dificuldade da IA (aleatória ou Minimax).

## 🎮 Funcionalidades

- Interface gráfica amigável com Java Swing
- Placar de vitórias para X e O
- Escolha entre jogar contra outro humano ou contra o computador
- IA com dois níveis: fácil (aleatório) e difícil (Minimax)
- Sons para jogadas e vitória/empate
- Animação visual ao jogar
- Código organizado e fácil de entender

## 📁 Estrutura de Pastas

Java_TicTacToe/
│
├── src/
│ └── main/
│ └── java/
│ ├── TicTacToeGUI.java
│ └── TicTacToeGame.java
│ ├── click.wav
│ └── win.wav



## 🚀 Como executar

### 1. Compile o projeto

Abra o terminal na pasta `Java_TicTacToe` e execute:

javac -d out -sourcepath src/main/java src/main/java/TicTacToeGame.java src/main/java/TicTacToeGUI.java


### 2. Execute o jogo

No **Windows**:

java -cp out;resources TicTacToeGUI


No **Linux/Mac**:

java -cp out:resources TicTacToeGUI


> **Atenção:** Os arquivos `click.wav` e `win.wav` devem estar na pasta `resources` na raiz do projeto.

### 3. Usando uma IDE

- Importe o projeto como projeto Java padrão.
- Certifique-se de que a pasta `resources` está incluída no classpath.
- Rode a classe `TicTacToeGUI`.

## 🔊 Sons

- O projeto utiliza dois arquivos `.wav`:  
  - `click.wav` (som ao jogar)
  - `win.wav` (som ao vencer ou empatar)
- Você pode substituir esses arquivos por outros sons de sua preferência. (afinal, usei minha voz. hehehe)

## 🧑‍💻 Créditos

- Desenvolvido por [bruno-a-dias]https://github.com/bruno-a-dias
- IA Minimax baseada em algoritmos clássicos de jogos de tabuleiro

## 📌 Dicas

- Se o som não tocar, verifique se a pasta `resources` está no classpath.
- Para personalizar ainda mais, edite as cores, fontes ou adicione imagens aos botões!

---

Divirta-se jogando e programando! 😃