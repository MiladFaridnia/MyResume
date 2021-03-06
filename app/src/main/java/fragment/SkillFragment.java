package fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import faridnia.milad.myresume.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SkillFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SkillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillFragment extends Fragment {


    private RadarChart mChart;
    private Typeface mTfLight;
    private SparseIntArray factors = new SparseIntArray(6);
    //    private SparseIntArray scores = new SparsFloatArray(6);
    private Float[] scores = {85f, 70f, 60f, 65f, 80f, 90f};
    private ArrayList<RadarEntry> entries = new ArrayList<>();
    private ArrayList<IRadarDataSet> dataSets = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public SkillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SkillFragment.
     */
    public static SkillFragment newInstance() {
        SkillFragment fragment = new SkillFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        factors.append(1, R.string.android_studio);
        factors.append(2, R.string.material_design);
        factors.append(3, R.string.design_patterns);
        factors.append(4, R.string.maps);
        factors.append(5, R.string.databases);
        factors.append(6, R.string.activity_fragment);

        //  mTfLight = Typeface.createFromAsset(getActivity().getAssets(), Constants.);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_skill, container, false);

        initRadarChart(root);

        drawRadarChart();



        initHorizontalChart(root);

        return root;
    }

    private void initHorizontalChart(View root) {
        BarChart horizontalBarChart =  root.findViewById(R.id.horizontalBarChart);


        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");


        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        horizontalBarChart.setData(data);
        horizontalBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        horizontalBarChart.invalidate(); // refresh





    }

    private void initRadarChart(View rootView) {
        mChart = (RadarChart) rootView.findViewById(R.id.radarChart);

        XAxis xAxis = mChart.getXAxis();

        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(8f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mFactors = new String[]{getString(factors.get(1)), getString(factors.get(2)),
                    getString(factors.get(3)), getString(factors.get(4)), getString(factors.get(5)), getString(factors.get(6))};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mFactors[(int) value % mFactors.length];
            }

        });

        YAxis yAxis = mChart.getYAxis();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setTypeface(mTfLight);
        yAxis.setTextSize(14f);
        yAxis.setLabelCount(6, false);
        yAxis.setDrawLabels(false);

        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

    }


    private void drawRadarChart() {

        entries.clear();

        for (int i = 0; i <= 5; i++) {
            entries.add(new RadarEntry(scores[i]));
        }


        RadarDataSet dataSet = new RadarDataSet(entries, "");
        dataSet.setColor(R.color.colorPrimary);
        dataSet.setDrawFilled(true);

        dataSets.add(dataSet);

        RadarData data = new RadarData(dataSets);
        //   data.setValueTypeface(mTfLight);
        data.setValueTextSize(8f);

        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value);
            }

        });

        mChart.setData(data);
        mChart.invalidate();
    }







    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        // void onFragmentInteraction(Uri uri);
    }
}
