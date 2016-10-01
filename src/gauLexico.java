import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.util.Vector;

        import javax.swing.JFileChooser;
        import javax.swing.JOptionPane;
        import javax.swing.JTextArea;
        import javax.swing.filechooser.FileFilter;

/**
 * Sintatico1 - Classe para implementa��o da vers�o 1 do Analisador Sint�tico
 *
 * @author Turma de compiladores de 2/2015
 * @author Ricardo Ferreira de Oliveira
 *
 * Gram�tica:
 *
 *1 <p>              ::= 'ENTREVERO' <declvariaveis> 'PARTIU' <comandos> 'DEITOU' 'O' 'CABELO'
 *2 <declvariaveis>  ::= <declvariavel> '@' <declvariaveis>
 *3                  |   <declvariavel>
 *4 <declvariavel>   ::= <tipo> <listavariaveis>
 *5 <tipo>           ::= NUMERICO
 *6                  |   EXTRINGUE
 *7 <listavariaveis> ::= <variavel> '#' <listavariaveis>
 *8                  |   <variavel>
 *9 <comandos>       ::= <comando> '$' <comandos>
 *10                  |   <comando> '$'
 *11 <comando>        ::= <cmdleitura>
 *12                  |   <cmdescrita>
 *13                  |   <cmdse>
 *14                  |   <cmdenquanto>
 *15                  |   <cmdpara>
 *16                  |   <cmdatrbuicao>
 *17 <cmdleitura>     ::= 'FALA' 'TCHE' 'O' <variavel>
 *18 <cmdescrita>     ::= 'TROVA' <expressao>
 *19 <cmdse>          ::= 'SE' <condicao> 'ENTON' <comandos> <senao> 'VAZA'
 *20 <senao>          ::= 'CAPAZ' <comandos>
 *21                  |   &
 *22 <cmdenquanto>    ::= 'ENQUANTO' <condicao> 'PELEIA' <comandos> 'DEU' 'PRA' 'TI'
 *23 <cmdatribuicao>  ::= 'POE' <expressao> 'NO' 'BOLICHO' <variavel>
 *24 <cmdpara>        ::= 'PARA' <variavel> 'DE' <expressao> 'ATE' <expressao> 'PELEIA' <comandos> 'DEU' 'PRA' 'TI'
 *25 <variavel>       ::= [A-Z][A-Z,0-9,_]*
 *26 <condicao>       ::= <expressao> '>' <expressao>
 *27                  |   <expressao> '>=' <expressao>
 *28                  |   <expressao> '<=' <expressao>
 *29                  |   <expressao> '<' <expressao>
 *30                  |   <expressao> '==' <expressao>
 *31                  |   <expressao> '<>' <expressao>
 *32 <expressao>      ::= <expressao> '+' <termo>
 *33                  |   <expressao> '-' <termo>
 *34                  |   <termo>
 *35 <termo>          ::= <termo> '*' <exponencial>
 *36                  |   <termo> '/' <exponencial>
 *37                  |   <termo> '?' <exponencial> // mmc dos operandos
 *38                  |   <exponencial>
 *39 <exponencial>    ::= <exponencial> ** <fatorial>
 *40                  |   <fatorial>
 *41 <fatorial>       ::= '[' <fatorial> ']''!'
 *42                  |   <fator>
 *43 <fator>          ::= '(' <expressao> ')'
 *44                  |   <variavel>
 *45                  |   <numero>
 *46 <numero>         ::= [0-9]+
 *                    |   [0-9]+.[0-9]+
 *
 * Com esta gram�tica, poderemos elaborar programas gdr como o abaixo:
 *
 *
 */

public class gauLexico {

    static final int T_P               =   1;
    static final int T_ENTREVERO       =   2;
    static final int T_PARTIU          =   3;
    static final int T_DEITOU          =   4;
    static final int T_O               =   5;
    static final int T_CABELO          =   6;
    static final int T_ARROBA          =   7;
    static final int T_NUMERICO        =   8;
    static final int T_EXTRINGUE       =   9;
    static final int T_JOGO_DA_VEIA    =  10;
    static final int T_PILA            =  11;
    static final int T_FALA            =  12;
    static final int T_TCHE            =  13;
    static final int T_TROVA           =  14;
    static final int T_SE              =  15;
    static final int T_ENTON           =  16;
    static final int T_VAZA            =  17;
    static final int T_CAPAZ           =  18;
    static final int T_ENQUANTO        =  19;
    static final int T_PELEIA          =  20;
    static final int T_DEU             =  21;
    static final int T_PRA             =  22;
    static final int T_TI              =  23;
    static final int T_POE             =  24;
    static final int T_NO              =  25;
    static final int T_BOLICHO         =  26;
    static final int T_PARA            =  27;
    static final int T_DE              =  28;
    static final int T_ATE             =  29;
    static final int T_ID              =  30;
    static final int T_MAIOR           =  31;
    static final int T_MENOR           =  32;
    static final int T_MAIOR_IGUAL     =  33;
    static final int T_MENOR_IGUAL     =  34;
    static final int T_IGUAL           =  35;
    static final int T_DIFERENTE       =  36;
    static final int T_MAIS            =  37;
    static final int T_MENOS           =  38;
    static final int T_VEZES           =  39;
    static final int T_DIVIDIDO        =  40;
    static final int T_MMC             =  41;
    static final int T_ELEVADO         =  42;
    static final int T_ABRE_COL        =  43;
    static final int T_FECHA_COL       =  44;
    static final int T_FATORIAL        =  45;
    static final int T_ABRE_PAR        =  46;
    static final int T_FECHA_PAR       =  47;
    static final int T_NUMERO          =  48;

    static final int T_FIM_FONTE       =  90;
    static final int T_ERRO_LEX        =  98;
    static final int T_NULO            =  99;
    static final int E_SEM_ERROS		 = 	 1;
    static final int E_ERRO_LEXICO	 = 	 2;
    static final int E_ERRO_SINTATICO	 = 	 3;
    static final int E_ERRO_SEMANTICO	 = 	 4;

    static final int FIM_ARQUIVO       =  26;

    static File arqFonte;
    static BufferedReader rdFonte;
    static File arqDestino;

    static char   		lookAhead;
    static int    		token;
    static String 		lexema;
    static int    		ponteiro;
    static String 		linhaFonte;
    static int    		linhaAtual;
    static int    		colunaAtual;
    static String 		mensagemDeErro;
    static StringBuffer 	tokensIdentificados = new StringBuffer();
    static StringBuffer 	regrasReconhecidas = new StringBuffer();
    static int 			estadoCompilacao;

    // Variaveis criadas para o semantico
    static String 		ultimoLexema;
    static StringBuffer 	codigoPython = new StringBuffer();
    static StringBuffer 	codigoInicioPython = new StringBuffer();
    static int 			nivelIdentacao = 0;
    static String			exp_left;
    static String			exp_right;
    static String			exp_middle;
    static String 		operador;
    static String			termo;
    static Vector         tabelaSimbolos;
    static boolean        temFatorial = false;
    static boolean        temMMC = false;
    static PilhaSemantica pilhaSemantica = new PilhaSemantica();

