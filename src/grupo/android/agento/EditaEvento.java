package grupo.android.agento;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class EditaEvento extends Activity {

	private TextView text;
	private EventosDataSource datasource;
	private SQLiteDatabase sql;
	private long value;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edita_evento);
        //Recuperando o id do evento
        Bundle extras = getIntent().getExtras();
        value = getIntent().getIntExtra("id_Evento", -1);
        
        text = (TextView)findViewById(R.id.editEvento);
        
        datasource = new EventosDataSource(this);
        datasource.open();
    	List<Eventos> values = datasource.getAllEventos();
    	text.setText(values.get((int) value).getEvento().toString());
    	datasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edita_evento, menu);
        return true;
    }

    public void salvar(View v){
        datasource.open();
        datasource.update((value + 1), "pendente", text.getText().toString());
        datasource.close();
    }
    public void cancelar(View v){
    	startActivity(
           	new Intent(this, AgentoEvento.class)
        );
    	this.finish();
    }
    @Override
	protected void onDestroy() {
		super.onDestroy();
		
		//fecha banco
		datasource.close();
	}
    @Override
    public void onBackPressed() {
       super.onBackPressed();
       startActivity(
       		new Intent(this, AgentoEvento.class)
       	);
       	finish();
    }
}
