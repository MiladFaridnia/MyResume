package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by  on 05-12-2015.
 */
public class TimeLineModel implements Parcelable {

    private String mTitle;
    private String mDuration;
    private String mDescription;

    public TimeLineModel() {
    }

    public TimeLineModel(String mDescription, String mTitle, String mDuration) {
        this.mDescription = mDescription;
        this.mTitle = mTitle;
        this.mDuration = mDuration;
    }

    public String getDescription() {
        return mDescription;
    }

    public void semDescription(String message) {
        this.mDescription = message;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String date) {
        this.mTitle = date;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String mStatus) {
        this.mDuration = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescription);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDuration);
    }

    protected TimeLineModel(Parcel in) {
        this.mDescription = in.readString();
        this.mTitle = in.readString();
        this.mDuration = in.readString();
    }

    public static final Parcelable.Creator<TimeLineModel> CREATOR = new Parcelable.Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel source) {
            return new TimeLineModel(source);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };
}
