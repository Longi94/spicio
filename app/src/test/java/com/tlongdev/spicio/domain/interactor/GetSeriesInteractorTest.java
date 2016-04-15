package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.GetSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.module.FakeThreadingModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author longi
 * @since 2016.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetSeriesInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private GetSeriesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Before
    public void setUp() {

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setSpicioRepository(mRepository);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .networkRepositoryModule(networkRepositoryModule)
                .daoModule(mock(DaoModule.class))
                .threadingModule(new FakeThreadingModule())
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }


    @Test
    public void testSuccess() {
        List<Series> series = new ArrayList<>();

        when(mRepository.getSeries(1L)).thenReturn(series);

        GetSeriesInteractorImpl interactor = new GetSeriesInteractorImpl(
                mApp, 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getSeries(1L);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetSeriesFinish(series);
    }

    @Test
    public void testFail() {
        when(mRepository.getSeries(1L)).thenReturn(null);

        GetSeriesInteractorImpl interactor = new GetSeriesInteractorImpl(
                mApp, 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getSeries(1L);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetSeriesFail();
    }
}
