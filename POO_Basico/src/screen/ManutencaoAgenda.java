package screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import data.Pessoa;
import data.Telefone;
import data.TipoTelefone;
import utils.SistemaAgenda;
import utils.TelefoneTableModel;
import utils.Utilitario;

public class ManutencaoAgenda extends JDialog {
	private Pessoa pessoa;

	// Labels
	JLabel labelNome = new JLabel();
	JLabel labelCPF = new JLabel();
	JLabel labelDataNascimento = new JLabel();
	JLabel labelTelefone = new JLabel();
	JLabel labelTipoTelefone = new JLabel();

	// TextField
	JTextField textFieldNome = new JTextField();
	JFormattedTextField textFieldCPF;
	JFormattedTextField textFieldDataNascimento;
	JFormattedTextField textFieldTelefone;

    // Table Telefone
    TelefoneTableModel telefoneTableModel = new TelefoneTableModel();
    JTable tabelaTelefone = new JTable(telefoneTableModel);
    JScrollPane scrollTableTelefone = new JScrollPane(tabelaTelefone);

	// Telefone Buttons
	JButton buttonTelefoneInserir = new JButton();
	JButton buttonTelefoneAlterar = new JButton();
	JButton buttonTelefoneExcluir = new JButton();

	// Button
	JButton buttonManutencao = new JButton();
	JButton buttonCancelar = new JButton();

	// ComboBox
	JComboBox<TipoTelefone> comboBoxTipoTelefone = new JComboBox<TipoTelefone>();

	//Inserir
	public ManutencaoAgenda() {
		super();
		pessoa = null;
		initialize();
	}

	//Alterar
	public ManutencaoAgenda(Pessoa pessoa) {
		super();
		//Guarda o objeto da pessoa no atributo privado da classe
		this.pessoa = pessoa;
		initialize();
	}

