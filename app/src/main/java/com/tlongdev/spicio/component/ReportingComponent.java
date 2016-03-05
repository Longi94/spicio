package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.ReportingModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Singleton
@Component(modules = ReportingModule.class)
public interface ReportingComponent {
}
