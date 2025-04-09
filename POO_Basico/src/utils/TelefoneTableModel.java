package utils;

import javax.swing.table.AbstractTableModel;

import data.Telefone;

import java.util.ArrayList;
import java.util.List;

public class TelefoneTableModel extends AbstractTableModel {
    private List<Telefone> telefones;
    private String[] colunas = {"Telefone", "Tipo de Telefone"};

    public TelefoneTableModel() {
        this.telefones = new ArrayList<>();
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
        fireTableDataChanged();
    }

    public Telefone getTelefoneAt(int rowIndex) {
        return telefones.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return telefones.size();
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
        Telefone telefone = telefones.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return telefone.getNumero();
            case 1:
                return telefone.getTipoTelefone();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}