	//Pega os dados do banco de dados e coloca nos componentes da janela
	private void preencheCampos() {
		try {
				textFieldNome.setText(pessoa.getNome());
				textFieldCPF.setText(pessoa.getCpf());
				//Formata o texto do formato inglês para o brasileiro
				textFieldDataNascimento
						.setText(new SimpleDateFormat("dd/MM/yyyy").format(pessoa.getDataNascimento()));
				telefoneTableModel.setTelefones(pessoa.getTelefones());
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
	}

	private void consultaTelefone(){
		telefoneTableModel.setTelefones(new ArrayList<>());
		telefoneTableModel.setTelefones(pessoa.getTelefones());
	}

	private void excluirTelefone(Telefone telefone){
		pessoa.getTelefones().removeIf(t -> t == telefone);
		SistemaAgenda.serializarUsuarios();
	}

	//Valida os campos do formulário e lança uma exceção caso estejam incorretos
	private void validaCampos() throws Exception {
		if (textFieldNome.getText().trim().equals(""))
		{
			textFieldNome.requestFocus();
			throw new Exception("Digite o nome");
		}

		if (!Utilitario.isCpfValido(textFieldCPF.getText().trim()))
		{
			textFieldCPF.requestFocus();
			throw new Exception("Digite um CPF válido");
		}

		if (!Utilitario.isDate(textFieldDataNascimento.getText().trim()))
		{
			textFieldDataNascimento.requestFocus();
			throw new Exception("Digite uma data válida");
		}
	}

	private void validaCamposTelefone() throws Exception {
		if (textFieldTelefone.getText().trim().length()!= 10)
		{
			textFieldTelefone.requestFocus();
			throw new Exception("Digite o telefone");
		}
		Telefone novoTelefone = new Telefone(textFieldTelefone.getText(), (TipoTelefone) comboBoxTipoTelefone.getSelectedItem());
		if(pessoa.getTelefones().contains(novoTelefone)){
			throw new Exception("Telefone já cadastrado");
		}
	}

	private void manutencao() {
		// Inserir
		if (pessoa == null) {
			try {
				validaCampos();
                pessoa = new Pessoa(textFieldNome.getText().trim(), 
                Utilitario.stringToDate(textFieldDataNascimento.getText().trim()), 
                textFieldCPF.getText().trim());

                SistemaAgenda.getAgendaUsuarioLogado().adicionarPessoa(pessoa);
				SistemaAgenda.serializarUsuarios();

				// Se inseriu o registro, fecha a tela
				dispose();
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro ao Inserir Registro",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(exc.getMessage());
			}
		} else // Alterar
		{
			try {
				validaCampos();
				pessoa.setNome(textFieldNome.getText().trim());
                pessoa.setCpf(textFieldCPF.getText().trim());
				pessoa.setDataNascimento(Utilitario.stringToDate(textFieldDataNascimento.getText().trim()));
                
                SistemaAgenda.serializarUsuarios();
                
                // Se atualizou o registro, fecha a tela
				dispose();
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro ao Atualizar Registro",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(exc.getMessage());
			}
		}
	}

	private void initialize() {
		this.setTitle("Manutenção da Agenda");
		this.setSize(450, 650);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setResizable(false);

		// Labels
		labelNome.setSize(100, 20);
		labelNome.setLocation(20, 20);
		labelNome.setText("Nome:");

		labelCPF.setSize(100, 20);
		labelCPF.setLocation(20, 60);
		labelCPF.setText("CPF:");

		labelDataNascimento.setSize(100, 20);
		labelDataNascimento.setLocation(20, 100);
		labelDataNascimento.setText("Data de Nasc.:");

		labelTelefone.setText("Telefone:");
		labelTelefone.setSize(60, 20);
		labelTelefone.setLocation(20, 265);

		labelTipoTelefone.setText("Tipo:");
		labelTipoTelefone.setSize(50, 20);
		labelTipoTelefone.setLocation(240, 265);

		// TextField
		textFieldNome.setSize(300, 20);
		textFieldNome.setLocation(120, 20);

		try {
			//Formata o campo de texto para o formato de telefone (8 digitos)
			MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
			textFieldCPF = new JFormattedTextField(mascaraCPF);
			textFieldCPF.setSize(150, 20);
			textFieldCPF.setLocation(120, 60);
		} catch (Exception exc) {
			System.out.println("Erro ao formatar mascara: " + exc.getMessage());
		}

		try {
			//Formata o campo de texto para o formato de telefone (8 digitos)
			MaskFormatter mascaraData = new MaskFormatter("##/##/####");
			textFieldDataNascimento = new JFormattedTextField(mascaraData);
			textFieldDataNascimento.setSize(150, 20);
			textFieldDataNascimento.setLocation(120, 100);
		} catch (Exception exc) {
			System.out.println("Erro ao formatar mascara: " + exc.getMessage());
		}

		try {
			//Formata o campo de texto para o formato de telefone (8 digitos)
			MaskFormatter mascaraTelefone = new MaskFormatter("#####-####");
			textFieldTelefone = new JFormattedTextField(mascaraTelefone);
			textFieldTelefone.setSize(130, 20);
			textFieldTelefone.setLocation(90, 265);
		} catch (Exception exc) {
			System.out.println("Erro ao formatar mascara: " + exc.getMessage());
		}

		// ComboBox
		comboBoxTipoTelefone.setSize(150, 20);
		comboBoxTipoTelefone.setLocation(280, 265);
		for (TipoTelefone tipo : TipoTelefone.values()) {
			comboBoxTipoTelefone.addItem(tipo);
		}		

		// Telefone Buttons
        buttonTelefoneInserir.setSize(100, 30);
        buttonTelefoneInserir.setLocation(20, 290);
        buttonTelefoneInserir.setText("Inserir");
        buttonTelefoneInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
					validaCamposTelefone();
					Telefone novoTelefone = new Telefone(textFieldTelefone.getText(), (TipoTelefone) comboBoxTipoTelefone.getSelectedItem());
					pessoa.adicionarTelefone(novoTelefone);
					SistemaAgenda.serializarUsuarios();
					// Realiza uma consulta logo após fechar a janela
					consultaTelefone();
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro ao Inserir Registro",
							JOptionPane.ERROR_MESSAGE);
					System.out.println(exc.getMessage());
				}
            }
        });

        buttonTelefoneAlterar.setSize(100, 30);
        buttonTelefoneAlterar.setLocation(140, 290);
        buttonTelefoneAlterar.setText("Alterar");
        buttonTelefoneAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verifica se existe um registro selecionado
                if (tabelaTelefone.getSelectedRow() != -1) {
					try
					{
						validaCamposTelefone();
						// Pega o objeto Telefone da linha selecionada da tabela
						Telefone telefone = telefoneTableModel.getTelefoneAt(tabelaTelefone.getSelectedRow());
						telefone.setNumero(textFieldTelefone.getText());
						telefone.setTipoTelefone((TipoTelefone) comboBoxTipoTelefone.getSelectedItem());
						// Realiza uma consulta logo após fechar a janela
						SistemaAgenda.serializarUsuarios();
						consultaTelefone();
					}catch (Exception exc) {
						JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro ao Alterar Registro",	JOptionPane.ERROR_MESSAGE);	}	
                }
            }
        });

        buttonTelefoneExcluir.setSize(100, 30);
        buttonTelefoneExcluir.setLocation(260, 290);
        buttonTelefoneExcluir.setText("Excluir");
        buttonTelefoneExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verifica se existe um registro selecionado
                if (tabelaTelefone.getSelectedRow() != -1) {
                    String message = "Deseja realmente excluir o registro?";
                    String title = "Confirmação de exclusão";
                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    // Verifica se o usuário selecionou SIM
                    if (reply == JOptionPane.YES_OPTION) {
                        // Pega o objeto Telefone da linha selecionada da tabela
                        Telefone telefone = telefoneTableModel.getTelefoneAt(tabelaTelefone.getSelectedRow());
                        // Chama o método de exclusão passando o ID como parâmetro
                        excluirTelefone(telefone);
                        // Realiza uma consulta logo após a exclusão
                        consultaTelefone();
                    }
                }
            }
        });

		// Buttons
		buttonManutencao.setSize(100, 50);
		buttonManutencao.setLocation(20, 550);
		buttonManutencao.setText("Inserir");
		buttonManutencao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chama a função que insere ou altera os dados
				manutencao();
			}
		});

		buttonCancelar.setSize(100, 50);
		buttonCancelar.setLocation(140, 550);
		buttonCancelar.setText("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a tela
				dispose();
			}
		});

		//Se nenhuma pessoa foi passada por parâmetro, então é uma inserção
		if (pessoa == null) {
			buttonManutencao.setText("Inserir");
		} else { //Senão, é uma atualização
			buttonManutencao.setText("Alterar");
			preencheCampos();
		}

		//Tabela Telefone
		tabelaTelefone.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaTelefone.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            if (tabelaTelefone.getSelectedRow() != -1) {
				textFieldTelefone.setText(tabelaTelefone.getValueAt(tabelaTelefone.getSelectedRow(), 0).toString());
				comboBoxTipoTelefone.setSelectedItem(tabelaTelefone.getValueAt(tabelaTelefone.getSelectedRow(), 1));
			}            
			}
		});
		scrollTableTelefone.setSize(410, 120);
        scrollTableTelefone.setLocation(20, 140);

		this.add(labelNome);
		this.add(labelCPF);
		this.add(labelDataNascimento);
		this.add(textFieldNome);
		this.add(textFieldCPF);
		this.add(textFieldDataNascimento);
		this.add(buttonManutencao);
		this.add(buttonCancelar);
		this.add(scrollTableTelefone);
		this.add(labelTelefone);
		this.add(labelTipoTelefone);
		this.add(textFieldTelefone);
		this.add(comboBoxTipoTelefone);
		this.add(buttonTelefoneInserir);
		this.add(buttonTelefoneAlterar);
		this.add(buttonTelefoneExcluir);

		this.setVisible(true);
	}
}