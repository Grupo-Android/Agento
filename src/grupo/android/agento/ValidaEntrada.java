package grupo.android.agento;

public class ValidaEntrada {
    //valida usuario e senha
    public static boolean validaEntrada(String entrada){
        return entrada.matches("[a-zA-Z0-9]+");
    }
    
    //valida email 
    public static boolean validaEmail(String email){
    	return email.matches("[a-z[-_.]]+[@]{1}[a-z[-_.]]+[.]{1}[a-z[-_]]+");
    }
}
