
package Reservas;

import java.util.ArrayList;
import java.util.List;

public class Analisador {
    // Método para dividir o texto em linhas
    public ArrayList<String> dividirTextoEmLinhas(String textoTextArea) {
        ArrayList<String> linhas = new ArrayList<>();
        if (textoTextArea != null && !textoTextArea.isEmpty()) {
            String[] linhasTexto = textoTextArea.split("\\n");
            for (String linha : linhasTexto) {
                linhas.add(linha);
            }
        }
        return linhas;
    }

    // Método para analisar uma linha de texto e retornar os tokens
    public static ArrayList<String> analisar(String linha) {
        List<String> tokens = new ArrayList<>();
        int indice = 0;

        while (indice < linha.length()) {
            // Ignora espaços em branco
            while (indice < linha.length() && Character.isWhitespace(linha.charAt(indice))) {
                indice++;
            }

            // Se atingir o final da linha, interrompe o loop
            if (indice == linha.length()) {
                break;
            }

            char caractereAtual = linha.charAt(indice);

            // Verifica se o caractere atual é o início de uma string
            if (caractereAtual == '\'') {
                int indiceFimString = indice + 1;
                // Procura pelo fechamento da string
                while (indiceFimString < linha.length() && linha.charAt(indiceFimString) != '\'') {
                    indiceFimString++;
                }
                if (indiceFimString < linha.length()) {
                    // Adiciona a string como token
                    String stringToken = linha.substring(indice, indiceFimString + 1);
                    tokens.add(stringToken);
                    indice = indiceFimString + 1;
                } else {
                    // Trata a string malformada como um token de erro
                    String stringToken = linha.substring(indice);
                    tokens.add(stringToken);
                    indice = linha.length();
                }
                continue;
            }

            // Verifica se o caractere atual é um símbolo especial
            if (eSimboloEspecial(caractereAtual)) {
                tokens.add(Character.toString(caractereAtual));
                indice++;
                continue;
            }

            // Verifica operadores compostos como <>
            if (caractereAtual == '<') {
                if (indice + 1 < linha.length() && linha.charAt(indice + 1) == '>') {
                    tokens.add("<>");
                    indice += 2;
                    continue;
                } else {
                    tokens.add(Character.toString(caractereAtual));
                    indice++;
                    continue;
                }
            } else if (caractereAtual == ':') {
                // Verifica operadores compostos como :=
                if (indice + 1 < linha.length() && linha.charAt(indice + 1) == '=') {
                    tokens.add(":=");
                    indice += 2;
                    continue;
                } else {
                    tokens.add(Character.toString(caractereAtual));
                    indice++;
                    continue;
                }
            } else if (Character.isLetter(caractereAtual)) {
                // Identifica palavras e identificadores
                int indiceFimPalavra = indice + 1;
                while (indiceFimPalavra < linha.length() && (Character.isLetterOrDigit(linha.charAt(indiceFimPalavra)) || linha.charAt(indiceFimPalavra) == '_')) {
                    indiceFimPalavra++;
                }
                String palavra = linha.substring(indice, indiceFimPalavra);
                tokens.add(palavra);
                indice = indiceFimPalavra;
                continue;
            } else if (Character.isDigit(caractereAtual)) {
                // Identifica números
                int indiceFimNumero = indice + 1;
                while (indiceFimNumero < linha.length() && Character.isDigit(linha.charAt(indiceFimNumero))) {
                    indiceFimNumero++;
                }
                String numero = linha.substring(indice, indiceFimNumero);
                tokens.add(numero);
                indice = indiceFimNumero;
                continue;
            } else if (eTokenErro(caractereAtual)) {
                // Identifica tokens de erro
                int indiceFimPalavra = indice + 1;
                while (indiceFimPalavra < linha.length() && (Character.isLetterOrDigit(linha.charAt(indiceFimPalavra)) || linha.charAt(indiceFimPalavra) == '_')) {
                    indiceFimPalavra++;
                }
                String palavra = linha.substring(indice, indiceFimPalavra);
                tokens.add(palavra);
                indice = indiceFimPalavra;
                continue;
            } else {
                // Qualquer outro caractere é considerado um token por si só
                tokens.add(Character.toString(caractereAtual));
                indice++;
            }
        }

        return (ArrayList<String>) tokens;
    }

    // Método para verificar se um caractere é um símbolo especial
    private static boolean eSimboloEspecial(char c) {
        return (c == '(' || c == ')' || c == '[' || c == ']' || c == '/' || c == ',' || c == '+' || c == '-' || c == '*' || c == ';' || c == '{' || c == '}');
    }

    // Método para verificar se um caractere é considerado um token de erro
    private static boolean eTokenErro(char c) {
        return (c == '_' || c == '@' || c == '!' || c == '?');
    }

