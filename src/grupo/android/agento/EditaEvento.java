package grupo.android.agento;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class EditaEvento extends Activity {

	private TextView text;
	private EventosDataSource datasource;
	private SQLiteDatabase sql;
	private Eventos eventoEditado;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edita_evento);
        
        //inicializacao
        text = (TextView)findViewById(R.id.editEvento);
        datasource = new EventosDataSource(this);
        datasource.open();
    	List<Eventos> values = datasource.getAllEventos();
        
        //Recuperando o id do evento
        long id = getIntent().getLongExtra("id_Evento", -1);
        
        //seta eventoEditado baseado no id
    	for (int i = 0; i < values.size(); ++i) 
    		if (id == values.get(i).getId())
    			eventoEditado = values.get(i);
    	
    	//seta o texto com o evento a ser editado
        text.setText(eventoEditado.getEvento().toString()); 
        
    	datasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edita_evento, menu);
        return true;
    }

    public void salvar(View v) {
        datasource.open();
        datasource.update(
        		eventoEditado.getId(), eventoEditado.getEstado(), //id e estado permanecem iguais
        		text.getText().toString()); //texto muda
        datasource.close();
    }
    public void cancelar(View v) {
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
