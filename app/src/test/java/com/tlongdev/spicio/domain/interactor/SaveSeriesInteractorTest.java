package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
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

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();
    }

    @Test
    public void testInsert(){

        Series series = mock(Series.class);

        SaveSeriesInteractorImpl interactor = new SaveSeriesInteractorImpl(
                mExecutor, mMainThread, series, mSeriesDao, mMockedCallback
        );
        interactor.run();

        verify(mSeriesDao).insertSeries(series);
        verifyNoMoreInteractions(mSeriesDao);
        verify(mMockedCallback).onFinish();
    }
}
