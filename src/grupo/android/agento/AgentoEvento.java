package grupo.android.agento;

import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.List;

import android.app.ActionBar.LayoutParams;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AgentoEvento extends Activity { 
	private EventosDataSource datasource;
	private String evento;
    private QuickActionWidget mBar;
    private final String DEFAULT_MSG = "Touch to Edit";
    private int aux;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento_evento);

        datasource = new EventosDataSource(this);
        
        prepareQuickActionBar();        
        carregaEventoSalvo();
    }

	private void carregaEventoSalvo() {
		
        datasource.open();
    	List<Eventos> values = datasource.getAllEventos();
    	datasource.close();
    	
    	//LENDO OS EVENTOS
    	for(int i = 0; i < values.size(); ++i) {
    		
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
        	
        	//EVENTO DO BOTÃO EDITAR
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
       this.finish();
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
            	if(position == 0){ //edit
            		edita(aux);
            	}
                
            }
        });
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
