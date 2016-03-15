package com.tlongdev.spicio.presentation.ui.view;

import android.content.Context;

import com.tlongdev.spicio.SpicioApplication;

/**
 * Outer Layer, UI.
 * The base view interface of the MVP architecture.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public interface BaseView {
    Context getContext();
    SpicioApplication getSpicioApplication();
}
