package screen;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class MinhaTela extends JFrame {
    private JTextField numero1 = new JTextField();
    private JTextField numero2 = new JTextField();
    private JComboBox<Operacao> operacao = new JComboBox<Operacao>();
    private JButton botao = new JButton();

    public MinhaTela(){
        super();
        initialize();
    }

    boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean validaFormulario(){
        if(!isNumeric(numero1.getText())){
            JOptionPane.showMessageDialog(null, "O campo número 1 deve ser numérico");
            numero1.requestFocus();
            return false;
        }
        if(!isNumeric(numero2.getText())){
            JOptionPane.showMessageDialog(null, "O campo número 2 deve ser numérico");
            numero2.requestFocus();
            return false;
        }
        return true;
    }

    private void initialize()
    {
        this.setTitle("Minha Tela");
        this.setSize(300, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        numero1.setBounds(10, 10, 270, 30);
        numero1.setToolTipText("Digite o primeiro número");
        this.add(numero1);

        numero2.setBounds(10, 110, 270, 30);
        numero2.setToolTipText("Digite o primeiro número");
        this.add(numero2);

        operacao.setBounds(10, 60, 270, 30);
        operacao.addItem(new Operacao('+', "Soma"));
        operacao.addItem(new Operacao('-', "Subtração"));
        operacao.addItem(new Operacao('*', "Multiplicação"));
        operacao.addItem(new Operacao('/', "Divisão"));
        this.add(operacao);

        botao.setBounds(100, 160, 100, 30);
        botao.setText("Calcular");
        botao.addActionListener(e -> {
            if(validaFormulario() == false){
                return;
            }

            Operacao op = (Operacao) operacao.getSelectedItem();
            double n1 = Double.parseDouble(numero1.getText());
            double n2 = Double.parseDouble(numero2.getText());
            double resultado = 0;
            switch(op.getSinal()){
                case '+':
                    resultado = n1 + n2;
                    break;
                case '-':
                    resultado = n1 - n2;
                    break;
                case '*':
                    resultado = n1 * n2;
                    break;
                case '/':
                    if(n2 == 0){
                        JOptionPane.showMessageDialog(null, "Não é possível dividir por zero");
                        return;
                    }
                    resultado = n1 / n2;
                    break;
            }
            JOptionPane.showMessageDialog(null, "O resultado da " + op.getDescricao() + " é: " + resultado);
        });
        this.add(botao);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new MinhaTela();
    }
}
