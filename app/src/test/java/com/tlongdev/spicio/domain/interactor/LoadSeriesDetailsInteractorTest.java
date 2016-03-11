package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
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
 * @since 2016. 03. 09.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadSeriesDetailsInteractorTest {

    @Mock
    private SeriesDao mSeriesDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private LoadSeriesDetailsInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;


    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeDaoModule storageModule = new FakeDaoModule();
        storageModule.setSeriesDao(mSeriesDao);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(storageModule)
                .networkRepositoryModule(mock(NetworkRepositoryModule.class))
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testSuccess() {

        Series series = new Series();
        when(mSeriesDao.getSeries(0)).thenReturn(series);

        LoadSeriesDetailsInteractorImpl interactor = new LoadSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, mMockedCallback
        );
        interactor.run();

        verify(mSeriesDao).getSeries(0);
        verifyNoMoreInteractions(mSeriesDao);
        verify(mMockedCallback).onLoadSeriesDetailsFinish(series);
    }

    @Test
    public void testFail() {
        when(mSeriesDao.getSeries(0)).thenReturn(null);

        LoadSeriesDetailsInteractorImpl interactor = new LoadSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, mMockedCallback
        );
        interactor.run();

        verify(mSeriesDao).getSeries(0);
        verifyNoMoreInteractions(mSeriesDao);
        verify(mMockedCallback).onLoadSeriesDetailsFail();
    }
}
