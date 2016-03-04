package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
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
 * @since 2016. 03. 04.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraktSeriesDetailsInteractorTest {
    @Mock
    private TraktRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TraktSeriesDetailsInteractor.Callback mMockedCallback;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();
    }

    @Test
    public void testSearchResult() {

        Series series = mock(Series.class);
        when(mRepository.getSeriesDetails(0)).thenReturn(series);

        TraktSeriesDetailsInteractorImpl interactor = new TraktSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, 0, mRepository, mMockedCallback
        );
        interactor.run();
        verify(mRepository).getSeriesDetails(0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onResult(series);
    }

    @Test
    public void testSearchFail() {

        when(mRepository.getSeriesDetails(0)).thenReturn(null);

        TraktSeriesDetailsInteractorImpl interactor = new TraktSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, 0, mRepository, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getSeriesDetails(0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onFail();
    }
}
