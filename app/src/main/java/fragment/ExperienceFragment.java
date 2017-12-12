package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.TimeLineAdapter;
import faridnia.milad.myresume.R;
import model.TimeLineModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExperienceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExperienceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExperienceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();

    public ExperienceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExperienceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExperienceFragment newInstance() {
        ExperienceFragment fragment = new ExperienceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_experience, container, false);
        //  return

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        initView();

        return root;

    }

    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void setDataListItems() {
        mDataList.add(new TimeLineModel("Developing apps for both Android and IOS Platforms.\n", "Mobile Developer at Afranet", "Mar 2016 → Current (1 year, 10 months)"));
        mDataList.add(new TimeLineModel("As an Android Developer I am responsible to keep and develop android applications for \"Maadiran R&D Department\". I also have work experience in building android kernels for Smart TVs and Smart Phones.",
                "Software Developer at Maadiran", "2014 → 2016 (3 years)"));
        mDataList.add(new TimeLineModel("Android Developer developing and publishing application on android markets.",
                "Android Developer at Armangarayan Technology Development", "2013 → 2014 (2 years)"));


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }
}
