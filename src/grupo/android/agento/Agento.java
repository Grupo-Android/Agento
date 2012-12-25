package grupo.android.agento;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Agento extends Activity {

	private UsuariosDataSource datasource;
	private EditText usuario;
	private EditText senha;
	private List<Usuarios> values;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agento);

		// inicializa os EditText para possivel manipulacao
		usuario = (EditText) findViewById(R.id.usuarioEditText);
		senha = (EditText) findViewById(R.id.senhaEditText);

		// abre o banco
		datasource = new UsuariosDataSource(this);
		datasource.open();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.agento, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// fecha banco
		datasource.close();
	}

	// TODO Concertar Método
	// chamado quando o botão entrar for clicado
	public void entrar(View view) {

		if (login() == true) {
			// fecha o banco
			datasource.close();

			startActivity(new Intent(this, AgentoEvento.class));
			this.finish();
		} else {
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(this,
					R.string.usuario_senha_invalidos, duration);
			int offsetX = 0;
			int offsetY = 70;
			toast.setGravity(Gravity.BOTTOM, offsetX, offsetY);
			toast.show();
		}
	}

	// checa a validade da entrada de dados (usuario e senha)
	private boolean validaEntrada() {
		return ValidaEntrada.validaUsuario(usuario.getText().toString())
				&& ValidaEntrada.validaSenha(senha.getText().toString());
	}

	// verifica se o usuario pode logar
	private boolean login() {
		// protecao contra qualquer tipo de erro de digitacao
		if (validaEntrada() == false)
			return false;

		// preenche values com os usuarios cadastrados
		values = datasource.getAllUsuarios();

		for (int i = 0; i < values.size(); ++i) {

			// confere se existe um usuario com esse nome
			if (values.get(i).getUsuario().equals(usuario.getText().toString())) {

				// confere se a senha está correta
				if (values.get(i).getSenha().equals(senha.getText().toString()))
					return true;
			}
		}
		return false;
	}

	public void esqueciSenhaRedirecionamento(View view) {
		// redireciona para funcinalidade de esqueci a minha senha
	}

	// redireciona para novo cadastro
	public void registrarRedirecionamento(View view) {
		startActivity(new Intent(this, AgentoCadastro.class));
	}
}
