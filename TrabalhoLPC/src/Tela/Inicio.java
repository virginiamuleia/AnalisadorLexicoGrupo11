
package Tela;
import Reservas.Analisador;
import Reservas.Impressao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class Inicio extends javax.swing.JFrame implements ActionListener {

    JPanel painel = new JPanel();
    JPanel painel1 = new JPanel();
    JPanel painelnumeroslinhas = new JPanel();
    JTextArea areaTexto = new JTextArea();
    JScrollPane areaRolagem = new JScrollPane(areaTexto);

    JTable tabela = new JTable();
    JScrollPane rolagem = new JScrollPane(tabela);
    Object[][] dados = {};
    Object[] colunas = {"Linha", "Tokens", "Lexema"};
    DefaultTableModel modeloTabela = new DefaultTableModel(dados, colunas);

    JFileChooser abrirArquivo = new JFileChooser();

    JButton btnOpen = new JButton();
    JButton btnlcomp = new JButton();
    JButton btnsalv = new JButton();
    JButton btnlimpar = new JButton();
    JButton btnSair = new JButton();

    public Inicio() {
        setTitle("AnalisadorLexico");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 900);
        this.setLocationRelativeTo(null);

        setLayout(null);

        JFrame frame = new JFrame("Analisador Lexico");
        JLabel label = new JLabel("Analizador lexico");
        label.setBounds(500, 20, 500, 30);
        Font font = new Font("Arial", Font.BOLD, 22);
        label.setFont(font);
        painel.add(label);

        // Botoes
        btnOpen.setBounds(25, 45, 40, 40);
        btnlcomp.setBounds(65, 45, 40, 40);
        //btnlcomp.setBackground(Color.pink);

        // Carregar o ícone
        ImageIcon icone = new ImageIcon(getClass().getResource("icons.png"));
        btnlcomp.setIcon(icone);
        ImageIcon icones = new ImageIcon(getClass().getResource("iconees.png"));
        btnsalv.setIcon (icones);
        ImageIcon iconees = new ImageIcon(getClass().getResource("icons30.png"));
        btnOpen.setIcon(iconees);
        ImageIcon iconess = new ImageIcon(getClass().getResource("icons824.png"));
        btnlimpar.setIcon(iconess);
        ImageIcon ics = new ImageIcon(getClass().getResource("icons830.png"));
        btnSair.setIcon(ics);
         

        btnsalv.setBounds(105, 45, 40, 40);
        btnlimpar.setBounds(145, 45, 40, 40);
        btnSair.setBounds(1120, 785, 40, 40);
       // rolagem.setBounds(30, 530, 1130, 250);

        // Adicionar os botões ao painel
        painel.add(btnOpen);
        painel.add(btnlcomp);
        painel.add(btnsalv);
        painel.add(btnlimpar);
        painel.add(btnSair);

        // Adicionar action listeners aos botões
        btnOpen.addActionListener(this);
        btnlcomp.addActionListener(this);
        btnsalv.addActionListener(this);
        btnlimpar.addActionListener(this);
        btnSair.addActionListener(this);

        painel.setBounds(0, 0, 1200, 880);
        painel.setBackground(new Color (195,187,240));
        painel.setLayout(null);

        JTextArea numerosLinhas = new JTextArea(50, 3);
        numerosLinhas.setEditable(false);
        areaTexto.setWrapStyleWord(true);

        painelnumeroslinhas.setBounds(30, 90, 30, 400);
        painelnumeroslinhas.setBackground(Color.LIGHT_GRAY);
        painelnumeroslinhas.add(numerosLinhas, BorderLayout.CENTER);
        painelnumeroslinhas.setLayout(new BorderLayout());
        painelnumeroslinhas.add(numerosLinhas, BorderLayout.NORTH);
        
        areaTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateNumbers();
            }

            private void updateNumbers() {
                SwingUtilities.invokeLater(() -> {
                    StringBuilder numbers = new StringBuilder();
                    int totalLines = areaTexto.getLineCount();
                    for (int i = 1; i <= totalLines; i++) {
                        numbers.append(i).append("\n");
                    }
                    numerosLinhas.setText(numbers.toString());
                });
            }
        });

        tabela.setModel(modeloTabela);
        rolagem.setBounds(30, 530, 1130, 250);
        painel.add(rolagem);
        setVisible(true);

        areaTexto.setBounds(60, 90, 1100, 400);// aumentar
        areaTexto.setBackground(Color.white);
        areaRolagem.setBounds(60, 90, 1100, 400); // ajusta a area de testo e a rolagem
        painel.add(areaRolagem);// painel para os numeros na area do testo
        painel.add(painelnumeroslinhas);
        painel.add(painel1);
        add(painel);
    }

    public void listar() {
        Analisador analisador = new Analisador();
        List<Impressao> listaTokens = new ArrayList<>();
        Impressao token;
        List<String> linhas = analisador.dividirTextoEmLinhas(areaTexto.getText());
        int numeroLinha = 0;

        for (String linha : linhas) {
            numeroLinha++;
            List<String> tokensLinha = analisador.analisar(linha);
            for (String lexema : tokensLinha) {
                String tipoToken = analisador.verificarTipoPalavra(lexema);
                token = new Impressao(numeroLinha, tipoToken, lexema);
                listaTokens.add(token); // adicionar os tokens a tabela
                modeloTabela.addRow(new Object[]{numeroLinha, tipoToken, lexema});
            }
        }
    }
    
    

    public void abrirArquivo() {
        if (abrirArquivo.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        areaTexto.setText(abrirArquivo.getSelectedFile().getAbsolutePath());

        StringBuilder texto = new StringBuilder();
        try {
            Scanner scan = new Scanner(abrirArquivo.getSelectedFile());
            while (scan.hasNextLine()) {
               texto.append(scan.nextLine()).append("\n");
            }

            areaTexto.setText(texto.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível ler o arquivo solicitado.");
            e.printStackTrace();
        }
    }

    public void salvarArquivo() {
        if (abrirArquivo.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(abrirArquivo.getSelectedFile());
            Scanner scan = new Scanner(areaTexto.getText());
            while (scan.hasNextLine()) {
                pw.print(scan.nextLine());

                if (scan.hasNextLine()) {
                    pw.println();
                }
            }
            pw.flush();
            JOptionPane.showMessageDialog(this, "Salvo");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar o arquivo.");
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        DefaultTableModel modelo = new DefaultTableModel();
    }

    public void Limpar() {
        areaTexto.setText("");
        modeloTabela.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnOpen) {
            abrirArquivo();
        }

        if (ae.getSource() == btnsalv) {
            salvarArquivo();
        }

        if (ae.getSource() == btnlcomp) {
            listar();
        }

        if (ae.getSource() == btnlimpar) {
            Limpar();
        }
        if(ae.getSource() == btnSair){
            
            new Inicio().setVisible(false);
            dispose();
  
        }
    }

    public static void main(String[] args) {
        new Inicio();
    }
}
