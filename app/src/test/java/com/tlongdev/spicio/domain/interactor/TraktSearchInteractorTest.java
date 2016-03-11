package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraktSearchInteractorTest {

    @Mock
    private TraktRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TraktSearchInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setTraktRepository(mRepository);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .networkRepositoryModule(networkRepositoryModule)
                .daoModule(mock(DaoModule.class))
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testSearchResult() {

        String searchQuery = "thrones";

        List<Series> seriesList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            seriesList.add(mock(Series.class));
        }

        when(mRepository.searchSeries(searchQuery)).thenReturn(seriesList);

        TraktSearchInteractorImpl interactor = new TraktSearchInteractorImpl(
                mExecutor, mMainThread, mApp, searchQuery, mMockedCallback
        );
        interactor.run();
        verify(mRepository).searchSeries(searchQuery);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onTraktSearchFinish(seriesList);
    }

    @Test
    public void testSearchFail() {

        String searchQuery = "thrones";

        when(mRepository.searchSeries(searchQuery)).thenReturn(null);

        TraktSearchInteractorImpl interactor = new TraktSearchInteractorImpl(
                mExecutor, mMainThread, mApp, searchQuery, mMockedCallback
        );
        interactor.run();

        verify(mRepository).searchSeries(searchQuery);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onTraktSearchFailed();
    }
}