    // Método para verificar o tipo de palavra (token)
    public static String verificarTipoPalavra(String token) {
        PalavrasReservadas palavrasReservadas = new PalavrasReservadas(); // Supondo que eu tenha um objeto PalavrasReservadas
        // Operadores aritméticos
        if (palavrasReservadas.Soma.equals(token) || palavrasReservadas.Subtraccao.equals(token)
                || palavrasReservadas.Multiplicacao.equals(token) || palavrasReservadas.Divisao.equals(token)) {
            return "Operador Aritmetico";
        } // Operadores relacionais
        else if (palavrasReservadas.Igual.equals(token) || palavrasReservadas.Diferente.equals(token)
                || palavrasReservadas.Menor.equals(token) || palavrasReservadas.Maior.equals(token)
                || palavrasReservadas.Maior_igual.equals(token) || palavrasReservadas.Menor_igual.equals(token)) {
            return "Operador Relacional";
        } // Delimitadores
        else if (palavrasReservadas.Abrir_parenteses.equals(token) || palavrasReservadas.Fechar_parenteses.equals(token)
                || palavrasReservadas.Abrir_conchetes.equals(token) || palavrasReservadas.Fechar_conchetes.equals(token)
                || palavrasReservadas.Abrir_chavetas.equals(token) || palavrasReservadas.Fechar_chaveta.equals(token)
                || palavrasReservadas.Ponto_virgula.equals(token) || palavrasReservadas.Ponto.equals(token)
                || palavrasReservadas.Virgula.equals(token) || palavrasReservadas.Dois_pontos.equals(token)
                || palavrasReservadas.aspas_simples.equals(token)) {
            return "Delimitador";
        } // Operadores lógicos
        else if (palavrasReservadas.Ou.equals(token) || palavrasReservadas.E.equals(token) || palavrasReservadas.Nao.equals(token)) {
            return "Operador Logico";
        } // Controle de fluxo
        else if (palavrasReservadas.Se.equals(token) || palavrasReservadas.Entao.equals(token) || palavrasReservadas.token_else.equals(token)
                || palavrasReservadas.Enquanto.equals(token) || palavrasReservadas.Faca.equals(token) || palavrasReservadas.Inicio.equals(token)
                || palavrasReservadas.Fim.equals(token)) {
            return "palavra reservada";
        } // Entrada e saída
        else if (palavrasReservadas.Le.equals(token) || palavrasReservadas.Escreve.equals(token) || palavrasReservadas.Writeln.equals(token)
                || palavrasReservadas.Readln.equals(token)) {
            return "Entrada ou saida";
        } // Declaração
        else if (palavrasReservadas.Variavel.equals(token) || palavrasReservadas.Matriz.equals(token)
                || palavrasReservadas.Funcao.equals(token) || palavrasReservadas.Procede.equals(token)
                || palavrasReservadas.Programa.equals(token)) {
            return "Declaracao";
        } // Tipos de dados
        else if (palavrasReservadas.Verdadeiro.equals(token) || palavrasReservadas.Falso.equals(token)
                || palavrasReservadas.Char.equals(token) || palavrasReservadas.Inteiro.equals(token) || palavrasReservadas.Booleano.equals(token)) {
            return "Tipo de Dados";
        } // Erro
        else if (palavrasReservadas.Erro.equals(token)) {
            return "Erro";
        } // Verifica se é um número
        else if (TodosDigitos(token)) {
            return "Digito";
        } // Verifica se é um identificador
        else if (Identificador(token)) {
            return "Identificador";
       } // Sinal de atribuição
        else if (SinalAtribuicao(token)) {
            return "Sinal de atribuicacao";
        } // Verifica se é uma string
        else if (eString(token)) {
            return "String";
        } // Caso não se encaixe em nenhum dos casos anteriores
        else {
            return "Erro";
        }
    }

    // Método para verificar se uma palavra contém apenas dígitos
    public static boolean TodosDigitos(String palavra) {
try {
Integer.parseInt(palavra);
return true;
} catch (NumberFormatException e) {
return false;
}

    }
// Método para verificar se uma palavra é um sinal de atribuição
public static boolean SinalAtribuicao(String palavra) {
    return ":=".equals(palavra);
}

// Método para verificar se uma palavra é um identificador válido
public static boolean Identificador(String palavra) {
    if (palavra == null || palavra.isEmpty() || !Character.isLetter(palavra.charAt(0))) {
        return false;
    }

    for (int i = 1; i < palavra.length(); i++) {
        char c = palavra.charAt(i);
        if (!Character.isLetterOrDigit(c) && c != '_') {
            return false;
        }
    }

    return true;
}

// Método para verificar se uma palavra é uma string delimitada por aspas simples
private static boolean eString(String palavra) {
    return palavra != null && palavra.length() >= 2 && palavra.startsWith("'") && palavra.endsWith("'");
}


}

