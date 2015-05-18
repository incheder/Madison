package com.wezen.madison.com.wezen.madison.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wezen.madison.R;
import com.wezen.madison.adapter.CheckoutAdapter;
import com.wezen.madison.model.Beverage;
import com.wezen.madison.model.ItemForCheckout;
import com.wezen.madison.model.ShoppingCartItemType;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private CheckoutAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CheckoutFragment() {
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
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCheckoutList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CheckoutAdapter(fillDummyList(),getActivity(),getFragmentManager());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private ArrayList<ItemForCheckout> fillDummyList(){
        ArrayList<ItemForCheckout> list = new ArrayList<>();
        ItemForCheckout bfc = null;
        for(int i = 0; i< 10; i++){
            bfc = new ItemForCheckout(R.drawable.ic_content_add,
                    2,
                    new Beverage("Torres 10, 700ml","350",R.drawable.torres_10_700ml),
                    ShoppingCartItemType.NORMAL_ITEM,
                    null

            );

            list.add(bfc);
        }
        bfc = new ItemForCheckout(null,null,null,ShoppingCartItemType.TOTAL,1500.00);
        list.add(bfc);
        return list;
    }


}
