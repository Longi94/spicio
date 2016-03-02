package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.repository.TvdbServiceRepository;
import com.tlongdev.spicio.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(TvdbServiceRepository TvdbServiceRepository);
}
