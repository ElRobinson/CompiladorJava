/**
 * Classe: NodoPilhaSemântica
 *
 * Esta classe modela objetos que formarão a pilha semântica.
 */
public class NodoPilhaSemantica {

    private String codigo;
    private int regraQueEmpilhou;

    public NodoPilhaSemantica( String c, int r ) {

        codigo = new String( c );
        regraQueEmpilhou = r;
    }

    /**
     * @return Retorna o código objeto empilhado.
     */
    public String getCodigo() {
        return codigo;
    }
    /**
     * @param codigo o código a ser empilhado.
     */
    public void setCodigo(String codigo) {
        this.codigo = new String( codigo );
    }
    /**
     * @return Retorna a regra que empilhou o código.
     */
    public int getRegraQueEmpilhou() {
        return regraQueEmpilhou;
    }
    /**
     * @param regraQueEmpilhou numero da regra que empilhou o c�digo.
     */
    public void setRegraQueEmpilhou(int regraQueEmpilhou) {
        this.regraQueEmpilhou = regraQueEmpilhou;
    }
}