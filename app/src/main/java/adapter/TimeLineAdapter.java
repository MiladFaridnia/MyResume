package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.github.vipulasri.timelineview.sample.R;
//import com.github.vipulasri.timelineview.sample.model.OrderStatus;
//import com.github.vipulasri.timelineview.sample.model.Orientation;
//import com.github.vipulasri.timelineview.sample.model.TimeLineModel;
//import com.github.vipulasri.timelineview.sample.utils.DateTimeUtils;
//import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import faridnia.milad.myresume.R;
import model.TimeLineModel;
import model.TimeLineViewHolder;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<TimeLineModel> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

            view = mLayoutInflater.inflate( R.layout.item_timeline_line_padding, parent, false);

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);

//        if(timeLineModel.getDuration() == OrderStatus.INACTIVE) {
//            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
//        } else if(timeLineModel.getDuration() == OrderStatus.ACTIVE) {
//            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
//        } else {
//            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
//        }

        if(!timeLineModel.getTitle().isEmpty()) {
            holder.mTitle.setVisibility(View.VISIBLE);
            holder.mTitle.setText(timeLineModel.getTitle());

        }
        else
            holder.mTitle.setVisibility(View.GONE);

        holder.mDescription.setText(timeLineModel.getDescription());
        holder.mDuration.setText(timeLineModel.getDuration());
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}
