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
	private EditText usuario;
	private EditText senha;
	private EditText email;
	private static final int MENSAGEM_ERRO_ENTRADA_INVALIDA = 1;
	private static final int MENSAGEM_SUCESSO = 2;
	private static final int MENSAGEM_ERRO_USUARIO_EXISTENTE = 3;
	private String mensagemErroEntradaInvalida,
		mensagemErroUsuarioExistente;
	
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
    
	//TODO Concertar o método showDialog que está ultrapassado
    //faz o cadastro do novo usuario
	public void registrar(View view){
		boolean entradaValida = entradaValida();
		boolean usuarioUnico = usuarioUnico();
		
		if(entradaValida == true){
    		if(usuarioUnico == true){
    			
    			//cadastra usuario
    			datasource.insertUsuario(usuario.getText().toString(),
    					senha.getText().toString(),
    					email.getText().toString());
    		
    			//mostra mensagem de sucesso
    			showDialog(MENSAGEM_SUCESSO);
    		
    			//redireciona para tela de login
    			startActivity(
    					new Intent(this, Agento.class));
    		}
    	}else{
    		
    		//mostra mensagens de erro
    		if(usuarioUnico == false)
    			showDialog(MENSAGEM_ERRO_USUARIO_EXISTENTE);
    		
    		
    		if(entradaValida == false)
    			showDialog(MENSAGEM_ERRO_ENTRADA_INVALIDA);
    	}
    }
    
    //verifica se existe o nome de usuario é unico
    private boolean usuarioUnico(){
    	
    	//preenche values com os usuarios cadastrados
    	List<Usuarios> values = datasource.getAllUsuarios();
    	
    	for(int i = 0; i < values.size(); ++i)
    		if(values.get(i).getUsuario().equals(usuario.getText().toString())){
    			mensagemErroUsuarioExistente = "-> Usuário Existente\n";
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
    	
    	mensagemErroEntradaInvalida = "";
    	
    	if( usuarioValido == false)
    		mensagemErroEntradaInvalida += "-> Usuario Inválido\n";
    	if( senhaValida == false)
    		mensagemErroEntradaInvalida += "-> Senha Inválida\n";
    	if( emailValido == false)
    		mensagemErroEntradaInvalida += "-> Email Inválido\n";
    	
        return ValidaEntrada.validaUsuario(usuario.getText().toString())
        		&& ValidaEntrada.validaSenha(senha.getText().toString())
        				&& ValidaEntrada.validaEmail(email.getText().toString());
    }


    //cria mensagens baseadas no parametro
    @Override
    public Dialog onCreateDialog(int id) {
		AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
		AlertDialog.Builder sucessDialog = new AlertDialog.Builder(this);
		
		//configura a mensagem de erro
		LayoutInflater li = LayoutInflater.from(this);
		View mensagemErroView = li.inflate(R.layout.mensagem_erro, null);
		
		errorDialog.setTitle(R.string.erro);
		errorDialog.setIcon(R.drawable.ic_error);
		errorDialog.setView(mensagemErroView);
		errorDialog.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {} //faz nada
		});

    	switch(id) {
			case (MENSAGEM_ERRO_USUARIO_EXISTENTE) :
				return errorDialog.create();
    		case (MENSAGEM_ERRO_ENTRADA_INVALIDA) :
    			return errorDialog.create();
    		case (MENSAGEM_SUCESSO) : 
    			sucessDialog.setTitle(R.string.sucesso_cadastro);
				sucessDialog.show();
				break;
    	}
    	return null;
    }
    
    //chamado logo apos o onCreateDialog
    @Override
    public void onPrepareDialog(int id, Dialog dialog) {
    	//cria TextView das mensagens de erro
    	TextView tv = (TextView) dialog.findViewById(R.id.MensagemErroTextView);
    	
    	switch(id) {
    		case (MENSAGEM_ERRO_ENTRADA_INVALIDA) :
    			tv.setText(mensagemErroEntradaInvalida);
    			break;
    		case (MENSAGEM_ERRO_USUARIO_EXISTENTE) :
    			tv.setText(mensagemErroUsuarioExistente);
    			break;
    	}
    }
}
