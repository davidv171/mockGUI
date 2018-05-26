package davidv7.avastgui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.MyViewHolder> {
    private List<AppList> appList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.appTitle);
            icon =  view.findViewById(R.id.appIcon);
        }
    }


    public AppListAdapter(List<AppList> appsList) {
        this.appList = appsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);
        System.out.println("Viewholder created");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppList app = appList.get(position);
        holder.title.setText(app.getAppName());
        holder.icon.setImageDrawable(app.getAppIcon());

    }

    @Override
    public int getItemCount() {
        return appList.size();
    }
}


