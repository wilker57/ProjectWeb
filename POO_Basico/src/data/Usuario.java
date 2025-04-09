package data;

public class Usuario {
    private String login;
    private String senha;
    private Agenda agenda;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.agenda = new Agenda();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public boolean equals(Object item) {
         if ((this.login.equals(((Usuario) item).getLogin())) &&
            (this.senha.equals(((Usuario) item).getSenha()))){
              return true;
         }
         return false;
    }
}

