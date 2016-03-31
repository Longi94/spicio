package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.storage.impl.DeleteAllDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.DeleteAllDataInteractor;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeThreadingModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.SeriesDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteAllDataInteractorTest {

    @Mock
    private SeriesDao mSeriesDao;

    @Mock
    private DeleteAllDataInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Before
    public void setUp() {
        FakeDaoModule storageModule = new FakeDaoModule();
        storageModule.setSeriesDao(mSeriesDao);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(storageModule)
                .networkRepositoryModule(mock(NetworkRepositoryModule.class))
                .threadingModule(new FakeThreadingModule())
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testDelete() throws Exception {

        DeleteAllDataInteractorImpl interactor = new DeleteAllDataInteractorImpl(
                mApp, mMockedCallback
        );
        interactor.run();;

        verify(mSeriesDao).deleteAllData();
        verify(mMockedCallback).onFinish();
    }
}
