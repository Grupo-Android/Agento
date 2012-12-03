package grupo.android.agento;

import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AgentoEvento extends Activity { 
	private EventosDataSource datasource;
	private String evento;
    private QuickActionWidget mBar;
    private final String DEFAULT_MSG = "Touch to Edit";
    private int linhaSelecionada;
    private List<Eventos> values;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento_evento);

        datasource = new EventosDataSource(this);
        
        updateValues();
    	
        prepareQuickActionBar();        
        carregaEventoSalvo();
    }

	private void carregaEventoSalvo() {
		ListView lista = (ListView) findViewById(R.id.eventoListView);
   		
   		QuickActionAdapter adapter = new QuickActionAdapter(this);
    		
   		adapter.setData(values);
   		lista.setAdapter(adapter);
    		
   		lista.setOnItemClickListener(new OnItemClickListener() {
   			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
   				linhaSelecionada = position; //set the selected row
   				mBar.show(view);
			}
		});    	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agento_evento, menu);
        return true;     
    }
    
    //ADD EVENTO
    public void addEvento(View v) {
    	/*datasource = new EventosDataSource(this);
    	datasource.open();
    	final List<Eventos> values = datasource.getAllEventos();
    	

    	
        //Criando uma nova linha
        TableRow tr = new TableRow(this);
        tr.setWeightSum(1);
        tr.setBackgroundResource(R.drawable.evento_gradiente);
        
        //Criando os componentes
        ImageButton button = new ImageButton(this);
        button.setImageResource(R.drawable.pen);
        button.setBackgroundResource(0);
        
    	//EVENTO DO BOTÃO EDITAR
    	button.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				edita(values.size());
			}
		});
    	
        final TextView text = new TextView(this);
        text.setText(DEFAULT_MSG);
        text.setMaxLines(1);
        text.setMaxEms(13);
        text.setTextSize(11);
        text.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onShowBar(v);
			}
		});
        
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
        Adicionando os componentes na linha
        tr.addView(button);
        tr.addView(text);
        tr.addView(box);
        
    	//Adicionando a linha no layout
        //tl.addView(tr);
        //Adicionando entrada no Banco de Dados
        datasource.insertEvento("pendente",text.getText().toString());
        datasource.close();*/
    }

    @Override
	protected void onDestroy() {
		super.onDestroy();
		//fecha banco
		if(datasource != null)
			datasource.close();
	}
    
    @Override
    public void onBackPressed() {
       super.onBackPressed();
       this.finish();
       startActivity(
       		new Intent(this, Agento.class)
       	);
    }
    
    public void edita(int i) {
    	Intent intent = new Intent(getBaseContext(), EditaEvento.class);
    	intent.putExtra("id_Evento", i);
    	startActivity(intent);
    	finish();
    }
    
    public void onShowBar(View v) {
        mBar.show(v);
    }

    private void prepareQuickActionBar() {
        mBar = new QuickActionBar(this);
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_edit, R.string.gd_edit));
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_trashcan, R.string.gd_trashcan));
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_share, R.string.gd_share));
        
        mBar.setOnQuickActionClickListener(new OnQuickActionClickListener() {
            public void onQuickActionClicked(QuickActionWidget widget, int position) {
            	if (position == 0){ //edit
            		edita(linhaSelecionada);
            	}else if (position == 1){
            		deleta();
            	}
            }
        });
    }
    
  //TODO concertar essa gambiarra
    public void deleta() {
    	datasource.open();
    	datasource.deleteEvento(values.get(linhaSelecionada));
    	updateValues();
    	datasource.close();
    	carregaEventoSalvo();
    }
    
    //abre o banco e carrega values com todos os eventos
    public void updateValues(){
    	datasource.open();
        values = datasource.getAllEventos();
    	datasource.close();
    }
    
    private static class MyQuickAction extends QuickAction {
        
        private static final ColorFilter BLACK_CF = new LightingColorFilter(Color.BLACK, Color.BLACK);

        public MyQuickAction(Context ctx, int drawableId, int titleId) {
            super(ctx, buildDrawable(ctx, drawableId), titleId);
        }
        
        private static Drawable buildDrawable(Context ctx, int drawableId) {
            Drawable d = ctx.getResources().getDrawable(drawableId);
            d.setColorFilter(BLACK_CF);
            return d;
        }
        
    }


}
