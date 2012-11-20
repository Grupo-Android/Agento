package grupo.android.agento;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsuariosDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] colunasUsuarios = {MySQLiteHelper.COLUNA_ID_USUARIO, MySQLiteHelper.COLUNA_USUARIO,
			MySQLiteHelper.COLUNA_SENHA, MySQLiteHelper.COLUNA_EMAIL};
	
	// Iniciando a classe MySQLiteHelper
	public UsuariosDataSource(Context context){
		dbHelper = new MySQLiteHelper(context);
	}
	
	// Metodos para abrir ou fechar o Banco
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close(){
		database.close();
	}
	
	// Insert na tabela USUARIOS
	public Usuarios insertUsuario(String usuario, String senha, String email) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUNA_USUARIO, usuario);
		values.put(MySQLiteHelper.COLUNA_SENHA, senha);
		values.put(MySQLiteHelper.COLUNA_EMAIL, email);
		long insertId = database.insert(MySQLiteHelper.TABLE_USUARIOS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_USUARIOS,
				colunasUsuarios, MySQLiteHelper.COLUNA_ID_USUARIO + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Usuarios novoUsuario = cursorToUsuario(cursor);
		cursor.close();
		return novoUsuario;
	}
	
	// Deletar usuário se necessário no futuro
	public void deleteUsuario(Usuarios usuario) {
	    long id = usuario.getId();
	    database.delete(MySQLiteHelper.TABLE_USUARIOS, MySQLiteHelper.COLUNA_ID_USUARIO
	        + " = " + id, null);
	  }
	
	public List<Usuarios> getAllUsuarios() {
	    List<Usuarios> usuarios = new ArrayList<Usuarios>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_USUARIOS,
	        colunasUsuarios, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Usuarios usuario = cursorToUsuario(cursor);
	      usuarios.add(usuario);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return usuarios;
	  }
	
	private Usuarios cursorToUsuario(Cursor cursor) {
	    Usuarios usuario = new Usuarios();
	    usuario.setId(cursor.getLong(0));
	    usuario.setUsuario(cursor.getString(1));
	    usuario.setSenha(cursor.getString(2));
	    usuario.setEmail(cursor.getString(3));
	    return usuario;
	  }
}
