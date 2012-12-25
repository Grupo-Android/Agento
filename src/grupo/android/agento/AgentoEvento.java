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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

public class AgentoEvento extends Activity {
	private EventosDataSource datasource;
	private QuickActionWidget mBar;
	private final String DEFAULT_MSG = "Touch to Edit";
	private int linhaSelecionada; /* usado para controle da linha que for selecionada na ListView */
	private List<Eventos> values; // list que contem todos os eventos
	private QuickActionAdapter adapter;
	private ImageView completadoCheck = null;
	public final static String ID_EVENTO = "grupo.android.agento.MESSAGE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agento_evento);

		datasource = new EventosDataSource(this);

		datasource.open();

		// metodos de inicializacao da activity
		updateValues();
		prepareQuickActionBar();
		carregaEventoSalvo();
	}

	private void carregaEventoSalvo() {
		ListView lista = (ListView) findViewById(R.id.eventoListView);

		adapter = new QuickActionAdapter(this);

		adapter.setData(values);
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				linhaSelecionada = position; // set the selected row
				mBar.show(view);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.agento_evento, menu);
		return true;
	}

	public void addEvento(View v) {
		if (datasource == null)
			datasource.open();

		Eventos defaultEvento = new Eventos(
				0, "pendente", DEFAULT_MSG); // o id não importa
		datasource.insertEvento(defaultEvento);

		// atualiza mudanças na tela
		updateValues();
		adapter.setData(values);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// fecha banco
		if (datasource != null)
			datasource.close();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		// fecha o datasource se estiver aberto
		if (datasource != null)
			datasource.close();

		this.finish();
		startActivity(new Intent(this, Agento.class));
	}

	public void edita(int i) {
		// fecha o datasource se estiver aberto
		if (datasource != null)
			datasource.close();

		Intent intent = new Intent(this, EditaEvento.class);
		intent.putExtra(ID_EVENTO, values.get(i).getId());

		startActivity(intent);
		finish();
	}

	public void onShowBar(View v) {
		mBar.show(v);
	}

	private void prepareQuickActionBar() {
		mBar = new QuickActionBar(this);
		mBar.addQuickAction(new MyQuickAction(this,
				R.drawable.gd_action_bar_edit, R.string.gd_edit));
		mBar.addQuickAction(new MyQuickAction(this,
				R.drawable.gd_action_bar_trashcan, R.string.gd_trashcan));
		mBar.addQuickAction(new MyQuickAction(this,
				R.drawable.navigation_accept, R.string.completar));

		mBar.setOnQuickActionClickListener(new OnQuickActionClickListener() {
			public void onQuickActionClicked(QuickActionWidget widget,
					int position) {
				if (position == 0) { // edit
					edita(linhaSelecionada);
				} else if (position == 1) { // delete
					deleta();
				} else if (position == 2) { // completar
					completar();
				}
			}
		});
	}

	public void deleta() {
		datasource.deleteEvento(values.get(linhaSelecionada));
		values.remove(linhaSelecionada);
		adapter.notifyDataSetChanged();
	}

	// abre o banco e carrega values com todos os eventos
	public void updateValues() {
		values = datasource.getAllEventos();
	}

	public void completar() {
		if (datasource == null)
			datasource.open();

		Eventos evento = values.get(linhaSelecionada);
		completadoCheck = (ImageView) findViewById(R.id.ic_more);

		// modifica o status
		if (evento.getEstado().equals("pendente")) {
			datasource.update(evento.getId(), "concluido", evento.getEvento());

			// troca icone para completado
			completadoCheck.setImageResource(R.drawable.navigation_accept_two);
		} else {
			datasource.update(evento.getId(), "pendente", evento.getEvento());

			// troca icone para não completado
			completadoCheck.setImageResource(R.drawable.navigation_accept);
		}

		// atualiza mudanças na tela
		updateValues();
		adapter.setData(values);
		adapter.notifyDataSetChanged();
	}

	private static class MyQuickAction extends QuickAction {

		private static final ColorFilter BLACK_CF = new LightingColorFilter(
				Color.BLACK, Color.BLACK);

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
