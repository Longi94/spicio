package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.GetUserDataInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetUserDataInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserFull;
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
 * @since 2016.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetUserInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private GetUserDataInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Mock
    private User mUser;

    @Mock
    private UserFull mUserFull;

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

        when(mUserFull.getUser()).thenReturn(mUser);
    }

    @Test
    public void testSuccess() {
        when(mRepository.getUser(1L, false)).thenReturn(mUserFull);

        GetUserDataInteractorImpl interactor = new GetUserDataInteractorImpl(
                mApp, 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getUser(1L, false);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetUserDataFinish(mUser);
    }

    @Test
    public void testFail() {
        when(mRepository.getUser(1L, false)).thenReturn(null);

        GetUserDataInteractorImpl interactor = new GetUserDataInteractorImpl(
                mApp, 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getUser(1L, false);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetUserDataFail();
    }
}
