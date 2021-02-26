package com.ebveneto.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebveneto.R;
import com.ebveneto.models.RequestedServiceYear;
import com.ebveneto.widgets.Helper;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Mayur on 26-10-2017.
 */
public class RequestedServiceYearAdapter extends BaseAdapter implements StickyListHeadersAdapter
{
    private LayoutInflater inflater;
    private ArrayList<RequestedServiceYear> requestedServiceYearList;
    Context context;

    private final OnAttachmentClickListener listener;

    public interface OnAttachmentClickListener {
        void onItemClick(RequestedServiceYear item);
    }

    public RequestedServiceYearAdapter(Context context,
                                       ArrayList<RequestedServiceYear> requestedServiceYearList,
                                       OnAttachmentClickListener listener ) {
        this.context = context;
        this.requestedServiceYearList = requestedServiceYearList;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return requestedServiceYearList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestedServiceYearList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // final News news = requestedServiceYearList.get(position);
        //if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_requested_services, parent, false);
            holder.tvServiceName = (TextView) convertView.findViewById(R.id.tvServiceName);
            holder.tvNrTot = (TextView) convertView.findViewById(R.id.tvNrTot);
            holder.tvNrPagato = (TextView) convertView.findViewById(R.id.tvNrPagato);
            holder.tvTotPagato = (TextView) convertView.findViewById(R.id.tvTotPagato);
            holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.rlTotal = (RelativeLayout) convertView.findViewById(R.id.rlTotal);

            holder.llAllYears = (LinearLayout) convertView.findViewById(R.id.llAllYears);
            holder.llCurrentYear = (LinearLayout) convertView.findViewById(R.id.llCurrentYear);

            holder.tvAmounnt = (TextView) convertView.findViewById(R.id.tvAmounnt);
            holder.tvLastDateOfIntegration = (TextView) convertView.findViewById(R.id.tvLastDateOfIntegration);
            holder.tvDatePayment = (TextView) convertView.findViewById(R.id.tvDatePayment);
            holder.tvPaymentTiming = (TextView) convertView.findViewById(R.id.tvPaymentTiming);
            holder.tvRequestedDate = (TextView) convertView.findViewById(R.id.tvRequestedDate);
            holder.tvStato = (TextView) convertView.findViewById(R.id.tvStato);
            holder.btnInvia = (Button) convertView.findViewById(R.id.btnInvia);
            holder.noteFieldValue = (TextView) convertView.findViewById(R.id.noteFieldValue);

         //   TextView tvAmounnt, tvPaymentTiming,tvLastDateOfIntegration,tvDatePayment,tvRequestedDate,tvStato;
//            convertView.setTag(holder);
        }
//      else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        // int state = (int) holder.youTubeThumbnailView.getTag(R.id.initialize);

