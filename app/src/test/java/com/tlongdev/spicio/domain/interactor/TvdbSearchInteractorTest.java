package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
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
 * @since 2016. 02. 28.
 */
@RunWith(MockitoJUnitRunner.class)
public class TvdbSearchInteractorTest {

    @Mock
    private TvdbRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TvdbSearchInteractor.Callback mMockedCallback;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();
    }

    @Test
    public void testSearchResult() {

        String searchQuery = "thrones";

        List<Series> seriesList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            seriesList.add(mock(Series.class));
        }

        when(mRepository.searchSeries(searchQuery)).thenReturn(seriesList);

        TvdbSearchInteractorInstance interactor = new TvdbSearchInteractorInstance(
                mExecutor, mMainThread, searchQuery, mMockedCallback, mRepository
        );
        interactor.run();
        verify(mRepository).searchSeries(searchQuery);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSearchResult(seriesList);
    }

    @Test
    public void testSearchFail() {
        String searchQuery = "thrones";

        when(mRepository.searchSeries(searchQuery)).thenReturn(null);

        TvdbSearchInteractorInstance interactor = new TvdbSearchInteractorInstance(
                mExecutor, mMainThread, searchQuery, mMockedCallback, mRepository
        );
        interactor.run();

        verify(mRepository).searchSeries(searchQuery);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSearchFailed();
    }
}
