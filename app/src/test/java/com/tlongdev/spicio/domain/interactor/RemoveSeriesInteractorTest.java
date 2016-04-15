package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.RemoveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.RemoveSeriesInteractorImpl;
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
 * @author longi
 * @since 2016.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class RemoveSeriesInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private RemoveSeriesInteractor.Callback mMockedCallback;

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
        when(mRepository.removeSeries(1L, 0)).thenReturn(true);

        RemoveSeriesInteractorImpl interactor = new RemoveSeriesInteractorImpl(
                mApp, 1L, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).removeSeries(1L, 0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onRemoveSeriesFinish();
    }

    @Test
    public void testFail() {
        when(mRepository.removeSeries(1L, 0)).thenReturn(false);

        RemoveSeriesInteractorImpl interactor = new RemoveSeriesInteractorImpl(
                mApp, 1L, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).removeSeries(1L, 0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onRemoveSeriesFail();
    }
}
