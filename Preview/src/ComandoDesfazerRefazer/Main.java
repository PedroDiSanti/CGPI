public class Main
{
    public static class Comando extends posicaoMudanca
    {
        private final String valor;

        public Comando(String v)
        {
            super();
            this.valor = v;
        }

        public void desfazer()
        {
            System.out.println(valor + " desfeito");
        }

        public void refazer()
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