    public static void main( String s[] ) throws java.io.IOException
    {
        try {
            abreArquivo();
            abreDestino();
            linhaAtual     = 0;
            colunaAtual    = 0;
            ponteiro       = 0;
            linhaFonte     = "";
            token          = T_NULO;
            estadoCompilacao 	= E_SEM_ERROS;
            mensagemDeErro = "Sem erros de Compila��o";
            tokensIdentificados.append( "Tokens reconhecidos: \n\n" );
            regrasReconhecidas.append( "\n\nRegras reconhecidas: \n\n" );

            tabelaSimbolos = new Vector();

            // posiciono no primeiro token
            movelookAhead();
            buscaProximoToken();

            analiseSintatica();

            exibeSaida();

            gravaSaida( arqDestino );

            fechaFonte();

        } catch( FileNotFoundException fnfe ) {
            JOptionPane.showMessageDialog( null, "Arquivo nao existe!", "FileNotFoundException!", JOptionPane.ERROR_MESSAGE );
        } catch( UnsupportedEncodingException uee ) {
            JOptionPane.showMessageDialog( null, "Erro desconhecido", "UnsupportedEncodingException!", JOptionPane.ERROR_MESSAGE );
        } catch( IOException ioe ) {
            JOptionPane.showMessageDialog( null, "Erro de io: " + ioe.getMessage(), "IOException!", JOptionPane.ERROR_MESSAGE );
        } finally {
            System.out.println( "Execucao terminada!" );
        }
    }

