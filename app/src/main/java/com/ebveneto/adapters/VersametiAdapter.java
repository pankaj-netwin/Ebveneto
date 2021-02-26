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
import com.ebveneto.models.Versameti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayur on 27-10-2017.
 */
public class VersametiAdapter extends ArrayAdapter<Versameti>
{
    ClickListener clickListener;
    Context context;
    String monthArray[] = {"GEN",
        "FEB",
        "MAR",
        "APR",
        "MAG",
        "GIU",
        "LUG",
        "AGO",
        "SET",
        "OTT",
        "NOV",
        "DIC"};
  //  JobClickListener listener;
    public VersametiAdapter(Context context,int resource, ArrayList<Versameti> objects, ClickListener clickListener)
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
            convertView = View.inflate(getContext(), R.layout.list_item_versameti, null);
            holder = new ViewHolder();
            holder.ivEdit = (ImageView) convertView.findViewById(R.id.ivEdit);
            holder.tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
//            holder.llLeft = (LinearLayout) convertView.findViewById(R.id.llLeft);
//            holder.llCenter = (LinearLayout) convertView.findViewById(R.id.llCenter);
//            holder.llRight = (LinearLayout) convertView.findViewById(R.id.llRight);
            holder.rlEdit = (RelativeLayout) convertView.findViewById(R.id.rlEdit);
            holder.llVersameti = (LinearLayout) convertView.findViewById(R.id.llVersameti);

            holder.tvValue1 = (TextView) convertView.findViewById(R.id.tvValue1);
            holder.tvValue2 = (TextView) convertView.findViewById(R.id.tvValue2);
            holder.tvValue3 = (TextView) convertView.findViewById(R.id.tvValue3);
            holder.tvValue4 = (TextView) convertView.findViewById(R.id.tvValue4);
            holder.tvValue5 = (TextView) convertView.findViewById(R.id.tvValue5);
            holder.tvValue6 = (TextView) convertView.findViewById(R.id.tvValue6);
            holder.tvValue7 = (TextView) convertView.findViewById(R.id.tvValue7);
            holder.tvValue8 = (TextView) convertView.findViewById(R.id.tvValue8);
            holder.tvValue9 = (TextView) convertView.findViewById(R.id.tvValue9);
            holder.tvValue10 = (TextView) convertView.findViewById(R.id.tvValue10);
            holder.tvValue11 = (TextView) convertView.findViewById(R.id.tvValue11);
            holder.tvValue12 = (TextView) convertView.findViewById(R.id.tvValue12);


            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvYear.setText(getItem(position).getAnnoCompetenza());
        holder.tvName.setText(getItem(position).getAzNome());

        if(TextUtils.equals(getItem(position).getJAN(),"null")){
            holder.tvValue1.setText("  -");

        }else{
            holder.tvValue1.setText(getItem(position).getJAN());
        }

        if(TextUtils.equals(getItem(position).getFEB(),"null")){
            holder.tvValue2.setText("  -");
        }else{
            holder.tvValue2.setText(getItem(position).getFEB());
        }

        if(TextUtils.equals(getItem(position).getMAR(),"null")){
            holder.tvValue3.setText("  -");
        }else{
            holder.tvValue3.setText(getItem(position).getMAR());
        }

        if(TextUtils.equals(getItem(position).getAPR(),"null")){
            holder.tvValue4.setText("  -");
        }else{
            holder.tvValue4.setText(getItem(position).getAPR());
        }

        if(TextUtils.equals(getItem(position).getMAY(),"null")){
            holder.tvValue5.setText("  -");
        }else{
            holder.tvValue5.setText(getItem(position).getMAY());
        }

        if(TextUtils.equals(getItem(position).getJUN(),"null")){
            holder.tvValue6.setText("  -");
        }else{
            holder.tvValue6.setText(getItem(position).getJUN());
        }

        if(TextUtils.equals(getItem(position).getJUL(),"null")){
            holder.tvValue7.setText("  -");
        }else{
            holder.tvValue7.setText(getItem(position).getJUL());
        }

        if(TextUtils.equals(getItem(position).getAUG(),"null")){
            holder.tvValue8.setText("  -");
        }else{
            holder.tvValue8.setText(getItem(position).getAUG());
        }

        if(TextUtils.equals(getItem(position).getSEP(),"null")){
            holder.tvValue9.setText("  -");
        }else{
            holder.tvValue9.setText(getItem(position).getSEP());
        }

        if(TextUtils.equals(getItem(position).getOCT(),"null")){
            holder.tvValue10.setText("  -");
        }else{
            holder.tvValue10.setText(getItem(position).getOCT());
        }

        if(TextUtils.equals(getItem(position).getNOV(),"null")){
            holder.tvValue11.setText("  -");
        }else{
            holder.tvValue11.setText(getItem(position).getNOV());
        }

        if(TextUtils.equals(getItem(position).getDEC(),"null")){
            holder.tvValue12.setText("  -");
        }else{
            holder.tvValue12.setText(getItem(position).getDEC());
        }
        /*holder.llLeft.removeAllViews();
        holder.llRight.removeAllViews();
        holder.llCenter.removeAllViews();
        for (int i = 0; i < monthArray.length; i++)
        {
            View view = (View) View.inflate(context, R.layout.list_item_versameti_month, null);
            TextView tvMonth = (TextView) view.findViewById(R.id.tvMonth);
            TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
            tvMonth.setText(monthArray[i]);
            switch (i){
                case  0:
                    if(TextUtils.equals(getItem(position).getJAN(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getJAN());
                    }

                    break;

                case  1:
                    if(TextUtils.equals(getItem(position).getFEB(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getFEB());
                    }
                    break;

                case  2:
                    if(TextUtils.equals(getItem(position).getMAR(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getMAR());
                    }
                    break;

                case  3:
                    if(TextUtils.equals(getItem(position).getAPR(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getAPR());
                    }
                    break;

                case  4:
                    if(TextUtils.equals(getItem(position).getMAY(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getMAY());
                    }
                    break;

                case  5:
                    if(TextUtils.equals(getItem(position).getJUN(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getJUN());
                    }
                    break;

                case  6:
                    if(TextUtils.equals(getItem(position).getJUL(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getJUL());
                    }
                    break;

                case  7:
                    if(TextUtils.equals(getItem(position).getAUG(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getAUG());
                    }
                    break;

                case  8:
                    if(TextUtils.equals(getItem(position).getSEP(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getSEP());
                    }
                    break;

                case  9:
                    if(TextUtils.equals(getItem(position).getOCT(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getOCT());
                    }
                    break;

                case  10:
                    if(TextUtils.equals(getItem(position).getNOV(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getNOV());
                    }
                    break;

                case  11:
                    if(TextUtils.equals(getItem(position).getDEC(),"null")){
                        tvValue.setText("  -");
                    }else{
                        tvValue.setText(getItem(position).getDEC());
                    }
                    break;
            }



            if (i % 3 == 0)
            {
                holder.llLeft.addView(view);

            } else if(i % 3 == 1)
            {
                holder.llCenter.addView(view);
            }else{
                holder.llRight.addView(view);
            }

        }*/

        holder.llVersameti.setOnClickListener(new View.OnClickListener()
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
        TextView tvYear, tvName, tvValue1, tvValue2, tvValue3, tvValue4, tvValue5, tvValue6, tvValue7;
        TextView tvValue8, tvValue9, tvValue10, tvValue11, tvValue12;
        LinearLayout llLeft, llCenter, llRight, llVersameti;
        RelativeLayout rlEdit;
        ImageView ivEdit;
    }
}
