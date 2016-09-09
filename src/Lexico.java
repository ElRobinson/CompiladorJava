import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

/**
 * Lexico - Classe para implementa��o de um analisador L�xico b�sico
 *
 * @author Ricardo Ferreira de Oliveira
 *
 * Gram�tica:
 *
 * <S> 			::= 'INICIO' <comandos> 'FIM'
 * <comandos> 	::= <comando> ';' <comando>
 *              |   <comando>
 * <comando>    ::= 'ESCREVE' '(' <id> ')'
 *              | 'ESCREVE' '(' <numero> ')'
 *              | 'ESCREVE' '(' <id> ',' <numero> ')'
 *              | 'LER' '(' <id> ')'
 *              | 'LIMPATELA' '(' ')'
 *              | <id> '<-' <numero>
 *              | 'SE' <condicao> <comandos>
 *              | 'ENQUANTO' <condicao> <comandos> *
 * <condicao>   ::= <numero> '>' <numero>
 *              |   <numero> '>=' <numero>
 *              |   <numero> '<=' <numero>
 *              |   <numero> '<' <numero>
 *              |   <numero> '==' <numero>
 *              |   <numero> '<>' <numero>
 *              | 'FOR' <variavel> '=' <expressao>
 *              | 'TO' <expressao> <comandos>
 * <numero>     ::= [0-9]+
 * <id>         ::= [A-Z][A-Z,0-9,_]*
 *
 * Com esta gram�tica, poderemos elaborar programas joz como o abaixo:
 *
 * INICIO
 *   LIMPATELA();
 *   LER( SOMA );
 *   ESCREVE( SOMA, 5 );
 *   ESCREVE( R2D2 );
 *   ESCREVE( C3PO );
 *   ESCREVE( 5 );
 *   ESCREVE( 2 )
 * FIM
 *
 */

public class Lexico {

    static final int T_INICIO          =   1;
    static final int T_FIM             =   2;
    static final int T_ESCREVE         =   3;
    static final int T_NUMERO          =   4;
    static final int T_PONTO_VG        =   5;
    static final int T_ABRE_PAR        =   6;
    static final int T_FECHA_PAR       =   7;
    static final int T_LIMPA_TELA      =   8;
    static final int T_ID			   =   9;
    static final int T_LER			   =  10;
    static final int T_VIRGULA         =  11;
    static final int T_ATRIBUICAO      =  12;

    static final int T_ENQUANTO        =  13;
    static final int T_MAIOR           =  14;
    static final int T_MENOR           =  15;
    static final int T_MAIOR_IGUAL     =  16;
    static final int T_MENOR_IGUAL     =  17;
    static final int T_IGUAL           =  18;
    static final int T_DIFERENTE       =  19;
    static final int T_SE              =  20;

    static final int T_FOR             =  21;
    static final int T_TO              =  22;


    static final int T_FIM_FONTE       =  90;
    static final int T_ERRO_LEX        =  98;
    static final int T_NULO            =  99;

    static final int FIM_ARQUIVO       =  26;

    static File arqFonte;
    static BufferedReader rdFonte;
    static File arqDestino;

    static char   lookAhead;
    static int    token;
    static String lexema;
    static int    ponteiro;
    static String linhaFonte;
    static int    linhaAtual;
    static int    colunaAtual;
    static String mensagemDeErro;
    static StringBuffer tokensIdentificados = new StringBuffer();

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
            mensagemDeErro = "";

            movelookAhead();

            while ( ( token != T_FIM_FONTE ) && ( token != T_ERRO_LEX ) ) {
                buscaProximoToken();
                mostraToken();
            }

            if ( token == T_ERRO_LEX ) {
                JOptionPane.showMessageDialog( null, mensagemDeErro, "Erro L�xico!", JOptionPane.ERROR_MESSAGE );
            } else {
                JOptionPane.showMessageDialog( null, "An�lise L�xica terminada sem erros l�xicos", "An�lise L�xica terminada!", JOptionPane.INFORMATION_MESSAGE );
            }

            exibeTokens();

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

    static void fechaFonte() throws IOException
    {
        rdFonte.close();
    }

