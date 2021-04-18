package com.example.homebuh;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.j256.ormlite.android.apptools.OpenHelperManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutgoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutgoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;

    private static DatabaseHelper databaseHelper;

    public OutgoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutgoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OutgoFragment newInstance(String param1, String param2) {
        OutgoFragment fragment = new OutgoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outgo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        pie = (PieChart) getActivity().findViewById(R.id.mySimplePieChart);

        // названия сегментов диаграммы
        String c1 = "Автомобиль";
        String c2 = "Продукты питания";
        String c3 = "Коммунальные платежи";
        String c4 = "Медицина";

        // получение данных по расходам по каждой категории
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select sum(`sum`) from operation where category_id = 3", null);
        cursor.moveToFirst();
        float f1 = cursor.getFloat(0);
        Log.e(getClass().toString(), "sum = " + f1);

        cursor = databaseHelper.getReadableDatabase().rawQuery("select sum(`sum`) from operation where category_id = 1", null);
        cursor.moveToFirst();
        float f2 = cursor.getFloat(0);

        cursor = databaseHelper.getReadableDatabase().rawQuery("select sum(`sum`) from operation where category_id = 2", null);
        cursor.moveToFirst();
        float f3 = cursor.getFloat(0);

        cursor = databaseHelper.getReadableDatabase().rawQuery("select sum(`sum`) from operation where category_id = 4", null);
        cursor.moveToFirst();
        float f4 = cursor.getFloat(0);

        s1 = new Segment(c1, f1);
        s2 = new Segment(c2, f2);
        s3 = new Segment(c3, f3);
        s4 = new Segment(c4, f4);

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getActivity(), R.xml.pie_segment_formatter1);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getActivity(), R.xml.pie_segment_formatter2);
        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getActivity(), R.xml.pie_segment_formatter3);
        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getActivity(), R.xml.pie_segment_formatter4);
        sf4.getFillPaint().setMaskFilter(emf);

        pie.addSeries(s1, sf1);
        pie.addSeries(s2, sf2);
        pie.addSeries(s3, sf3);
        pie.addSeries(s4, sf4);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        pie.getRenderer(PieRenderer.class).setDonutSize(0.1f, PieRenderer.DonutMode.PERCENT);
        pie.redraw();
    }

}
