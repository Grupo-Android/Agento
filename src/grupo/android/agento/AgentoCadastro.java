package grupo.android.agento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AgentoCadastro extends Activity {
	private UsuariosDataSource datasource;
	private EditText usuario;
	private EditText senha;
	private EditText email;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento_cadastro);
        
        //inicializa os EditText para possivel manipulacao
    	usuario = (EditText) findViewById(R.id.usuarioCadastroEditText);
    	senha = (EditText) findViewById(R.id.senhaCadastroEditText);
    	email= (EditText) findViewById(R.id.emailCadastroEditText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agento_cadastro, menu);
        return true;
    }

    //faz o cadastro do novo usuario
    public void registrar(View view){
    	//abre o banco
      	datasource = new UsuariosDataSource(this);
    	datasource.open();
    	
    	if(validaEntrada() == true){
    		//cadastra usuario
    		datasource.insertUsuario(usuario.getText().toString(),
    				senha.getText().toString(),
    				email.getText().toString());
    		
    		datasource.close();
    		
    		//>>>>>>>>>>>>>EXIBE MSG DE SUCESSO<<<<<<<<<<<<<<<<
    		
    		//redireciona para tela de login
        	startActivity(
        			new Intent(this, Agento.class));
    	}else{
    		//>>>>>>>>>>>>>>EXIBE MSG DE ERRO<<<<<<<<<<<<<<<<<<<<<<
    	}
    }
    
	//redireciona para tela de login
    public void cancelar(View view){
    	startActivity(
    			new Intent(this, Agento.class));
    }
    
    //checa a validade da entrada de dados (usuario, senha e email)
    private boolean validaEntrada(){
        return ValidaEntrada.validaEntrada(usuario.getText().toString())
        		&& ValidaEntrada.validaEntrada(senha.getText().toString())
        				&& ValidaEntrada.validaEmail(email.getText().toString());
    }
}
