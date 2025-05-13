package com.example.myfoodapp.adapters;

import com.example.myfoodapp.models.HomeVerModel;

import java.util.ArrayList;

public interface UpdateVerticalRec {
    void callback(int position);

    void callback(int position, ArrayList<HomeVerModel> list);
}
