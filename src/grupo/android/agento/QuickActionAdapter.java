package grupo.android.agento;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class QuickActionAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Eventos> data;
	
	public QuickActionAdapter(Context context) { 
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<Eventos> data) {
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int item) {
		return data.get(item);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView	= mInflater.inflate(R.layout.list, null);
			
			holder = new ViewHolder();
			
			holder.mTitleText = (TextView) convertView.findViewById(R.id.t_name);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mTitleText.setText(data.get(position).getEvento().toString());
		holder.mTitleText.setBackgroundResource(R.drawable.evento_gradiente);
		
		return convertView;
	}

	static class ViewHolder {
		TextView mTitleText;
	}
}