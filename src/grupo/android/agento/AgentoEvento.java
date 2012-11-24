package grupo.android.agento;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class AgentoEvento extends Activity { 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agento_evento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agento_evento, menu);
        return true;
    }
    
    public void addEvento(View v){
    	TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);
        //Criando uma nova linha
        TableRow tr = new TableRow(this);
        tr.setWeightSum(1);
        //Criando os componentes
        ImageButton button = new ImageButton(this);
        button.setImageResource(R.drawable.pen);
        
        TextView text = new TextView(this);
        text.setText("Arraste até a lixeira para excluir");
        
        CheckBox box = new CheckBox(this);
        
        //Adicionando os componentes na linha
        tr.addView(button);
        tr.addView(text);
        tr.addView(box);
        //Adicionando a linha no layout
        tl.addView(tr);
    }
}
