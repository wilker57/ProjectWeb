package screen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import data.Usuario;
import utils.SistemaAgenda;

public class Login extends JFrame {
    JLabel loginLabel = new JLabel();
    JTextField loginField = new JTextField();
    JLabel senhaLabel = new JLabel();
    JPasswordField senhaField = new JPasswordField("senha0");
    JButton loginButton = new JButton();

    public Login(){
        super();
        initialize();
        SistemaAgenda.desserializarUsuarios();
    }

    private void initialize()
    {
        this.setTitle("Login - Agenda");
        this.setSize(300, 200);
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //Componentes do Login
        loginLabel.setBounds(10, 10, 60, 30);
        loginLabel.setText("Login:");
        loginField.setBounds(80, 10, 150, 30);
        loginField.setText("usuario0");
        loginField.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER)
                    logar();
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.add(loginLabel);
        this.add(loginField);
        
        //Componentes da Senha
        senhaLabel.setBounds(10, 50, 60, 30);
        senhaLabel.setText("Senha:");
        senhaField.setBounds(80, 50, 150, 30);
        senhaField.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER)
                    logar();
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.add(senhaLabel);
        this.add(senhaField);

        //Botão de Login
        loginButton.setBounds(10, 100, 100, 30);
        loginButton.setText("Login");
        loginButton.addActionListener(e -> {
            logar();
        });
        this.add(loginButton);

        this.setVisible(true);
    }

    boolean validaFormulario()
    {
        if(loginField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Preencha o campo login", "Erro", JOptionPane.ERROR_MESSAGE);
            loginField.requestFocus();
            return false;
        }
        if(senhaField.getPassword().length == 0)
        {
            JOptionPane.showMessageDialog(this, "Preencha o campo senha", "Erro", JOptionPane.ERROR_MESSAGE);
            senhaField.requestFocus();
            return false;
        }
        return true;
    }
    

    private String md5(String texto)
    {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(texto.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private void logar(){
        if(!validaFormulario())
            return;
        String login = loginField.getText();
        String senha = md5(new String(senhaField.getPassword()));
        if(autenticar(login, senha)){
            this.dispose();
            new App();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean autenticar(String login, String senha){
        Usuario usuario = new Usuario(login, senha);

        if(!SistemaAgenda.getUsuarios().contains(usuario)){
            return false;
        }
        
        SistemaAgenda.setUsuarioLogado(usuario);
        return true;
    }
}
