/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservas;

/**
 *
 * @author Umberto
 */
public class Impressao {
    private int linha;
    private String classetoken;
    private String lexema;
    
    
    //construtor
    public Impressao (int linha,String classetoken, String lexema ){
        this.linha=linha;
        this.lexema=lexema;
        this.classetoken=classetoken;
    } 
  
    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public String getClassetoken() {
        return classetoken;
    }

    public void setClassetoken(String classetoken) {
        this.classetoken = classetoken;
    }
    
    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
}
