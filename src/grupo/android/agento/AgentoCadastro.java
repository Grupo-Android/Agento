package grupo.android.agento;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AgentoCadastro extends Activity {
	private UsuariosDataSource datasource;
	private EditText usuario,
					senha,
					email;
	private static final int MENSAGEM_ERRO = 1;
	private String mensagemErro;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento_cadastro);
        
        //inicializa os EditText para possivel manipulacao
    	usuario = (EditText) findViewById(R.id.usuarioCadastroEditText);
    	senha = (EditText) findViewById(R.id.senhaCadastroEditText);
    	email= (EditText) findViewById(R.id.emailCadastroEditText);
    	
    	//abre o banco
      	datasource = new UsuariosDataSource(this);
    	datasource.open();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agento_cadastro, menu);
        return true;
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//fecha banco
		datasource.close();
	}
    
	//TODO Mensagem de sucesso
    //faz o cadastro do novo usuario
	public void registrar(View view){
		boolean entradaValida = entradaValida();
		
		if(entradaValida == true){
    			
    			//cadastra usuario
    			datasource.insertUsuario(usuario.getText().toString(),
    					senha.getText().toString(),
    					email.getText().toString());
    			
    			//redireciona para tela de login
    			startActivity(
    					new Intent(this, Agento.class));
    	}else{
    		
    		if(entradaValida == false)
    			mostraMensagem(MENSAGEM_ERRO);
    	}
    }
    
    //verifica se existe o nome de usuario é unico
    private boolean usuarioUnico(){
    	//preenche values com os usuarios cadastrados
    	List<Usuarios> values = datasource.getAllUsuarios();
    	
    	for(int i = 0; i < values.size(); ++i)
    		if(values.get(i).getUsuario().equals(usuario.getText().toString())){
    			return false;
    		}
    	
    	return true;
    }

	//redireciona para tela de login
    public void cancelar(View view){
    	startActivity(
    			new Intent(this, Agento.class));
    }
    
    //checa a validade da entrada de dados (usuario, senha e email)
    private boolean entradaValida(){
    	boolean usuarioValido = ValidaEntrada.validaUsuario(
    			usuario.getText().toString());
    	boolean senhaValida = ValidaEntrada.validaSenha(
    			senha.getText().toString());
    	boolean emailValido = ValidaEntrada.validaEmail(
    			email.getText().toString());
    	
    	mensagemErro = "";
    	
    	if( senhaValida == false)
    		mensagemErro += "-> Senha Inválida\n";
    	if( emailValido == false)
    		mensagemErro += "-> Email Inválido\n";
    	if( usuarioValido == true){
        	if(usuarioUnico() == false){
            	mensagemErro += "-> Usuário Existente\n";
        		return false;
        	}
    	}else{
    		mensagemErro += "-> Usuario Inválido\n";
    	}
    	
    	
        return ValidaEntrada.validaUsuario(usuario.getText().toString())
        		&& ValidaEntrada.validaSenha(senha.getText().toString())
        				&& ValidaEntrada.validaEmail(email.getText().toString());
    }

    public void mostraMensagem(int id){
    	AlertDialog.Builder msg = new AlertDialog.Builder(this);
    	
    	switch(id) {
			case (MENSAGEM_ERRO) :
				msg.setTitle(R.string.erro);
				msg.setIcon(R.drawable.ic_error);
				msg.setNeutralButton("OK", null);
				msg.setMessage(mensagemErro);
				break;
    	}
    }
}
