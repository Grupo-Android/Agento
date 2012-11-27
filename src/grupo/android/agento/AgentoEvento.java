package grupo.android.agento;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class AgentoEvento extends Activity { 
	
	private EventosDataSource datasource;
	private String estado, evento;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento_evento);
        
        datasource = new EventosDataSource(this);
        datasource.open();
    	List<Eventos> values = datasource.getAllEventos();
    	datasource.close();
    	
    	//LENDO OS EVENTOS
    	for(int i = 0; i < values.size(); ++i) {
    		
    		datasource.open();
    		
    		TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);
    		tl.setStretchAllColumns(true);
    		
        	//Criando uma nova linha
        	TableRow tr = new TableRow(this);
        	tr.setWeightSum(1);
        	tr.setBackgroundResource(R.drawable.evento_gradiente);
        	
        	//Criando os componentes
        	ImageButton button = new ImageButton(this);
        	button.setImageResource(R.drawable.pen);
        	button.setBackgroundResource(0);
        	final int aux = i;
        	//EVENTO DO BOT�O EDITAR
        	button.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					edita(aux);
				}
			});
        	
        	final TextView text = new TextView(this);
        	evento = values.get(i).getEvento().toString();
        	text.setText(evento);
        	text.setMaxLines(1);
            text.setMaxEms(13);
            text.setTextSize(11);
        	
        	final CheckBox box = new CheckBox(this);
        	box.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(box.isChecked() == true){
						datasource.open();
				        datasource.update((aux + 1), "concluido", text.getText().toString());
				        datasource.close();
					}else{
						datasource.open();
				        datasource.update((aux + 1), "pendente", text.getText().toString());
				        datasource.close();
					}
				}
			});
        	if(values.get(i).getEstado().toString().equals("pendente"))
        		box.setChecked(false);
        		else
        			box.setChecked(true);

        	//Adicionando os componentes na linha
        	tr.addView(button);
        	tr.addView(text);
        	tr.addView(box);
        	//Adicionando a linha no layout
        	tl.addView(tr);
        	datasource.close();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agento_evento, menu);
        return true;     
    }
    
    //ADD EVENTO
    public void addEvento(View v){
    	datasource = new EventosDataSource(this);
    	datasource.open();
    	final List<Eventos> values = datasource.getAllEventos();
    	
    	TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);
    	
        //Criando uma nova linha
        TableRow tr = new TableRow(this);
        tr.setWeightSum(1);
        tr.setBackgroundResource(R.drawable.evento_gradiente);
        
        //Criando os componentes
        ImageButton button = new ImageButton(this);
        button.setImageResource(R.drawable.pen);
        button.setBackgroundResource(0);
        
    	//EVENTO DO BOT�O EDITAR
    	button.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				edita(values.size());
			}
		});
    	
        final TextView text = new TextView(this);
        text.setText("Arraste at� a lixeira para excluir");
        text.setMaxLines(1);
        text.setMaxEms(13);
        text.setTextSize(11);
        
        final CheckBox box = new CheckBox(this);
    	box.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(box.isChecked() == true){
					datasource.open();
			        datasource.update((values.size()), "concluido", text.getText().toString());
			        datasource.close();
				}else{
					datasource.open();
			        datasource.update((values.size()), "pendente", text.getText().toString());
			        datasource.close();
				}
			}
		});
        //Adicionando os componentes na linha
        tr.addView(button);
        tr.addView(text);
        tr.addView(box);
        //Adicionando a linha no layout
        tl.addView(tr);
        //Adicionando entrada no Banco de Dados
        datasource.insertEvento("pendente",text.getText().toString());
        datasource.close();
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
       		new Intent(this, Agento.class)
       	);
    }
    public void edita(int i){
    	Intent intent = new Intent(getBaseContext(), EditaEvento.class);
    	intent.putExtra("id_Evento", i);
    	startActivity(intent);
    	finish();
    }
}