    static void analiseSintatica() {

        p();

        if ( estadoCompilacao == E_ERRO_LEXICO ) {
            JOptionPane.showMessageDialog( null, mensagemDeErro, "Erro L�xico!", JOptionPane.ERROR_MESSAGE );
        } else if ( estadoCompilacao == E_ERRO_SINTATICO ) {
            JOptionPane.showMessageDialog( null, mensagemDeErro, "Erro Sint�tico!", JOptionPane.ERROR_MESSAGE );
        } else if ( estadoCompilacao == E_ERRO_SEMANTICO ) {
            JOptionPane.showMessageDialog( null, mensagemDeErro, "Erro Sem�ntico!", JOptionPane.ERROR_MESSAGE );
        } else {
            JOptionPane.showMessageDialog( null, "An�lise Sint�tica terminada sem erros", "An�lise Sint�tica terminada!", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    /**
     * <p> ::= 'ENTREVERO' <declvariaveis>
     * 'PARTIU' <comandos> 'DEITOU' 'O' 'CABELO'
     */
    static void p() {

        regraSemantica( 0 );
        if ( token == T_ENTREVERO ) {
            buscaProximoToken();
            declvariaveis();
            if ( token == T_PARTIU ) {
                buscaProximoToken();
                comandos();
                if ( token == T_DEITOU ) {
                    buscaProximoToken();
                    if ( token == T_O ) {
                        buscaProximoToken();
                        if ( token == T_CABELO ) {
                            buscaProximoToken();
                            acumulaRegra( "<p> ::= 'ENTREVERO' <declvariaveis> 'PARTIU' <comandos> 'DEITOU' 'O' 'CABELO'");
                        } else {
                            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nCABELO esperado, mas me surge: " + lexema );
                        }
                    } else {
                        registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nO esperado, mas o guasca me vem com: " + lexema );
                    }
                } else {
                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nDEITOU esperado, mas o pi� se fresqueia e vem com: " + lexema );
                }
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPARTIU esperado, mas o vivente me aparece com: " + lexema );
            }
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nENTREVERO esperado, mas o vivente me vem com: " + lexema );
        }
        regraSemantica( 1 );
    }

    /**
     * <declvariaveis>  ::= <declvariavel> '@' <declvariaveis>
     *                  |   <declvariavel>
     */
    private static void declvariaveis() {
        declvariavel();
        if ( token == T_ARROBA ) {
            buscaProximoToken();
            declvariaveis();
        }

    }

    /**
     * <declvariavel>   ::= <tipo> <listavariaveis>
     */
    private static void declvariavel() {
        tipo();
        listavariaveis();
    }

    /**
     * <tipo>           ::= NUMERICO
     *                  |   EXTRINGUE
     */
    private static void tipo() {
        if ( token == T_NUMERICO ) {
            buscaProximoToken();
        } else if ( token == T_EXTRINGUE ) {
            buscaProximoToken();
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nTe decide vivente: ou NUMERICO ou EXTRINGUE! sem estas pilchas o vivente vai ser expulso do CTG! " + lexema );
        }
    }

    /**
     * <listavariaveis> ::= <variavel> '#' <listavariaveis>
     *                  |   <variavel>
     */
    private static void listavariaveis() {
        variavel();
        if ( token == T_JOGO_DA_VEIA ) {
            buscaProximoToken();
            listavariaveis();
        }
    }

    /**
     * <comandos>       ::= <comando> '$' <comandos>
     *                  |   <comando> '$'
     */
    private static void comandos() {

        comando();
        if ( token == T_PILA ) {
            buscaProximoToken();
            acumulaRegra( "<comandos> ::= <comando> '$'");
            if ( iniciadorComando() ) {
                acumulaRegra( "<comandos> ::= <comando> '$' <comandos>");
                comandos();
            }
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPILA esperado, mas o vivente esta submisso aos inter�sses internacionais: " + lexema );
        }


    }

    static boolean iniciadorComando() {
        switch ( token ) {
            case T_FALA: return true;
            case T_TROVA: return true;
            case T_SE: return true;
            case T_ENQUANTO: return true;
            case T_POE: return true;
            case T_PARA: return true;
            default: return false;
        }
    }

    /**
     * <comando>        ::= <cmdleitura>
     *                  |   <cmdescrita>
     *                  |   <cmdse>
     *                  |   <cmdenquanto>
     *                  |   <cmdpara>
     *                  |   <cmdatrbuicao>
     */
    static void comando() {

        switch ( token ) {
            case T_FALA: comandoLeitura(); break;
            case T_TROVA: comandoEscrita(); break;
            case T_SE: comandoSe(); break;
            case T_ENQUANTO: comandoEnquanto(); break;
            case T_POE: comandoAtribuicao(); break;
            case T_PARA: comandoPara(); break;
        }
    }

    /**
     * <cmdatribuicao>  ::= 'POE' <expressao> 'NO' 'BOLICHO' <variavel>
     */
    static void comandoAtribuicao() {
        if ( token == T_POE ) {
            buscaProximoToken();
            expressao();
            if ( token == T_NO ) {
                buscaProximoToken();
                if ( token == T_BOLICHO ) {
                    buscaProximoToken();
                    variavel();
                    regraSemantica( 23 );
                } else {
                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nTI esperado, mas a bagualada estava de boa: " + lexema );
                }
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPRA esperado, mas a bagualada estava de boa: " + lexema );
            }
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPOE esperado, mas a bagualada estava de boa: " + lexema );
        }
    }

    /**
     * <cmdpara>        ::= 'PARA' <variavel> 'DE' <expressao> 'ATE' <expressao> 'PELEIA' <comandos> 'DEU' 'PRA' 'TI'
     */
    static void comandoPara() {
        if ( token == T_PARA ) {
            buscaProximoToken();
            variavel();
            if ( token == T_DE ) {
                buscaProximoToken();
                expressao();
                if ( token == T_ATE ) {
                    buscaProximoToken();
                    expressao();
                    if ( token == T_PELEIA ) {
                        buscaProximoToken();
                        comandos();
                        if ( token == T_DEU ) {
                            buscaProximoToken();
                            if ( token == T_PRA ) {
                                buscaProximoToken();
                                if ( token == T_TI ) {
                                    buscaProximoToken();
                                } else {
                                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nTI esperado, mas a bagualada estava de boa: " + lexema );
                                }
                            } else {
                                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPRA esperado, mas a bagualada estava de boa: " + lexema );
                            }
                        } else {
                            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nDEU esperado, mas a bagualada estava de boa: " + lexema );
                        }
                    } else {
                        registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPELEIA esperada, mas a bagualada estava de boa: " + lexema );
                    }
                } else {
                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nATE esperada, mas a bagualada estava de boa: " + lexema );
                }
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nDE esperada, mas a bagualada estava de boa: " + lexema );
            }
        }
    }

    /**
     * <cmdse> ::= 'SE' <condicao> 'ENTON' <comandos> <senao> 'VAZA'
     */
    static void comandoSe() {
        if ( token == T_SE ) {
            buscaProximoToken();
            condicao();
            if ( token == T_ENTON ) {
                buscaProximoToken();
                comandos();
                senao();
                if ( token == T_VAZA ) {
                    buscaProximoToken();
                } else {
                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nVAZA esperado, mas o bagual se passou e escreveu: " + lexema );
                }
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nENTON esperado, mas o bagual se passou e escreveu: " + lexema );
            }
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nSE esperado, mas o bagual se passou e escreveu: " + lexema );
        }
    }

    /**
     * <senao>          ::= 'CAPAZ' <comandos>
     *                  |   &
     */
    static void senao() {
        if ( token == T_CAPAZ ) {
            buscaProximoToken();
            comandos();
        }
    }

    /**
     * <cmdenquanto>    ::= 'ENQUANTO' <condicao> 'PELEIA' <comandos> 'DEU' 'PRA' 'TI'
     */
    static void comandoEnquanto() {
        if ( token == T_ENQUANTO ) {
            buscaProximoToken();
            condicao();
            if ( token == T_PELEIA ) {
                buscaProximoToken();
                comandos();
                if ( token == T_DEU ) {
                    buscaProximoToken();
                    if ( token == T_PRA ) {
                        buscaProximoToken();
                        if ( token == T_TI ) {
                            buscaProximoToken();
                        } else {
                            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nTI esperado, mas a bagualada estava de boa: " + lexema );
                        }
                    } else {
                        registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPRA esperado, mas a bagualada estava de boa: " + lexema );
                    }
                } else {
                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nDEU esperado, mas a bagualada estava de boa: " + lexema );
                }
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nPELEIA esperada, mas a bagualada estava de boa: " + lexema );
            }
        }
    }


    /**
     * <cmdleitura>     ::= 'FALA' 'TCHE' 'O' <variavel>
     */
    static void comandoLeitura() {
        if ( token == T_FALA ) {
            buscaProximoToken();
            if ( token == T_TCHE ) {
                buscaProximoToken();
                if ( token == T_O ) {
                    buscaProximoToken();
                    variavel();
                    acumulaRegra( "<cmdleitura> ::= 'FALA' 'TCHE' 'O' <variavel>");
                } else {
                    registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nO esperado, mas o guasca me vem com: " + lexema );
                }
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nTCHE esperado, mas bah me aparece: " + lexema );
            }
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nFALA esperado, mas o bagual se passou e escreveu: " + lexema );
        }
    }

    /**
     *  <cmdescrita>     ::= 'TROVA' <expressao>
     */
    static void comandoEscrita() {
        if ( token == T_TROVA ) {
            buscaProximoToken();
            expressao();
            acumulaRegra( "<cmdescrita>     ::= 'TROVA' <expressao>");
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nTROVA esperado, mas o guasca me vem com: " + lexema );
        }
    }

    /**
     * <variavel>       ::= [A-Z][A-Z,0-9,_]*
     */
    static void variavel() {
        if ( token == T_ID ) {
            buscaProximoToken();
            regraSemantica( 25 );
            acumulaRegra( "<variavel> ::= [A-Z][A-Z,0-9,_]*");
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nID esperado, mas o guasca me vem com: " + lexema );
        }
    }

    /**
     * <condicao>       ::= <expressao> '>' <expressao>
     *                  |   <expressao> '>=' <expressao>
     *                  |   <expressao> '<=' <expressao>
     *                  |   <expressao> '<' <expressao>
     *                  |   <expressao> '==' <expressao>
     *                  |   <expressao> '<>' <expressao>
     */
    static void condicao() {
        expressao();
        if ( ( token == T_MAIOR ) ||
                ( token == T_MENOR ) ||
                ( token == T_MAIOR_IGUAL ) ||
                ( token == T_MENOR_IGUAL ) ||
                ( token == T_IGUAL ) ||
                ( token == T_DIFERENTE )) {
            buscaProximoToken();
            expressao();
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nO bagual tem que compar�. N�o me sai com: " + lexema );
        }
    }

    /**
     * 32 <expressao>      ::= <expressao> '+' <termo>
     * 33                 |   <expressao> '-' <termo>
     * 34                 |   <termo>
     */
    static void expressao() {
        termo();
        acumulaRegra( "<expressao> ::= <termo>");
        while ( ( token == T_MAIS ) ||
                ( token == T_MENOS ) ) {
            if ( token == T_MAIS  ) {
                buscaProximoToken();
                termo();
                regraSemantica( 32 );
                acumulaRegra( "<expressao> ::= <expressao> '+' <termo>");
            } else {
                buscaProximoToken();
                termo();
                regraSemantica( 33 );
                acumulaRegra( "<expressao> ::= <expressao> '-' <termo>");
            }
        }

    }

    /** <termo>         ::= <termo> '*' <exponencial>
     *                  |   <termo> '/' <exponencial>
     *                  |   <termo> '?' <exponencial> // mmc dos operandos
     *                  |   <exponencial>
     */
    static void termo() {
        exponencial();
        acumulaRegra( "<termo> ::= <exponencial>");
        while ( ( token == T_VEZES ) ||
                ( token == T_DIVIDIDO ) ||
                ( token == T_MMC ) ) {
            switch ( token ) {
                case T_VEZES: buscaProximoToken();
                    exponencial();
                    acumulaRegra( "<termo> ::= <termo> '*' <exponencial>");
                    regraSemantica( 35 );
                    break;
                case T_DIVIDIDO: buscaProximoToken();
                    exponencial();
                    acumulaRegra( "<termo> ::= <termo> '/' <exponencial>");
                    regraSemantica( 36 );
                    break;
                case T_MMC: buscaProximoToken();
                    exponencial();
                    acumulaRegra( "<termo> ::= <termo> '?' <exponencial>");
                    regraSemantica( 37 );
                    break;
            }



        }
    }

    /**
     * <exponencial>    ::= <exponencial> ** <fatorial>
     *                  |   <fatorial>
     */
    static void exponencial() {
        fatorial();
        acumulaRegra( "<exponencial> ::= <fatorial>");
        while ( token == T_ELEVADO ) {
            buscaProximoToken();
            fatorial();
            acumulaRegra( "<exponencial> ::= <exponencial> '**' <fatorial>");
        }
    }

    /**
     * <fatorial>       ::= '[' <fatorial> ']''!'
     *                  |   <fator>
     */
    static void fatorial() {

        if ( token == T_ABRE_COL ) {
            buscaProximoToken();
            fatorial();
            if ( token == T_FECHA_COL ) {
                buscaProximoToken();
                if ( token == T_FATORIAL ) {
                    buscaProximoToken();
                }
                acumulaRegra( "<fatorial>       ::= '[' <fatorial> ']''!'");
            }
        }	else {
            fator();
            acumulaRegra( "<fatorial>       ::= <fator>");
        }
    }

    /**
     * <fator>          ::= '(' <expressao> ')'
     *                  |   <variavel>
     *                  |   <numero>
     */
    static void fator() {
        if ( token == T_ABRE_PAR ) {
            buscaProximoToken();
            expressao();
            if ( token == T_FECHA_PAR ) {
                buscaProximoToken();
                regraSemantica( 43 );
                acumulaRegra( "<fator> ::= '(' <expressao> ')'");
            } else {
                registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nO vivente n�o fecha os parentesis: " + lexema );
            }
        } else if ( token == T_NUMERO ) {
            numero();
            regraSemantica( 44 );
            acumulaRegra( "<fator> ::= <numero>");
        } else {
            variavel();
            regraSemantica( 45 );
            acumulaRegra( "<fator> ::= <variavel>");
        }
    }

    /**
     * <numero>         ::= [0-9]+
     *                  |   [0-9]+.[0-9]+
     */
    static void numero() {
        if ( token == T_NUMERO ) {
            buscaProximoToken();
            regraSemantica( 46 );
            acumulaRegra( "<numero> ::= [0-9]+ | [0-9]+.[0-9]+");
        } else {
            registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nO vivente n�o sabe os algarismos ar�bicos. Mas que barbaridade ch�!: " + lexema );
        }
    }

    /**
     * <comando_sendo>    ::= 'SENDO' '(' <cond> ')' '['
     *                        <comandos> ']'
     *                        <ousendos>
     *                        <naosendo>
     *                        'FIMSENDO'
     */
//   private static void sendo() {
//		  if ( token == T_SENDO ) {
//			  buscaProximoToken();
//			  if ( token == T_ABRE_PAR ) {
//				  buscaProximoToken();
//				  condicao();
//				  if ( token == T_FECHA_PAR ) {
//					  buscaProximoToken();
//					  if ( token == T_ABRE_COL ) {
//						  buscaProximoToken();
//						  comandos();
//						  if ( token == T_FECHA_COL ) {
//							  buscaProximoToken();
//							  ousendos();
//							  naosendo();
//							  if ( token == T_FIMSENDO ) {
//								  buscaProximoToken();
//								  acumulaRegra( "<comando_sendo>    ::= 'SENDO' '(' <cond> ')' '[' <comandos> ']' <ousendos> <naosendo> 'FIMSENDO'" );
//							  }  else {
//								  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\']' esperado, mas encontrei: " + lexema );
//							  }
//						  }  else {
//							  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\']' esperado, mas encontrei: " + lexema );
//						  }
//					  }  else {
//						  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'[' esperado, mas encontrei: " + lexema );
//					  }
//				  }  else {
//					  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\')' esperado, mas encontrei: " + lexema );
//				  }
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'(' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'SENDO' esperado, mas encontrei: " + lexema );
//	  }
//   }

    /**
     * <ousendos>         ::= <ousendo>
     * 					|   <ousendo> <ousendos>
     *                    |   <epsilon>
     */
//  private static void ousendos() {
//	  while ( token == T_OUSENDO ) {
//		  ousendo();
//	  }
//  }

    /**
     * <ousendo> ::= 'OUSENDO' '(' <cond> ')' '[' <comandos> ']'
     */
//  private static void ousendo() {
//	  if ( token == T_OUSENDO ) {
//		  buscaProximoToken();
//		  if ( token == T_ABRE_PAR ) {
//			  buscaProximoToken();
//			  condicao();
//			  if ( token == T_FECHA_PAR ) {
//				  buscaProximoToken();
//				  if ( token == T_ABRE_COL ) {
//					  buscaProximoToken();
//					  comandos();
//					  if ( token == T_FECHA_COL ) {
//						  buscaProximoToken();
//						  acumulaRegra( "<comando_enquanto> ::= 'ENQUANTO' '(' <cond> ')' '[' <comandos> ']'");
//					  }  else {
//						  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\']' esperado, mas encontrei: " + lexema );
//					  }
//				  }  else {
//					  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'[' esperado, mas encontrei: " + lexema );
//				  }
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\')' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'(' esperado, mas encontrei: " + lexema );
//		  }
//	  }  else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'OUSENDO' esperado, mas encontrei: " + lexema );
//	  }
//  }

    /**
     * <naosendo>         ::= 'NAOSENDO' '[' <comandos> ']'
     *                    |   <epsilon>
     */
//  private static void naosendo() {
//	  if ( token == T_NAOSENDO ) {
//		  buscaProximoToken();
//		  if ( token == T_ABRE_COL ) {
//			  buscaProximoToken();
//			  comandos();
//			  if ( token == T_FECHA_COL ) {
//				  buscaProximoToken();
//				  acumulaRegra( "<naosendo>         ::= 'NAOSENDO' '[' <comandos> ']'");
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\']' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'[' esperado, mas encontrei: " + lexema );
//		  }
//
//	  } else {
//		  acumulaRegra( "<naosendo> ::= <epsilon>");
//	  }
//  }

    /**
     * <comando_enquanto> ::= 'ENQUANTO' '(' <cond> ')'
     *                        '[' <comandos> ']'
     */
//  private static void comandoEnquanto() {
//	  if ( token == T_ENQUANTO ) {
//		  buscaProximoToken();
//		  if ( token == T_ABRE_PAR ) {
//			  buscaProximoToken();
//			  condicao();
//			  if ( token == T_FECHA_PAR ) {
//				  buscaProximoToken();
//				  if ( token == T_ABRE_COL ) {
//					  buscaProximoToken();
//					  comandos();
//					  if ( token == T_FECHA_COL ) {
//						  buscaProximoToken();
//						  acumulaRegra( "<comando_enquanto> ::= 'ENQUANTO' '(' <cond> ')' '[' <comandos> ']'");
//					  }  else {
//						  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\']' esperado, mas encontrei: " + lexema );
//					  }
//				  }  else {
//					  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'[' esperado, mas encontrei: " + lexema );
//				  }
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\')' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'(' esperado, mas encontrei: " + lexema );
//		  }
//	  }  else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'ENQUANTO' esperado, mas encontrei: " + lexema );
//	  }
//  }

    /**
     * <comando_faca> ::= 'FACA' <id> '<-' <expressao> 'ATE' <expressao> '['
     *                     <comandos> ']'
     */
//  private static void comandoFaca() {
//	  if ( token == T_FACA ) {
//		  buscaProximoToken();
//		  id();
//		  if ( token == T_ATRIBUICAO ) {
//			  buscaProximoToken();
//			  expressao();
//			  if ( token == T_ATE ) {
//				  buscaProximoToken();
//				  expressao();
//				  if ( token == T_ABRE_COL ) {
//					  buscaProximoToken();
//					  comandos();
//					  if ( token == T_FECHA_COL ) {
//						  buscaProximoToken();
//						  acumulaRegra( "<comando_faca> ::= 'FACA' <id> '<-' <expressao> 'ATE' <expressao> '[' <comandos> ']'");
//					  }  else {
//						  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\']' esperado, mas encontrei: " + lexema );
//					  }
//				  }  else {
//					  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'[' esperado, mas encontrei: " + lexema );
//				  }
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'ATE' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'<-' esperado, mas encontrei: " + lexema );
//		  }
//	  }  else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'FACA' esperado, mas encontrei: " + lexema );
//	  }
//  }

    // <comando_le>       ::= '?' '(' <id> ')'
//  private static void comandoLe() {
//	  if ( token == T_INTERROGACAO ) {
//		  buscaProximoToken();
//		  if ( token == T_ABRE_PAR ) {
//			  buscaProximoToken();
//			  id();
//			  if ( token == T_FECHA_PAR ) {
//				  buscaProximoToken();
//				  acumulaRegra( "<comando_le> ::= '?' '(' <id> ')'");
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n')' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n'(' esperado, mas encontrei: " + lexema );
//		  }
//	  }  else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'!' esperado, mas encontrei: " + lexema );
//	  }
//  }

    //* <comando_atribui>  ::= <lista_ids> '<-' <expressao>
//  private static void comandoAtribui() {
//	listaIds();
//	if ( token == T_ATRIBUICAO ) {
//		buscaProximoToken();
//		expressao();
//		acumulaRegra( "<comando_atribui>  ::= <lista_ids> '<-' <expressao>");
//	} else {
//		registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'<-' esperado, mas encontrei: " + lexema );
//	}
//
//  }

//  private static void listaIds() {
//	  id();
//	  while ( token == T_VIRGULA ) {
//		  buscaProximoToken();
//		  listaIds();
//	  }
//  }

//  <comando_escreve>  ::= '!' '(' <exp> ')'
//  private static void comandoEscreve() {
//	  if ( token == T_EXCLAMACAO ) {
//		  buscaProximoToken();
//		  if ( token == T_ABRE_PAR ) {
//			  buscaProximoToken();
//			  expressao();
//			  if ( token == T_FECHA_PAR ) {
//				  buscaProximoToken();
//				  acumulaRegra( "<comando_escreve> ::= '!' '(' <exp> ')'");
//			  }  else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n')' esperado, mas encontrei: " + lexema );
//			  }
//		  }  else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n'(' esperado, mas encontrei: " + lexema );
//		  }
//	  }  else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\'!' esperado, mas encontrei: " + lexema );
//	  }
//
//  }

    /**
     * <cond>             ::= <expressao> '>' <expressao>
     *                    |   <expressao> '>=' <expressao>
     *                    |   <expressao> '<=' <expressao>
     *                    |   <expressao> '<' <expressao>
     *                    |   <expressao> '<>' <expressao>
     *                    |   <expressao> '=' <expressao>
     */
//   private static void condicao() {
//	   expressao();
//	   if ( ( token == T_MAIOR ) ||
//			( token == T_MAIOR_IGUAL ) ||
//			( token == T_MENOR ) ||
//			( token == T_MENOR_IGUAL ) ||
//			( token == T_DIFERENTE ) ||
//			( token == T_IGUAL ) ) {
//		   buscaProximoToken();
//		   expressao();
//	   } else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nSimbolo de compara��o esperado, mas encontrei: " + lexema );
//	   }
//   }

    /**
     * <expressao>        ::= <expressao> '+' <termo>
     *                    |   <expressao> '-' <termo>
     *                    |   <expressao> '&' <termo>
     *                    |   <termo>
     */
//  private static void expressao() {
//	  termo();
//	  acumulaRegra( "<expressao> ::= <termo>");
//	  while ( ( token == T_MAIS ) ||
//			  ( token == T_MENOS ) ||
//			  ( token == T_MMC ) ) {
//		  buscaProximoToken();
//		  termo();
//		  acumulaRegra( "<expressao> ::= <expressao> '+|-|&' <termo>");
//	  }
//  }

    /**
     * <termo>            ::= <termo> '*' <exponendo>
     *                    |   <termo> '/' <exponendo>
     *                    |   <termo> '%' <exponendo>
     *                    |   <exponendo>
     */
//  private static void termo() {
//	  exponendo();
//	  acumulaRegra( "<termo> ::= <exponendo>");
//	  while ( ( token == T_VEZES ) ||
//			  ( token == T_DIVIDIDO ) ||
//			  ( token == T_RESTO ) ) {
//		  buscaProximoToken();
//		  exponendo();
//		  acumulaRegra( "<termo> ::= <termo> '*|/|%' <exponendo>");
//	  }
//  }

    /**
     * <exponendo>        ::= <fator> '^' <exponendo>
     *                    |   '@' <exponendo>
     *                    |   <fator>
     */
//  private static void exponendo() {
//	  if ( token == T_RADICIONADO ) {
//		  buscaProximoToken();
//		  exponendo();
//		  acumulaRegra( "<exponendo> ::= '@' <exponendo>");
//	  } else {
//		  fator();
//		  if ( token == T_ELEVADO ) {
//			  buscaProximoToken();
//			  exponendo();
//			  acumulaRegra( "<exponendo> ::= <fator> '^' <exponendo>");
//		  }
//	  }
//  }

    /**
     * <fator>            ::= '(' <expressao> ')'
     *                    |   <id>
     *  			        |   <numero>
     */
//  private static void fator() {
//	switch ( token ) {
//	case T_ID: id(); break;
//	case T_NUMERO: numero(); break;
//	case T_ABRE_PAR: {
//		buscaProximoToken();
//		expressao();
//		if ( token == T_FECHA_PAR ) {
//			buscaProximoToken();
//		} else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n')' esperado, mas encontrei: " + lexema );
//		}
//		break;
//	}
//	default: registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n'id, numero ou (' esperado, mas encontrei: " + lexema );
//	}
//  }

//  private static void lista_ids() {
//	  id();
//	  while ( token == T_VIRGULA ) {
//		  buscaProximoToken();
//		  lista_ids();
//	  }
//  }

//  private static void numero() {
//	  if ( token == T_NUMERO ) {
//		  buscaProximoToken();
//		  acumulaRegra( "<numero> ::= [0-9]+");
//	  } else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nNumero esperado, mas encontrei: " + lexema );
//	  }
//  }
//
//  private static void id() {
//	  if ( token == T_ID ) {
//		  buscaProximoToken();
//		  acumulaRegra( "<id> ::= [A-Z][A-Z,0-9,_]*");
//	  } else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nId esperado, mas encontrei: " + lexema );
//	  }
//  }
//


//
//  static void comandoPara() {
//	  if ( token == T_PARA ) {
//	 	  buscaProximoToken();
//		  id();
//		  if ( token == T_IGUAL ) {
//		 	  buscaProximoToken();
//		 	  expressao();
//			  if ( token == T_ATE ) {
//			 	  buscaProximoToken();
//			 	  expressao();
//				  if ( token == T_PULANDO ) {
//					  buscaProximoToken();
//					  expressao();
//					  if ( token == T_FACA ) {
//						  buscaProximoToken();
//						  comandos();
//						  if ( token == T_FIMFACA ) {
//							  buscaProximoToken();
//							  acumulaRegra( "regralh�o do para");
//						  } else {
//							  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nfimfaca esperado, mas encontrei: " + lexema );
//						  }
//					  } else {
//						  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nfaca esperado, mas encontrei: " + lexema );
//					  }
//				  } else {
//					  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\npulando ou saltando esperado, mas encontrei: " + lexema );
//				  }
//			  } else {
//				  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nate esperado, mas encontrei: " + lexema );
//			  }
//		  } else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n= esperado, mas encontrei: " + lexema );
//		  }
//	  }
//
//  }
//
//  static void comandoAtribuicao() {
//	  if ( token == T_ID ) {
//	 	  buscaProximoToken();
//		  if ( token == T_ATRIBUICAO ) {
//		 	  buscaProximoToken();
//		 	  expressao();
//			  acumulaRegra( "<id> ::= <expressao>");
//		  } else {
//			  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\n<- esperado, mas encontrei: " + lexema );
//		  }
//	  }
//  }
//  static void listaExpressoes() {
//
//	  expressao();
//	  acumulaRegra( "<lista_expressoes> ::= <expressao>");
//	  if ( token == T_VIRGOLA ) {
//	 	  buscaProximoToken();
//		  acumulaRegra( "<lista_expressoes> ::= <expressao> ',' <lista_expressoes>");
//		  listaExpressoes();
//	  }
//  }
//
//  static void expressao() {
//
//	  if ( token == T_NUMERO || token == T_ID ) {
//		  if ( token == T_NUMERO ) {
//			  acumulaRegra( "<expressao> ::= <numero>");
//		  } else {
//			  acumulaRegra( "<expressao> ::= <id>");
//		  }
//		  buscaProximoToken();
//		  if ( token == T_ADICAO || token == T_PRODUTO || token == T_DIVISAO || token == T_SUBTRACAO ) {
//			  switch ( token ) {
//			  case T_ADICAO: buscaProximoToken();
//			  				 expressao();
//			  				 break;
//			  case T_PRODUTO: buscaProximoToken();
//				 			 expressao();
//				 			 break;
//			  case T_DIVISAO: buscaProximoToken();
//				 			 expressao();
//				 			 break;
//			  case T_SUBTRACAO: buscaProximoToken();
//				 			 expressao();
//				 			 break;
//			  }
//			  acumulaRegra( "<expressao> ::= <expressao> [+-*/] <expressao>");
//		  }
//	  } else {
//		  registraErroSintatico( "Erro Sint�tico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nNUMERO esperado, mas encontrei: " + lexema );
//	  }
//  }
//
    static void registraErroSintatico( String msg ) {

        if ( estadoCompilacao == E_SEM_ERROS ) {
            estadoCompilacao = E_ERRO_SINTATICO;
            mensagemDeErro = msg;
        }
    }

    static void fechaFonte() throws IOException
    {
        rdFonte.close();
    }

    static void movelookAhead()
    {

        if ( ( ponteiro + 1 ) > linhaFonte.length() ) {

            linhaAtual++;
            ponteiro = 0;

            try {
                if ( ( linhaFonte = rdFonte.readLine() ) == null ) {
                    lookAhead = FIM_ARQUIVO;
                } else {

                    StringBuffer sbLinhaFonte = new StringBuffer( linhaFonte );
                    sbLinhaFonte.append( '\13' ).append( '\10' );
                    linhaFonte = sbLinhaFonte.toString();

                    lookAhead = linhaFonte.charAt( ponteiro );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            lookAhead = linhaFonte.charAt( ponteiro );
        }

        if ( ( lookAhead >= 'a' ) &&
                ( lookAhead <= 'z' ) ) {
            lookAhead = (char) ( lookAhead - 'a' + 'A' );
        }

        ponteiro++;
        colunaAtual = ponteiro + 1;
    }

    static void buscaProximoToken()
    {

        if ( lexema != null ) {
            ultimoLexema = new String( lexema );
        }

        int i,j;

        StringBuffer sbLexema = new StringBuffer( "" );

        while ( ( lookAhead == 9 ) ||
                ( lookAhead == '\n' ) ||
                ( lookAhead == 8 ) ||
                ( lookAhead == 11 ) ||
                ( lookAhead == 12 ) ||
                ( lookAhead == '\r' ) ||
                ( lookAhead == 32 ) )
        {
            movelookAhead();
        }

    /*--------------------------------------------------------------*
     * Caso o primeiro caracter seja alfabetico, procuro capturar a *
     * sequencia de caracteres que se segue a ele e classifica-la   *
     *--------------------------------------------------------------*/
        if ( ( lookAhead >= 'A' ) && ( lookAhead <= 'Z' ) )
        {
            sbLexema.append( lookAhead );
            movelookAhead();

            while ( ( ( lookAhead >= 'A' ) && ( lookAhead <= 'Z' ) ) ||
                    ( ( lookAhead >= '0' ) && ( lookAhead <= '9' ) ) )
            {
                sbLexema.append( lookAhead );
                movelookAhead();
            }

            lexema = sbLexema.toString();

        /* Classifico o meu token como palavra reservada ou id */
            if ( lexema.equals( "ENTREVERO" ) )
                token = T_ENTREVERO;
            else if ( lexema.equals( "PARTIU" ) )
                token = T_PARTIU;
            else if ( lexema.equals( "DEITOU" ) )
                token = T_DEITOU;
            else if ( lexema.equals( "O" ) )
                token = T_O;
            else if ( lexema.equals( "CABELO" ) )
                token = T_CABELO;
            else if ( lexema.equals( "ATE" ) )
                token = T_ATE;
            else if ( lexema.equals( "NUMERICO" ) )
                token = T_NUMERICO;
            else if ( lexema.equals( "EXTRINGUE" ) )
                token = T_EXTRINGUE;
            else if ( lexema.equals( "FALA" ) )
                token = T_FALA;
            else if ( lexema.equals( "TCHE" ) )
                token = T_TCHE;
            else if ( lexema.equals( "TROVA" ) )
                token = T_TROVA;
            else if ( lexema.equals( "SE" ) )
                token = T_SE;
            else if ( lexema.equals( "ENTON" ) )
                token = T_ENTON;
            else if ( lexema.equals( "CAPAZ" ) )
                token = T_CAPAZ;
            else if ( lexema.equals( "ENQUANTO" ) )
                token = T_ENQUANTO;
            else if ( lexema.equals( "PELEIA" ) )
                token = T_PELEIA;
            else if ( lexema.equals( "POE" ) )
                token = T_POE;
            else if ( lexema.equals( "NO" ) )
                token = T_NO;
            else if ( lexema.equals( "DEU" ) )
                token = T_DEU;
            else if ( lexema.equals( "PRA" ) )
                token = T_PRA;
            else if ( lexema.equals( "TI" ) )
                token = T_TI;
            else if ( lexema.equals( "BOLICHO" ) )
                token = T_BOLICHO;
            else if ( lexema.equals( "PARA" ) )
                token = T_PARA;
            else if ( lexema.equals( "DE" ) )
                token = T_DE;
            else {
                token = T_ID;
            }
        }
        else if ( ( lookAhead >= '0' ) && ( lookAhead <= '9' ) ) {
            sbLexema.append( lookAhead );
            movelookAhead();
            while ( ( lookAhead >= '0' ) && ( lookAhead <= '9' ) )
            {
                sbLexema.append( lookAhead );
                movelookAhead();
            }
            token = T_NUMERO;
        } else if ( lookAhead == '@' ){
            sbLexema.append( lookAhead );
            token = T_ARROBA;
            movelookAhead();
        } else if ( lookAhead == '#' ){
            sbLexema.append( lookAhead );
            token = T_JOGO_DA_VEIA;
            movelookAhead();
        } else if ( lookAhead == '(' ){
            sbLexema.append( lookAhead );
            token = T_ABRE_PAR;
            movelookAhead();
        } else if ( lookAhead == '[' ){
            sbLexema.append( lookAhead );
            token = T_ABRE_COL;
            movelookAhead();
        } else if ( lookAhead == ')' ){
            sbLexema.append( lookAhead );
            token = T_FECHA_PAR;
            movelookAhead();
        } else if ( lookAhead == ']' ){
            sbLexema.append( lookAhead );
            token = T_FECHA_COL;
            movelookAhead();
        } else if ( lookAhead == '<' ){
            token = T_MENOR;
            sbLexema.append( lookAhead );
            movelookAhead();
            if ( lookAhead == '=' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_MENOR_IGUAL;
            } else if ( lookAhead == '>' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_DIFERENTE;
            }
        } else if ( lookAhead == '>' ){
            token = T_MAIOR;
            sbLexema.append( lookAhead );
            movelookAhead();
            if ( lookAhead == '=' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_MAIOR_IGUAL;
            }
        } else if ( lookAhead == '=' ){
            sbLexema.append( lookAhead );
            token = T_IGUAL;
            movelookAhead();
        } else if ( lookAhead == '!' ){
            sbLexema.append( lookAhead );
            token = T_FATORIAL;
            movelookAhead();
        } else if ( lookAhead == '+' ){
            sbLexema.append( lookAhead );
            token = T_MAIS;
            movelookAhead();
        } else if ( lookAhead == '-' ){
            sbLexema.append( lookAhead );
            token = T_MENOS;
            movelookAhead();
        } else if ( lookAhead == '*' ){
            sbLexema.append( lookAhead );
            token = T_VEZES;
            movelookAhead();
            if ( lookAhead == '*' ){
                sbLexema.append( lookAhead );
                token = T_ELEVADO;
                movelookAhead();
            }
        } else if ( lookAhead == '/' ){
            sbLexema.append( lookAhead );
            token = T_DIVIDIDO;
            movelookAhead();
        } else if ( lookAhead == '^' ){
            sbLexema.append( lookAhead );
            token = T_ELEVADO;
            movelookAhead();
        } else if ( lookAhead == '?' ){
            sbLexema.append( lookAhead );
            token = T_MMC;
            movelookAhead();
        } else if ( lookAhead == '$' ){
            sbLexema.append( lookAhead );
            token = T_PILA;
            movelookAhead();
        } else if ( lookAhead == FIM_ARQUIVO ){
            token = T_FIM_FONTE;
        } else {
            token = T_ERRO_LEX;
            mensagemDeErro = "Erro L�xico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nToken desconhecido: " + lookAhead;
            sbLexema.append( lookAhead );
        }

        lexema = sbLexema.toString();

        if ( token == T_ERRO_LEX ) {
            estadoCompilacao = E_ERRO_LEXICO;
        }

        mostraToken();
    }

    static void mostraToken()
    {

        StringBuffer tokenLexema = new StringBuffer( "" );

        switch ( token ) {
            case T_P            : tokenLexema.append( "T_P" ); break;
            case T_ENTREVERO    : tokenLexema.append( "T_ENTREVERO" ); break;
            case T_PARTIU       : tokenLexema.append( "T_PARTIU" ); break;
            case T_DEITOU       : tokenLexema.append( "T_DEITOU" ); break;
            case T_O      		: tokenLexema.append( "T_O" ); break;
            case T_CABELO       : tokenLexema.append( "T_CABELO" ); break;
            case T_ARROBA       : tokenLexema.append( "T_ARROBA" ); break;
            case T_NUMERICO     : tokenLexema.append( "T_NUMERICO" ); break;
            case T_FIM_FONTE    : tokenLexema.append( "T_FIM_FONTE" ); break;
            case T_ERRO_LEX     : tokenLexema.append( "T_ERRO_LEX" ); break;
            case T_NULO         : tokenLexema.append( "T_NULO" ); break;
            case T_EXTRINGUE    : tokenLexema.append( "T_EXTRINGUE" ); break;
            case T_JOGO_DA_VEIA : tokenLexema.append( "T_JOGO_DA_VEIA" ); break;
            case T_PILA         : tokenLexema.append( "T_PILA" ); break;
            case T_FALA         : tokenLexema.append( "T_FALA" ); break;
            case T_TCHE         : tokenLexema.append( "T_TCHE" ); break;
            case T_TROVA        : tokenLexema.append( "T_TROVA" ); break;
            case T_SE           : tokenLexema.append( "T_SE" ); break;
            case T_ENTON        : tokenLexema.append( "T_ENTON" ); break;
            case T_VAZA         : tokenLexema.append( "T_VAZA" ); break;
            case T_CAPAZ        : tokenLexema.append( "T_CAPAZ" ); break;
            case T_ENQUANTO     : tokenLexema.append( "T_ENQUANTO" ); break;
            case T_PELEIA       : tokenLexema.append( "T_PELEIA" ); break;
            case T_DEU          : tokenLexema.append( "T_DEU" ); break;
            case T_PRA          : tokenLexema.append( "T_PRA" ); break;
            case T_TI           : tokenLexema.append( "T_TI" ); break;
            case T_POE          : tokenLexema.append( "T_POE" ); break;
            case T_NO           : tokenLexema.append( "T_NO" ); break;
            case T_BOLICHO      : tokenLexema.append( "T_BOLICHO" ); break;
            case T_PARA         : tokenLexema.append( "T_PARA" ); break;
            case T_DE           : tokenLexema.append( "T_DE" ); break;
            case T_ATE          : tokenLexema.append( "T_ATE" ); break;
            case T_ID           : tokenLexema.append( "T_ID" ); break;
            case T_MAIOR        : tokenLexema.append( "T_MAIOR" ); break;
            case T_MENOR        : tokenLexema.append( "T_MENOR" ); break;
            case T_MAIOR_IGUAL  : tokenLexema.append( "T_MAIOR_IGUAL" ); break;
            case T_MENOR_IGUAL  : tokenLexema.append( "T_MENOR_IGUAL" ); break;
            case T_IGUAL        : tokenLexema.append( "T_IGUAL" ); break;
            case T_NUMERO       : tokenLexema.append( "T_NUMERO" ); break;
            case T_FECHA_PAR    : tokenLexema.append( "T_FECHA_PAR" ); break;
            case T_ABRE_PAR     : tokenLexema.append( "T_ABRE_PAR" ); break;
            case T_FATORIAL     : tokenLexema.append( "T_FATORIAL" ); break;
            case T_FECHA_COL    : tokenLexema.append( "T_FECHA_COL" ); break;
            case T_ABRE_COL     : tokenLexema.append( "T_ABRE_COL" ); break;
            case T_ELEVADO      : tokenLexema.append( "T_ELEVADO" ); break;
            case T_MMC          : tokenLexema.append( "T_MMC" ); break;
            case T_DIVIDIDO     : tokenLexema.append( "T_DIVIDIDO" ); break;
            case T_VEZES        : tokenLexema.append( "T_VEZES" ); break;
            case T_MENOS        : tokenLexema.append( "T_MENOS" ); break;
            case T_MAIS         : tokenLexema.append( "T_MAIS" ); break;
            case T_DIFERENTE    : tokenLexema.append( "T_DIFERENTE" ); break;
            default             : tokenLexema.append( "N/A" ); break;
        }

        acumulaToken( tokenLexema.toString() + " ( " + lexema + " )" );
        tokenLexema.append( lexema );
    }

    private static void abreArquivo() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

        FiltroJozS4 filtro = new FiltroJozS4();

        fileChooser.addChoosableFileFilter( filtro );
        int result = fileChooser.showOpenDialog( null );

        if( result == JFileChooser.CANCEL_OPTION ) {
            return;
        }

        arqFonte = fileChooser.getSelectedFile();
        abreFonte( arqFonte );

    }

    private static boolean abreFonte( File fileName ) {

        if( arqFonte == null || fileName.getName().trim().equals( "" ) ) {
            JOptionPane.showMessageDialog( null, "Nome de Arquivo Inv�lido", "Nome de Arquivo Inv�lido", JOptionPane.ERROR_MESSAGE );
            return false;
        } else {
            linhaAtual = 1;
            try {
                FileReader fr = new FileReader( arqFonte );
                rdFonte = new BufferedReader( fr );
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        }
    }

    private static void abreDestino() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

        FiltroJozS4 filtro = new FiltroJozS4();

        fileChooser.addChoosableFileFilter( filtro );
        int result = fileChooser.showSaveDialog( null );

        if( result == JFileChooser.CANCEL_OPTION ) {
            return;
        }

        arqDestino = fileChooser.getSelectedFile();
    }


    private static boolean gravaSaida( File fileName ) {

        if( arqDestino == null || fileName.getName().trim().equals( "" ) ) {
            JOptionPane.showMessageDialog( null, "Nome de Arquivo Inv�lido", "Nome de Arquivo Inv�lido", JOptionPane.ERROR_MESSAGE );
            return false;
        } else {
            FileWriter fw;
            try {
                fw = new FileWriter( arqDestino );
                BufferedWriter bfw = new BufferedWriter( fw );
                bfw.write( tokensIdentificados.toString() );
                bfw.write( regrasReconhecidas.toString() );
                bfw.write( "\n\nStatus da Compila��o:\n\n" );
                bfw.write( mensagemDeErro );
                String codigo = codigoInicioPython.toString().concat(codigoPython.toString() );
                bfw.write( codigo );
                bfw.close();
                JOptionPane.showMessageDialog( null, "Arquivo Salvo: " + arqDestino, "Salvando Arquivo", JOptionPane.INFORMATION_MESSAGE );
            } catch (IOException e) {
                JOptionPane.showMessageDialog( null, e.getMessage(), "Erro de Entrada/Sa�da", JOptionPane.ERROR_MESSAGE );
            }
            return true;
        }
    }

    public static void exibeSaida() {

        JTextArea texto = new JTextArea();
        texto.append( tokensIdentificados.toString() );
        JOptionPane.showMessageDialog(null, texto, "An�lise L�xica", JOptionPane.INFORMATION_MESSAGE );

        texto.setText( regrasReconhecidas.toString() );
        texto.append( "\n\nStatus da Compila��o:\n\n" );
        texto.append( mensagemDeErro );

        JOptionPane.showMessageDialog(null, texto, "Resumo da Compila��o", JOptionPane.INFORMATION_MESSAGE );
    }

    public static void acumulaToken( String tokenIdentificado ) {

        tokensIdentificados.append( tokenIdentificado );
        tokensIdentificados.append( "\n" );

    }

    public static void acumulaRegra( String regraReconhecida ) {

        regrasReconhecidas.append( regraReconhecida );
        regrasReconhecidas.append( "\n" );

    }

    static void regraSemantica( int numeroRegra ) {
        System.out.println( "Regra Semantica " + numeroRegra );

        switch ( numeroRegra ) {
            case  0: codigoInicioPython.append( "\n\nimport os\nimport sys\nimport glob\nimport string\n\n" );
                codigoPython.append( "\ndef main( ):\n" );
                nivelIdentacao++;
                codigoPython.append( tabulacao( nivelIdentacao ) );
                codigoPython.append( "'''Bochincho Python compiler�'''\n\n\n");
                break;
            case  1: codigoPython.append( tabulacao( nivelIdentacao ) );
                codigoPython.append( "pass\n\n" );
                codigoPython.append( "if __name__ == '__main__':\n" );
                codigoPython.append( tabulacao( nivelIdentacao ) );
                codigoPython.append( "main( )\n" );
                if ( temMMC ) {
                    codigoInicioPython.append( "\n\ndef mmc( x, y ):\n" );
                    codigoInicioPython.append( "    n1 = x\n" );
                    codigoInicioPython.append( "    m1 = y\n" );
                    codigoInicioPython.append( "    while (m1 != n1):\n" );
                    codigoInicioPython.append( "        if ( m1 > n1 ):\n" );
                    codigoInicioPython.append( "            n1 += x\n" );
                    codigoInicioPython.append( "        else:\n" );
                    codigoInicioPython.append( "            m1 += y\n" );
                    codigoInicioPython.append( "    return m1\n\n" );
                }
                break;
            case 23: exp_right = pilhaSemantica.pop().getCodigo(); // variavel
                termo     = pilhaSemantica.pop().getCodigo(); // expressao
                codigoPython.append( tabulacao( nivelIdentacao ) );
                codigoPython.append( exp_right + " = " + termo + "\n" );
                break;

            case  25: pilhaSemantica.push( ultimoLexema.toLowerCase(), 25 ); // Empilha Identificador (variavel encontrada)
                break;
            case  32: termo     = pilhaSemantica.pop().getCodigo();
                exp_right = pilhaSemantica.pop().getCodigo();
                exp_left  = exp_right + " + " + termo;
                pilhaSemantica.push( exp_left, 32 );
                break;
            case  33: termo     = pilhaSemantica.pop().getCodigo();
                exp_right = pilhaSemantica.pop().getCodigo();
                exp_left  = exp_right + " - " + termo;
                pilhaSemantica.push( exp_left, 33 );
                break;
            case  35: termo     = pilhaSemantica.pop().getCodigo();
                exp_right = pilhaSemantica.pop().getCodigo();
                exp_left  = exp_right + " * " + termo;
                pilhaSemantica.push( exp_left, 35 );
                break;
            case  36: termo     = pilhaSemantica.pop().getCodigo();
                exp_right = pilhaSemantica.pop().getCodigo();
                exp_left  = exp_right + " / " + termo;
                pilhaSemantica.push( exp_left, 36 );
                break;
            case  37: termo     = pilhaSemantica.pop().getCodigo();
                exp_right = pilhaSemantica.pop().getCodigo();
                exp_left  = "mmc( " + exp_right + ", " + termo + " )";
                pilhaSemantica.push( exp_left, 37 );
                temMMC = true;
                break;
            case  43: exp_right = pilhaSemantica.pop().getCodigo();
                exp_left = "( " + exp_right + " )";
                pilhaSemantica.push( exp_left, 43 );
            case  44: break;
            case  45: break;
            case  46: pilhaSemantica.push( ultimoLexema.toLowerCase(), 25 ); // Empilha Identificador (variavel encontrada)
                break;
        }
    }

    static String tabulacao( int qtd ) {

        StringBuffer sb = new StringBuffer();
        for ( int i=0; i<qtd; i++ ) {
            sb.append( "    " );
        }
        return sb.toString();
    }

}

/**
 * Classe Interna para cria��o de filtro de sele��o
 */
class FiltroJozS4 extends FileFilter {

    public boolean accept(File arg0) {
        if(arg0 != null) {
            if(arg0.isDirectory()) {
                return true;
            }
            if( getExtensao(arg0) != null) {
                if ( getExtensao(arg0).equalsIgnoreCase( "gdr" ) ) {
                    return true;
                }
            };
        }
        return false;
    }

    /**
     * Retorna quais extens�es poder�o ser escolhidas
     */
    public String getDescription() {
        return "*.gdr";
    }

    /**
     * Retorna a parte com a extens�o de um arquivo
     */
    public String getExtensao(File arq) {
        if(arq != null) {
            String filename = arq.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase();
            };
        }
        return null;
    }
}
