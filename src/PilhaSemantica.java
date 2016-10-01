import java.util.Stack;

/**
 * Classe: Pilha Semantica
 *
 * Esta classe modela a pilha semântica.
 *
 */

public class PilhaSemantica {

    private Stack pilha;

    private StringBuffer saidaPilhaSemantica;

    public PilhaSemantica() {
        pilha = new Stack();
    }

    public NodoPilhaSemantica pop() {


        NodoPilhaSemantica nodo;
        nodo = ( NodoPilhaSemantica ) pilha.pop();
        System.out.println( "Desempilhou " + nodo.getCodigo() );

        return( nodo );
    }

    public NodoPilhaSemantica push( String c, int r ) {

        System.out.println( "Empilhou: " + c );

        NodoPilhaSemantica nodo = new NodoPilhaSemantica( c, r );
        pilha.push( nodo );
        return( nodo );
    }

    public NodoPilhaSemantica push( NodoPilhaSemantica nodo ) {

        pilha.push( nodo );
        return( nodo );
    }

}