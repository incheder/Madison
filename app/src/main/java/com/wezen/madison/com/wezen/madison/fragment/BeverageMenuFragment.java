package com.wezen.madison.com.wezen.madison.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wezen.madison.R;
import com.wezen.madison.adapter.BeverageMenuAdapter;
import com.wezen.madison.adapter.GridAdapter;
import com.wezen.madison.model.BeverageMenu;
import com.wezen.madison.model.BeverageType;
import com.wezen.madison.utils.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeverageMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeverageMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private BeverageMenuAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeverageMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeverageMenuFragment newInstance(String param1, String param2) {
        BeverageMenuFragment fragment = new BeverageMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BeverageMenuFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_beverage_menu, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvBeverageMenu);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new BeverageMenuAdapter(dummyList(),getActivity(),getFragmentManager());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private ArrayList<BeverageMenu> dummyList() {

        ArrayList<BeverageMenu> list = new ArrayList<>();
       // for (int i = 0; i<5;i++){
            list.add(new BeverageMenu(R.drawable.menu_brandy,"Brandy"));
            list.add(new BeverageMenu(R.drawable.menu_beer,"Cerveza"));
            list.add(new BeverageMenu(R.drawable.menu_champagne,"Champagne"));
            list.add(new BeverageMenu(R.drawable.menu_ron,"Ron"));
            list.add(new BeverageMenu(R.drawable.menu_tequila,"Tequila"));
            list.add(new BeverageMenu(R.drawable.menu_vino,"Vino"));
            list.add(new BeverageMenu(R.drawable.menu_vodka,"Vodka"));
            list.add(new BeverageMenu(R.drawable.menu_whiskey,"Whiskey"));
      //  }
        return list;
    }


}
