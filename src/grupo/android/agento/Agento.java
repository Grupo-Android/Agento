package grupo.android.agento;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Agento extends Activity {
	private UsuariosDataSource datasource;
	private EditText usuario;
	private EditText senha;
	private List<Usuarios> values;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>TO FIX<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //chamado quando o botão entrar for clicado
    public void login(View view) {
    	//abre o banco
      	datasource = new UsuariosDataSource(this);
    	datasource.open();
        
    	//preenche values com os usuarios cadastrados
    	values = datasource.getAllUsuarios();
        
    	//recebe usuario e senha digitados
    	usuario = (EditText) findViewById(R.id.usuarioEditText);
    	senha = (EditText) findViewById(R.id.senhaEditText);
       
    	if(validaEntrada() == true){    	
    		//protecao contra qualquer tipo de erro de digitacao
    		if(podeLogarMeuFilho() == true){
    			//fecha o banco
        		datasource.close();
        		
        		//>>>>>>CHAMAR A OUTRA ACTIVITY AQUI <<<<<
    		}
    	}else{
    		//>>>>>>exibe mensagem de erro<<<<<
    		
    		return;
    	}
    }

    //checa a validade da entrada de dados (usuario e senha)
    private boolean validaEntrada(){
        return ValidaEntrada.validaEntrada(usuario.getText().toString())
        		&& ValidaEntrada.validaEntrada(senha.getText().toString());
    }
    
    //verifica se o usuario pode logar
    private boolean podeLogarMeuFilho(){
        for(int i = 0; i < values.size(); ++i){
        	
        	//confere se existe um usuario com esse nome
        	if(values.get(i).getUsuario().equals(
        			usuario.getText().toString())){
        		
        		//confere se a senha está correta
        		if(values.get(i).getSenha().equals(
        				senha.getText().toString()))
        			return true;
        	}
        }
        
        return false;
    }
    
    public void esqueciSenhaRedirecionamento(View view){
    	//redireciona para funcinalidade de esqueci a minha senha
    }
    
    public void registrarRedirecionamento(View view){
    	//redireciona para funcionalidade de novo cadastro
    }
}
