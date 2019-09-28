public class ComandoDesfazerRefazer
{
    private No posicaoAtual;
    private No noPai = new No();

    private class No
    {
        private No esquerda = null;
        private No direita = null;
        private final posicaoMudanca mudanca;

        public No(posicaoMudanca c)
        {
            mudanca = c;
        }

        public No()
        {
            mudanca = null;
        }
    }

    public ComandoDesfazerRefazer()
    {
        posicaoAtual = noPai;
    }

    public ComandoDesfazerRefazer(ComandoDesfazerRefazer manager)
    {
        this();
        posicaoAtual = manager.posicaoAtual;
    }

    public void limparFila()
    {
        posicaoAtual = noPai;
    }

    public void adicionarEstado(posicaoMudanca mudanca)
    {
        No no = new No(mudanca);

        posicaoAtual.direita = no;
        no.esquerda = posicaoAtual;
        posicaoAtual = no;
    }

    public boolean podeSerDesfeito()
    {
        return posicaoAtual != noPai;
    }

    public boolean podeSerRefeito()
    {
        return posicaoAtual.direita != null;
    }

    public void desfazer()
    {
        if (!podeSerDesfeito()){
            throw new IllegalStateException("Ação não pode ser desfeita.");
        }

        posicaoAtual.mudanca.desfazer();
        moverParaEsquerda();
    }

    private void moverParaEsquerda()
    {
        if (posicaoAtual.esquerda == null){
            throw new IllegalStateException("Ponteiro apontado para null.");
        }

        posicaoAtual = posicaoAtual.esquerda;
    }

    private void moverParaDireita()
    {
        if (posicaoAtual.direita == null){
            throw new IllegalStateException("Ponteiro apontado para null.");
        }

        posicaoAtual = posicaoAtual.direita;
    }

    public void refazer()
    {
        if (!podeSerRefeito()){
            throw new IllegalStateException("Ação não pode ser refeita.");
        }

        moverParaDireita();
        posicaoAtual.mudanca.refazer();
    }

}