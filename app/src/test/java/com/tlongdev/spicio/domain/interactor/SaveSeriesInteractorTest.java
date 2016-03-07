package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

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
 * @since 2016. 03. 05.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveSeriesInteractorTest {

    @Mock
    private SeriesDao mSeriesDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private SaveSeriesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Mock
    private TraktRepository mTraktRepository;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeDaoModule daoModule = new FakeDaoModule();
        daoModule.setSeriesDao(mSeriesDao);

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setTraktRepository(mTraktRepository);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(daoModule)
                .networkRepositoryModule(networkRepositoryModule)
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testInsert(){

        Series series = mock(Series.class);

        when(mTraktRepository.getImages(series.getTraktId(), true)).thenReturn(new Images());

        SaveSeriesInteractorImpl interactor = new SaveSeriesInteractorImpl(
                mExecutor, mMainThread, mApp, series, mMockedCallback
        );
        interactor.run();

        verify(mTraktRepository).getImages(series.getTraktId(), true);
        verify(mSeriesDao).insertSeries(series);
        verifyNoMoreInteractions(mSeriesDao);
        verify(mMockedCallback).onFinish();
    }
}
