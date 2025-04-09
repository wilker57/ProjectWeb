package data;

public class Email {
    private String endereco;

    public Email(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return endereco;
    }

    @Override
    public boolean equals(Object item) {
         if ((this.endereco.equals(((Email) item).getEndereco()))){
              return true;
         }
         return false;
    }
}
