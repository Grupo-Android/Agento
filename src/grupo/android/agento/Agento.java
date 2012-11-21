package grupo.android.agento;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
        
      //inicializa os EditText para possivel manipulacao
    	usuario = (EditText) findViewById(R.id.usuarioEditText);
    	senha = (EditText) findViewById(R.id.senhaEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agento, menu);
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
       
    	//protecao contra qualquer tipo de erro de digitacao
    	if(validaEntrada() == true){    	
    		if(podeLogarMeuFilho() == true){
    			//fecha o banco
        		datasource.close();
        		
        		usuario.setText("Usuário:");
        		senha.setText("Senha:");
        		//ACTIVITY AgentoEvento
        		startActivity(
            			new Intent(this, AgentoEvento.class));
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
    
  //redireciona para novo cadastro
    public void registrarRedirecionamento(View view){
    	startActivity(
    			new Intent(this, AgentoCadastro.class));
    }
}
