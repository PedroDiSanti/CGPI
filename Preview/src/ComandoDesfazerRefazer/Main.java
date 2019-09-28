public class Main
{
    public static class Comando extends Changeable
    {
        private final String valor;

        public Comando(String v)
        {
            super();
            this.valor = v;
        }

        public void undo()
        {
            System.out.println(valor + " desfeito");
        }

        public void redo()
        {
            System.out.println(valor + " refeito");
        }
    }

    public static void main(String[] args)
    {
        ComandoDesfazerRefazer acao = new ComandoDesfazerRefazer();

        acao.adicionarEstado(new Comando("1"));
        acao.adicionarEstado(new Comando("2"));
        acao.desfazer();
        acao.refazer();
        acao.desfazer();
        acao.desfazer();
        acao.adicionarEstado(new Comando("3"));
        acao.desfazer();
    }

}