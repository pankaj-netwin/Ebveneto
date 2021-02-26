package com.ebveneto.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebveneto.R;
import com.ebveneto.interfaces.ClickListener;
import com.ebveneto.models.Sedi;
import com.ebveneto.models.Versameti;

import java.util.ArrayList;

/**
 * Created by Mayur on 27-10-2017.
 */
public class SediAdapter extends ArrayAdapter<Sedi>
{
    ClickListener clickListener;
    Context context;

  //  JobClickListener listener;
    public SediAdapter(Context context, int resource, ArrayList<Sedi> objects, ClickListener clickListener)
    {
        super(context, resource, objects);
        this.context = context ;
        this.clickListener = clickListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(getContext(), R.layout.list_tem_sedi, null);
            holder = new ViewHolder();

            holder.tvSedeItemAddress = (TextView) convertView.findViewById(R.id.tvSedeItemAddress);
            holder.llSedi = (LinearLayout) convertView.findViewById(R.id.llSedi);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }




        String via = getItem(position).getVia();
        String comune = getItem(position).getComune();
        String prov = getItem(position).getProv();

        ArrayList<String> addressList = new ArrayList<String>();
        if(TextUtils.equals(via,"null")){
            via = "";
        }else {
            addressList.add(via) ;
        }

        if(TextUtils.equals(comune,"null")){
            comune = "";
        }else {
            addressList.add(comune) ;
        }
        if(TextUtils.equals(prov,"null")){
            prov = "";
        }else {
            addressList.add(prov) ;
        }

        String address = "";
        for (int i = 0; i < addressList.size(); i++)
        {
            if(i==0){
                address = addressList.get(0);
            }else {
                address =  address + ", " + addressList.get(i);
            }
        }
        holder.tvSedeItemAddress.setText(address);

     //   holder.tvSedeItemAddress.setText((via+", "+comune+", "+", "+prov+"."));

        holder.llSedi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickListener.onButtonClicked(position);
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        TextView tvSedeItemAddress;
        LinearLayout llSedi;
    }
}
