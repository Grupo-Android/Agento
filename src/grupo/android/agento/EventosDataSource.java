package grupo.android.agento;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventosDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] colunasEventos = {MySQLiteHelper.COLUNA_ID_EVENTO, MySQLiteHelper.COLUNA_ESTADO,
			MySQLiteHelper.COLUNA_EVENTO};
	
	// Iniciando a classe MySQLiteHelper
	public EventosDataSource(Context context){
		dbHelper = new MySQLiteHelper(context);
	}
	
	// Metodos para abrir ou fechar o Banco
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close(){
		database.close();
	}
	
	// Insert na tabela EVENTOS
	public Eventos insertEvento(String estado, String evento) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUNA_ESTADO, estado);
		values.put(MySQLiteHelper.COLUNA_EVENTO, evento);
		long insertId = database.insert(MySQLiteHelper.TABLE_EVENTOS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTOS,
				colunasEventos, MySQLiteHelper.COLUNA_ID_EVENTO + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Eventos novoEvento = cursorToEvento(cursor);
		cursor.close();
		return novoEvento;
	}
	
	public void update(long id, String v1, String v2){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUNA_ESTADO, v1);
		values.put(MySQLiteHelper.COLUNA_ID_EVENTO, v2);
		database.update(MySQLiteHelper.TABLE_EVENTOS, values, "_id ="+id, null);
	}
	
	// Deletar evento se necessário no futuro
	public void deleteEvento(Eventos evento){
	    long id = evento.getId();
	    database.delete(MySQLiteHelper.TABLE_EVENTOS, MySQLiteHelper.COLUNA_ID_EVENTO
	        + " = " + id, null);
	  }
	
	public List<Eventos> getAllEventos() {
	    List<Eventos> eventos = new ArrayList<Eventos>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTOS,
	        colunasEventos, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Eventos evento = cursorToEvento(cursor);
	      eventos.add(evento);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return eventos;
	  }
	
	private Eventos cursorToEvento(Cursor cursor) {
	    Eventos evento = new Eventos();
	    evento.setId(cursor.getLong(0));
	    evento.setEstado(cursor.getString(1));
	    evento.setEvento(cursor.getString(2));
	    return evento;
	  }
}