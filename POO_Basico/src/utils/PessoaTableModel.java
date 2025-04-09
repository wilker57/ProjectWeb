package utils;

import javax.swing.table.AbstractTableModel;

import data.Pessoa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PessoaTableModel extends AbstractTableModel {
    private List<Pessoa> pessoas;
    private String[] colunas = {"Nome", "Data Nascimento", "Telefone", "E-mail"};

    public PessoaTableModel() {
        this.pessoas = new ArrayList<>();
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
        fireTableDataChanged();
    }

    public Pessoa getPessoaAt(int rowIndex) {
        return pessoas.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return pessoas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pessoa pessoa = pessoas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return pessoa.getNome();
            case 1:
                return new SimpleDateFormat("dd/MM/yyyy").format(pessoa.getDataNascimento());
            case 2:
                return pessoa.getTelefonesFormatados();
            case 3:
                return pessoa.getEmailsFormatados();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
