package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import data.Agenda;
import data.Email;
import data.Pessoa;
import data.Telefone;
import data.TipoTelefone;
import data.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public final class SistemaAgenda {
    private static Usuario usuarioLogado;
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Random random = new Random();
    private static final String ARQUIVO_JSON = "usuarios.json";

    public SistemaAgenda() {
        popularDados();
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado_) {
        usuarioLogado = usuarioLogado_;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static Agenda getAgendaUsuarioLogado() {
        Usuario usuarioLogado = SistemaAgenda.getUsuarios().get(SistemaAgenda.getUsuarios().indexOf(getUsuarioLogado()));
        return usuarioLogado.getAgenda();
    }

    public static void popularDados() {
        // Listas de dados simulados
        String[] nomes = {"Maria", "João", "Ana", "Carlos", "José", "Fernanda", "Paulo", "Carla", "Pedro", "Sofia"};
        String[] sobrenomes = {"Silva", "Souza", "Lima", "Pereira", "Ferreira", "Costa", "Oliveira", "Rodrigues"};
        String[] dominiosEmail = {"example.com", "email.com", "teste.com", "empresa.com"};
        String[] numerosTelefone = {"99999-1111", "88888-2222", "77777-3333", "66666-4444", "55555-5555", "44444-6666", "33333-7777", "22222-8888", "11111-9999", "12345-6789"};
    
        // Criando 5 usuários
        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario("usuario" + i, "senha" + i);
    
            // Adicionando pelo menos 5 pessoas para cada usuário
            for (int j = 0; j < 5; j++) {
                String nomeCompleto = nomes[random.nextInt(nomes.length)] + " " + sobrenomes[random.nextInt(sobrenomes.length)];
                String cpf = gerarCPF();
                Date dataNascimento = gerarDataNascimentoAleatoria();
    
                Pessoa pessoa = new Pessoa(nomeCompleto, dataNascimento, cpf);
    
                // Adicionando entre 1 a 3 telefones aleatórios para a pessoa
                int qtdTelefones = 1 + random.nextInt(3);
                for (int k = 0; k < qtdTelefones; k++) {
                    String numeroTelefone = numerosTelefone[random.nextInt(numerosTelefone.length)];
                    TipoTelefone tipoTelefone = TipoTelefone.values()[random.nextInt(TipoTelefone.values().length)];
                    pessoa.adicionarTelefone(new Telefone(numeroTelefone, tipoTelefone));
                }
    
                // Adicionando entre 1 a 3 emails aleatórios para a pessoa
                int qtdEmails = 1 + random.nextInt(3);
                for (int k = 0; k < qtdEmails; k++) {
                    String email = nomeCompleto.toLowerCase().replace(" ", ".") + (k + 1) + "@" + dominiosEmail[random.nextInt(dominiosEmail.length)];
                    pessoa.adicionarEmail(new Email(email));
                }
    
                // Adicionando a pessoa à agenda do usuário
                usuario.getAgenda().adicionarPessoa(pessoa);
            }
    
            // Adicionando o usuário à lista de usuários do sistema
            usuarios.add(usuario);
        }
    }

    // Método para gerar um CPF aleatório
    private static String gerarCPF() {
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            cpf.append(random.nextInt(10));
            if (i == 2 || i == 5) {
                cpf.append(".");
            } else if (i == 8) {
                cpf.append("-");
            }
        }
        return cpf.toString();
    }

    // Método para gerar uma data de nascimento aleatória
    private static Date gerarDataNascimentoAleatoria() {
        Calendar calendar = Calendar.getInstance();
        int ano = 1950 + random.nextInt(51); // Gera um ano entre 1950 e 2000
        int mes = random.nextInt(12); // Gera um mês entre 0 e 11
        int dia = 1 + random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Gera um dia entre 1 e o máximo do mês
        calendar.set(ano, mes, dia);
        return calendar.getTime();
    }

// Método para serializar a lista de usuários em um arquivo JSON
    public static void serializarUsuarios() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();  // Gson com formatação "bonita"
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(usuarios, writer);
            System.out.println("Usuários serializados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para desserializar o arquivo JSON e popular a lista de usuários
    public static void desserializarUsuarios() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Type usuariosListType = new TypeToken<List<Usuario>>() {}.getType();
            usuarios = gson.fromJson(reader, usuariosListType);
            System.out.println("Usuários desserializados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Serializando os dados dos usuários para um arquivo JSON
        serializarUsuarios();

        // Limpando os dados atuais da lista de usuários
        getUsuarios().clear();

        // Desserializando os dados do arquivo JSON e carregando na lista de usuários
        desserializarUsuarios();

        // Exibindo dados dos usuários e suas agendas
        for (Usuario usuario : getUsuarios()) {
            System.out.println("Usuário: " + usuario.getLogin());
            for (Pessoa pessoa : usuario.getAgenda().getPessoas()) {
                System.out.println("  Nome: " + pessoa.getNome());
                System.out.println("  CPF: " + pessoa.getCpf());
                System.out.println("  Data de Nascimento: " + sdf.format(pessoa.getDataNascimento()));
                for (Telefone telefone : pessoa.getTelefones()) {
                    System.out.println("    Telefone: " + telefone.getNumero() + " (" + telefone.getTipoTelefone() + ")");
                }
                for (Email email : pessoa.getEmails()) {
                    System.out.println("    Email: " + email.getEndereco());
                }
                for(int i = 0; i < 50; i++){
                    System.out.print("-");
                }
                System.out.println();
            }
        }
    }
}

