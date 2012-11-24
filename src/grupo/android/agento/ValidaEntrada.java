package grupo.android.agento;

public class ValidaEntrada {
    //valida usuario e senha
    public static boolean validaUsuario(String usuario){
        return usuario.matches("[a-zA-Z0-9]+");
    }
    
    //valida senha
    public static boolean validaSenha(String senha){
        return senha.matches("[a-zA-Z0-9]+");
    }
    
    //valida email 
    public static boolean validaEmail(String email){
    	return email.matches("[a-z[-_.]]+[@]{1}[a-z[-_.]]+[.]{1}[a-z[-_]]+");
    }
}
