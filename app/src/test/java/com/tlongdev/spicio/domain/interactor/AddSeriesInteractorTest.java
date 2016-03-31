package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.AddSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.AddSeriesInteractorImpl;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 31.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddSeriesInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private AddSeriesInteractor.Callback mMockedCallback;

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
        Series series = mock(Series.class);

        when(mRepository.addSeries(1L, series)).thenReturn(true);

        AddSeriesInteractorImpl interactor = new AddSeriesInteractorImpl(
                mApp, 1L, series, mMockedCallback
        );
        interactor.run();

        verify(mRepository).addSeries(1L, series);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onAddSeriesFinish();
    }

    @Test
    public void testFail() {
        Series series = mock(Series.class);

        when(mRepository.addSeries(1L, series)).thenReturn(false);

        AddSeriesInteractorImpl interactor = new AddSeriesInteractorImpl(
                mApp, 1L, series, mMockedCallback
        );
        interactor.run();

        verify(mRepository).addSeries(1L, series);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onAddSeriesFail();
    }
}
