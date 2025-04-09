package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utilitario {
    public static Date stringToDate(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(data);
    }

    public static String dateToString(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    public static Boolean strongPassword(String senha) {
        return senha.length() >= 8 && senha.matches(".*[0-9].*") && senha.matches(".*[a-z].*") && senha.matches(".*[A-Z].*");
    }

    public static Boolean isCpfValido(String cpf) {
        //Remove todos os caracteres que não são dígitos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF possui 11 dígitos numéricos
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) return false;
    
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;
    
        // Cálculo do 1º dígito verificador
        int[] peso = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0, digito1, digito2;
    
        for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * peso[i];
        digito1 = 11 - (soma % 11);
        digito1 = (digito1 > 9) ? 0 : digito1;
        
        // Cálculo do 2º dígito verificador
        soma = 0;
        for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * (peso[i] + 1);
        soma += digito1 * 2;
        digito2 = 11 - (soma % 11);
        digito2 = (digito2 > 9) ? 0 : digito2;
    
        return cpf.charAt(9) - '0' == digito1 && cpf.charAt(10) - '0' == digito2;
    }

    public static boolean isEmail(String email) {
        if ((email == null) || (email.trim().length() == 0))
            return false;

        String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
	}

	public static boolean isTelefone(String telefone) {
		return telefone.matches("\\(\\d{2}\\)\\d{4,5}-\\d{4}");
	}

	public static boolean isDate(String data) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			sdf.parse(data);
			return true;
			} catch (ParseException ex) {
			return false;
			}
	}
}