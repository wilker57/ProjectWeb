package data;

public class Telefone {
    private String numero;
    private TipoTelefone tipoTelefone;

    public Telefone(String numero, TipoTelefone tipoTelefone) {
        this.numero = numero;
        this.tipoTelefone = tipoTelefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    @Override
    public String toString() {
        return numero;
    }

    @Override
    public boolean equals(Object item) {
         if ((this.numero.equals(((Telefone) item).getNumero()) && 
             (this.tipoTelefone.equals(((Telefone) item).getTipoTelefone())))){
              return true;
         }
         return false;
    }
}