    static void movelookAhead() throws IOException
    {

        if ( ( ponteiro + 1 ) > linhaFonte.length() ) {

            linhaAtual++;
            ponteiro = 0;


            if ( ( linhaFonte = rdFonte.readLine() ) == null ) {
                lookAhead = FIM_ARQUIVO;
            } else {

                StringBuffer sbLinhaFonte = new StringBuffer( linhaFonte );
                sbLinhaFonte.append( '\13' ).append( '\10' );
                linhaFonte = sbLinhaFonte.toString();

                lookAhead = linhaFonte.charAt( ponteiro );
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

    static void buscaProximoToken() throws IOException
    {
        int i, j;

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
                    ( ( lookAhead >= '0' ) && ( lookAhead <= '9' ) ) ||
                    ( lookAhead ==  '_' ))
            {
                sbLexema.append( lookAhead );
                movelookAhead();
            }

            lexema = sbLexema.toString();

        /* Classifico o meu token como palavra reservada ou id */
            if ( lexema.equals( "ESCREVE" ) )
                token = T_ESCREVE;
            else if ( lexema.equals( "INICIO" ) )
                token = T_INICIO;
            else if ( lexema.equals( "FIM" ) )
                token = T_FIM;
            else if ( lexema.equals( "LIMPATELA" ) )
                token = T_LIMPA_TELA;
            else if (lexema.equals("SE"))
                token = T_SE;
            else if ( lexema.equals( "ENQUANTO" ) )
                token = T_ENQUANTO;
            else if (lexema.equals("FOR"))
                token = T_FOR;
            else if (lexema.equals("TO"))
                token = T_TO;
            else if ( lexema.equals( "LER" ) )
                token = T_LER;
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
            if ( lookAhead == '.' ) {
                sbLexema.append( lookAhead );
                movelookAhead();
                while ( ( lookAhead >= '0' ) && ( lookAhead <= '9' ) )
                {
                    sbLexema.append( lookAhead );
                    movelookAhead();
                }
            }
            token = T_NUMERO;
        } else if ( lookAhead == '(' ){
            sbLexema.append( lookAhead );
            token = T_ABRE_PAR;
            movelookAhead();

        } else if ( lookAhead == ')' ){
            sbLexema.append( lookAhead );
            token = T_FECHA_PAR;
            movelookAhead();
        } else if ( lookAhead == '<' ){
            sbLexema.append( lookAhead );
            token = T_MENOR;
            movelookAhead();
            if ( lookAhead == '-' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_ATRIBUICAO;
            } else if ( lookAhead == '=' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_MENOR_IGUAL;
            } else if ( lookAhead == '>' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_DIFERENTE;
            }
        } else if ( lookAhead == '>' ){
            sbLexema.append( lookAhead );
            token = T_MAIOR;
            movelookAhead();
            if ( lookAhead == '=' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_MAIOR_IGUAL;
            }
        } else if ( lookAhead == '=' ){
            sbLexema.append( lookAhead );
            token = T_ERRO_LEX;
            movelookAhead();
            if ( lookAhead == '=' ){
                sbLexema.append( lookAhead );
                movelookAhead();
                token = T_IGUAL;
            }
        } else if ( lookAhead == ';' ){
            sbLexema.append( lookAhead );
            token = T_PONTO_VG;
            movelookAhead();
        } else if ( lookAhead == ',' ) {
            sbLexema.append(lookAhead);
            token = T_VIRGULA;
            movelookAhead();
        } else if(lookAhead == '=' && token == T_ID ){
            sbLexema.append(lookAhead);
            token = T_IGUAL;
            movelookAhead();
        } else if ( lookAhead == FIM_ARQUIVO ){
            token = T_FIM_FONTE;
        } else {
            token = T_ERRO_LEX;
            mensagemDeErro = "Erro Léxico na linha: " + linhaAtual + "\nReconhecido ao atingir a coluna: " + colunaAtual + "\nLinha do Erro: <" + linhaFonte + ">\nToken desconhecido: " + lookAhead;
            sbLexema.append( lookAhead );
        }

        lexema = sbLexema.toString();
    }



    static void mostraToken()
    {

        StringBuffer tokenLexema = new StringBuffer( "" );

        switch ( token ) {
            case T_INICIO         : tokenLexema.append( "T_INICIO" ); break;
            case T_FIM            : tokenLexema.append( "T_FIM" ); break;
            case T_ESCREVE        : tokenLexema.append( "T_ESCREVE" ); break;
            case T_ABRE_PAR       : tokenLexema.append( "T_ABRE_PAR" ); break;
            case T_FECHA_PAR      : tokenLexema.append( "T_FECHA_PAR" ); break;
            case T_NUMERO         : tokenLexema.append( "T_NUMERO" ); break;
            case T_PONTO_VG       : tokenLexema.append( "T_PONTO_VG" ); break;
            case T_LIMPA_TELA     : tokenLexema.append( "T_LIMPA_TELA" ); break;
            case T_ID             : tokenLexema.append( "T_ID" ); break;
            case T_LER            : tokenLexema.append( "T_LER" ); break;
            case T_VIRGULA        : tokenLexema.append( "T_VIRGULA" ); break;
            case T_ATRIBUICAO     : tokenLexema.append( "T_ATRIBUICAO" ); break;
            case T_ENQUANTO       : tokenLexema.append( "T_ENQUANTO" ); break;
            case T_FOR            : tokenLexema.append( "T_FOR" ); break;
            case T_TO             : tokenLexema.append( "T_TO" ); break;
            case T_SE             : tokenLexema.append( "T_SE" ); break;
            case T_MAIOR          : tokenLexema.append( "T_MAIOR" ); break;
            case T_MENOR          : tokenLexema.append( "T_MENOR" ); break;
            case T_MAIOR_IGUAL    : tokenLexema.append( "T_MAIOR_IGUAL" ); break;
            case T_MENOR_IGUAL    : tokenLexema.append( "T_MENOR_IGUAL" ); break;
            case T_IGUAL          : tokenLexema.append( "T_IGUAL" ); break;
            case T_DIFERENTE      : tokenLexema.append( "T_DIFERENTE" ); break;
            case T_FIM_FONTE      : tokenLexema.append( "T_FIM_FONTE" ); break;
            case T_ERRO_LEX       : tokenLexema.append( "T_ERRO_LEX" ); break;
            case T_NULO           : tokenLexema.append( "T_NULO" ); break;
            default               : tokenLexema.append( "N/A" ); break;
        }
        System.out.println( tokenLexema.toString() + " ( " + lexema + " )" );
        acumulaToken( tokenLexema.toString() + " ( " + lexema + " )" );
        tokenLexema.append( lexema );
    }

    private static void abreArquivo() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

        FiltroJoz3 filtro = new FiltroJoz3();

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

        FiltroJoz3 filtro = new FiltroJoz3();

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
                System.out.println( arqDestino.toString() );
                System.out.println( tokensIdentificados.toString() );
                fw = new FileWriter( arqDestino );
                BufferedWriter bfw = new BufferedWriter( fw );
                bfw.write( tokensIdentificados.toString() );
                bfw.close();
                JOptionPane.showMessageDialog( null, "Arquivo Salvo: " + arqDestino, "Salvando Arquivo", JOptionPane.INFORMATION_MESSAGE );
            } catch (IOException e) {
                JOptionPane.showMessageDialog( null, e.getMessage(), "Erro de Entrada/Sa�da", JOptionPane.ERROR_MESSAGE );
            }
            return true;
        }
    }

    public static void exibeTokens() {

        JTextArea texto = new JTextArea();
        texto.append( tokensIdentificados.toString() );
        JOptionPane.showMessageDialog(null, texto, "Tokens Identificados (token/lexema)", JOptionPane.INFORMATION_MESSAGE );
    }

    public static void acumulaToken( String tokenIdentificado ) {

        tokensIdentificados.append( tokenIdentificado );
        tokensIdentificados.append( "\n" );

    }

}

/**
 * Classe Interna para cria��o de filtro de sele��o
 */
class FiltroJoz3 extends FileFilter {

    public boolean accept(File arg0) {
        if(arg0 != null) {
            if(arg0.isDirectory()) {
                return true;
            }
            if( getExtensao(arg0) != null) {
                if ( getExtensao(arg0).equalsIgnoreCase( "joz" ) ) {
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
        return "*.joz";
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