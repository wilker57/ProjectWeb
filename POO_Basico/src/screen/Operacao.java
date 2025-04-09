package screen;

public class Operacao {

    private char sinal;
    private String descricao;
  
    public Operacao(char sinal, String descricao) {
      this.sinal = sinal;
      this.descricao = descricao;
    }
  
    public int getSinal() {
      return sinal;
    }
  
    public String getDescricao() {
      return descricao;
    }
  
    @Override
    public String toString() {
      return descricao;
    }
    
    @Override
    public boolean equals(Object item) {
         if ((this.sinal == ((Operacao) item).getSinal()) && (this.descricao.equals(((Operacao) item).getDescricao()))){
              return true;
         }
         return false;
    }
  }
  
