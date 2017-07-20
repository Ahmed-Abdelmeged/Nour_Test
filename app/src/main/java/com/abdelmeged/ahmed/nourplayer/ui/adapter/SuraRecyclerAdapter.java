package com.abdelmeged.ahmed.nourplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abdelmeged.ahmed.nourplayer.R;
import com.abdelmeged.ahmed.nourplayer.model.Sura;
import com.abdelmeged.ahmed.nourplayer.utils.DownloadUtils;

import java.util.ArrayList;

/**
 * Created by ahmed on 7/20/2017.
 */

public class SuraRecyclerAdapter extends RecyclerView.Adapter<SuraRecyclerAdapter.SuraViewHolder> {

    private ArrayList<Sura> suras;
    private Context context;

    private final SuraClickCallbacks suraClickCallbacks;
    private final SuraDownloadClickCallbacks suraDownloadClickCallbacks;

    public SuraRecyclerAdapter(Context context, SuraClickCallbacks suraClickCallbacks, SuraDownloadClickCallbacks suraDownloadClickCallbacks) {
        this.suraClickCallbacks = suraClickCallbacks;
        this.suraDownloadClickCallbacks = suraDownloadClickCallbacks;
        this.context = context;
    }

    @Override
    public SuraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_sura;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new SuraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuraViewHolder holder, int position) {
        Sura currentSura = suras.get(position);
        holder.suraName.setText(currentSura.getName());

        if (DownloadUtils.isSuraDownloaded(currentSura.getQuranIndex(), context)) {
            holder.downloadButton.setEnabled(false);
            holder.downloadButton.setImageResource(R.drawable.cloud_check);
            holder.suraDownloadingProgressBar.setVisibility(View.GONE);
            holder.downloadButton.setVisibility(View.VISIBLE);
        } else {
            holder.downloadButton.setEnabled(true);
            holder.downloadButton.setImageResource(R.drawable.cloud_download);
            holder.suraDownloadingProgressBar.setVisibility(View.GONE);
            holder.downloadButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (suras == null) return 0;
        return suras.size();
    }

    public class SuraViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView suraName;
        ImageButton downloadButton;
        ProgressBar suraDownloadingProgressBar;

        public SuraViewHolder(View itemView) {
            super(itemView);
            suraName = (TextView) itemView.findViewById(R.id.sura_name);
            downloadButton = (ImageButton) itemView.findViewById(R.id.sura_download_button);
            suraDownloadingProgressBar = (ProgressBar) itemView.findViewById(R.id.sura_downloading_progress_bar);

            //set the click listener
            downloadButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (suras != null) {
                if (v.getId() == downloadButton.getId()) {
                    suraDownloadClickCallbacks.onSuraDownloadClick(suras.get(getAdapterPosition()));
                    downloadButton.setVisibility(View.GONE);
                    suraDownloadingProgressBar.setVisibility(View.VISIBLE);
                } else {
                    suraClickCallbacks.onSuraClick(suras.get(getAdapterPosition()));
                }
            }
        }
    }


    public void setSuras(ArrayList<Sura> suras) {
        this.suras = suras;
        notifyDataSetChanged();
    }

    public void addSura(Sura sura) {
        this.suras.add(sura);
        notifyDataSetChanged();
    }

    public void clear() {
        if (suras != null) {
            this.suras.clear();
            notifyDataSetChanged();
        }
    }
}