        Log.d("***loggg",""+requestedServiceYearList.get(position).isCurrentYear());
        if(requestedServiceYearList.get(position).isCurrentYear()){
            holder.llAllYears.setVisibility(View.GONE);
            holder.llCurrentYear.setVisibility(View.VISIBLE);


            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentWebTitolo(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentWebTitolo(),"")){
                holder.tvServiceName.setText("");
            }else{
                holder.tvServiceName.setText(requestedServiceYearList.get(position).getCurrentWebTitolo());
            }

            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentRequestedDate(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentRequestedDate(),"")){
                holder.tvRequestedDate.setText("-");
            }else{
                holder.tvRequestedDate.setText(Helper.formatDate(requestedServiceYearList.get(position).getCurrentRequestedDate()));
            }


            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentDatePayment(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentDatePayment(),"")){
                holder.tvDatePayment.setText("-");
            }else{
                holder.tvDatePayment.setText(Helper.formatDate(requestedServiceYearList.get(position).getCurrentDatePayment()));
            }


            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentAmount(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentAmount(),"")){
                holder.tvDatePayment.setText("-");
            }else{
               holder.tvAmounnt.setText(requestedServiceYearList.get(position).getCurrentAmount().replace(".",",")+" "+context.getResources().getString(R.string.euro));
            }

            if(TextUtils.equals(requestedServiceYearList.get(position).getNoteFiledValue(),"null") ||
                    TextUtils.equals(requestedServiceYearList.get(position).getNoteFiledValue(),"")){
                holder.noteFieldValue.setText("-");

            }else {
                holder.noteFieldValue.setText(requestedServiceYearList.get(position).getNoteFiledValue());

            }

            if(requestedServiceYearList.get(position).getIsAttachementShow()){
                holder.btnInvia.setVisibility(View.VISIBLE);
                Log.d("****Checking","V "+requestedServiceYearList.get(position).getIsAttachementShow());
            }else {
                holder.btnInvia.setVisibility(View.GONE);
                Log.d("****Checking","I"+requestedServiceYearList.get(position).getIsAttachementShow());
            }

            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentPaymentTiming(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentPaymentTiming(),"")){
                holder.tvPaymentTiming.setText("-");
            }else{
                holder.tvPaymentTiming.setText(requestedServiceYearList.get(position).getCurrentPaymentTiming()+" giorni");
            }

            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentState(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentState(),"")){
                holder.tvStato.setText("-");
            }else{
                holder.tvStato.setText(requestedServiceYearList.get(position).getCurrentState());
            }

            if(TextUtils.equals(requestedServiceYearList.get(position).getCurrentLastDateOfIntegration(),"null") || TextUtils.equals(requestedServiceYearList.get(position).getCurrentLastDateOfIntegration(),"")){
                holder.tvLastDateOfIntegration.setText("-");
            }else{
                holder.tvLastDateOfIntegration.setText(Helper.formatDate(requestedServiceYearList.get(position).getCurrentLastDateOfIntegration()));
            }

        }else {
            holder.llAllYears.setVisibility(View.VISIBLE);
            holder.llCurrentYear.setVisibility(View.GONE);
            holder.tvServiceName.setText(requestedServiceYearList.get(position).getServiceName());
            holder.tvNrTot.setText(requestedServiceYearList.get(position).getNrTot());
            holder.tvNrPagato.setText(requestedServiceYearList.get(position).getNrPagato());

            String amount = requestedServiceYearList.get(position).getTotPagato().replace(".",",");
            holder.tvTotPagato.setText(amount+" "+context.getResources().getString(R.string.euro));
            //  holder.tvTotPagato.setText(requestedServiceYearList.get(position).getTotPagato());
            Log.d("***isLatElment",""+requestedServiceYearList.get(position).isLastElement());
            if(requestedServiceYearList.get(position).isLastElement()){
                holder.rlTotal.setVisibility(View.VISIBLE);
                String result = String.format("%.2f", requestedServiceYearList.get(position).getFinalTotal());
                //  String total = ""+;
                String finalTotal = result.replace(".",",");
                Log.d("****finalTotal",""+finalTotal);
                holder.tvTotal.setText(finalTotal+" "+context.getResources().getString(R.string.euro));
            }else {
                holder.rlTotal.setVisibility(View.GONE);
            }
        }
        holder.btnInvia.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(requestedServiceYearList.get(position));
            }
        });

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_item_service_header, parent, false);
            holder.tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = requestedServiceYearList.get(position).getAnnoCompetenza();
        holder.tvYear.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
       // return requestedServiceYearList.get(position).getCategoryId();
        return Integer.parseInt(requestedServiceYearList.get(position).getAnnoCompetenza());
    }

    class HeaderViewHolder {
        TextView tvYear;
    }

    class ViewHolder {
        TextView tvServiceName, tvNrTot, tvNrPagato, tvTotPagato, tvTotal;
        RelativeLayout rlTotal;
        LinearLayout llAllYears, llCurrentYear;
        TextView tvAmounnt, tvPaymentTiming,tvLastDateOfIntegration,tvDatePayment,tvRequestedDate,tvStato,noteFieldValue;
        Button btnInvia;
    }

}
