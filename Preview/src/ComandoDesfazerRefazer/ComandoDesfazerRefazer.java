public class ComandoDesfazerRefazer
{
    private No currentIndex;
    private No parentNode = new No();

    private class No
    {
        private No left = null;
        private No right = null;
        private final Changeable changeable;

        public No(Changeable c)
        {
            changeable = c;
        }

        public No()
        {
            changeable = null;
        }
    }

    public ComandoDesfazerRefazer()
    {
        currentIndex = parentNode;
    }

    public ComandoDesfazerRefazer(ComandoDesfazerRefazer manager)
    {
        this();
        currentIndex = manager.currentIndex;
    }

    public void limparFila()
    {
        currentIndex = parentNode;
    }

    public void adicionarEstado(Changeable changeable)
    {
        No no = new No(changeable);

        currentIndex.right = no;
        no.left = currentIndex;
        currentIndex = no;
    }

    public boolean podeSerDesfeito()
    {
        return currentIndex != parentNode;
    }

    public boolean podeSerRefeito()
    {
        return currentIndex.right != null;
    }

    public void desfazer()
    {
        if (!podeSerDesfeito()){
            throw new IllegalStateException("Ação não pode ser desfeita.");
        }

        currentIndex.changeable.undo();
        moverParaEsquerda();
    }

    private void moverParaEsquerda()
    {
        if (currentIndex.left == null){
            throw new IllegalStateException("Ponteiro apontado para null.");
        }

        currentIndex = currentIndex.left;
    }

    private void moverParaDireita()
    {
        if (currentIndex.right == null){
            throw new IllegalStateException("Ponteiro apontado para null.");
        }

        currentIndex = currentIndex.right;
    }

    public void refazer()
    {
        if (!podeSerRefeito()){
            throw new IllegalStateException("Ação não pode ser refeita.");
        }

        moverParaDireita();
        currentIndex.changeable.redo();
    }

}