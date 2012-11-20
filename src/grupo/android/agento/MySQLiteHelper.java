package grupo.android.agento;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	// Tabela USUARIOS
	public static final String TABLE_USUARIOS = "usuarios";
	public static final String COLUNA_ID_USUARIO = "_id";
	public static final String COLUNA_USUARIO = "usuario";
	public static final String COLUNA_SENHA = "senha";
	public static final String COLUNA_EMAIL = "email";
	
	// Tabela EVENTOS
	public static final String TABLE_EVENTOS = "eventos";
	public static final String COLUNA_ID_EVENTO = "_id";
	public static final String COLUNA_ESTADO = "estado";
	public static final String COLUNA_EVENTO = "evento";
	
	// DataBase AGENTO
	private static final String DATABASE_AGENTO = "agento.db";
	private static final int DATABASE_VERSAO = 1;
	
	// SQL para a criação da tabela USUARIOS
	private static final String DATABASE_USUARIOS = "create table" + TABLE_USUARIOS + "("
			+ COLUNA_ID_USUARIO + " integer primary key autoincrement, "
			+ COLUNA_USUARIO + "text not null, "
			+ COLUNA_SENHA + "text not null, "
			+ COLUNA_EMAIL + "text not null);";
	
	// SQL para a criação da tabela EVENTOS
	private static final String DATABASE_EVENTOS = "create table" + TABLE_EVENTOS + "("
			+ COLUNA_ID_EVENTO + " integer primary key autoincrement, "
			+ COLUNA_ESTADO + " text not null, "
			+ COLUNA_EVENTO + " text);";
	
	// Construtor
	public MySQLiteHelper(Context context){
		super(context, DATABASE_AGENTO, null, DATABASE_VERSAO);
	}
	
	//Criar a tabela se ela não existir
	@Override
	public void onCreate(SQLiteDatabase database){
		database.execSQL(DATABASE_USUARIOS);
		database.execSQL(DATABASE_EVENTOS);
	}
	
	public void onUpgrade(SQLiteDatabase database, int versaoVelha, int versaoNova){
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading da versão " + versaoVelha + " para "
				+ versaoNova + " que destruirá todos os dados antigos!");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTOS);
		onCreate(database);
	}
	
}
