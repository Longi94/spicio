package com.tlongdev.spicio.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.tlongdev.spicio.SpicioApplication;

/**
 * @author longi
 * @since 2016.04.19.
 */
public abstract class SpicioFragment extends Fragment {

    protected SpicioApplication mApplication;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (SpicioApplication) getActivity().getApplication();
    }
}
