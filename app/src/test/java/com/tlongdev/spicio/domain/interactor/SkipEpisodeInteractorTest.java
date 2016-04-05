package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.storage.SkipEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.SkipEpisodeInteractorImpl;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.module.FakeThreadingModule;
import com.tlongdev.spicio.storage.dao.EpisodeDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author longi
 * @since 2016.04.02.
 */
@RunWith(MockitoJUnitRunner.class)
public class SkipEpisodeInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private SkipEpisodeInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Before
    public void setUp() {
        FakeDaoModule daoModule = new FakeDaoModule();
        daoModule.setEpisodeDao(mEpisodeDao);

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(daoModule)
                .networkRepositoryModule(networkRepositoryModule)
                .threadingModule(new FakeThreadingModule())
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testCheck(){

        when(mEpisodeDao.setSkipped(0, 0, true)).thenReturn(true);

        SkipEpisodeInteractorImpl interactor = new SkipEpisodeInteractorImpl(
                mApp, 0, 0, true, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).setSkipped(0, 0, true);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onEpisodeSkipFinish();
    }

    @Test
    public void testFail(){

        when(mEpisodeDao.setSkipped(0, 0, true)).thenReturn(false);

        SkipEpisodeInteractorImpl interactor = new SkipEpisodeInteractorImpl(
                mApp, 0, 0, true, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).setSkipped(0, 0, true);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onEpisodeSkipFail();
    }
}
