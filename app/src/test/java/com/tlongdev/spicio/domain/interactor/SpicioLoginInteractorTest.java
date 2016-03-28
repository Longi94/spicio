package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.impl.SpicioLoginInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
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
 * @since 2016.03.28.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpicioLoginInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private SpicioLoginInteractor.Callback mMockedCallback;

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
        User user = mock(User.class);

        when(mRepository.login(user)).thenReturn(1L);

        SpicioLoginInteractorImpl interactor = new SpicioLoginInteractorImpl(
                mApp, user, mMockedCallback
        );
        interactor.run();

        verify(mRepository).login(user);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onLoginSuccess(1L);
    }

    @Test
    public void testFail() {
        User user = mock(User.class);

        when(mRepository.login(user)).thenReturn(-1L);

        SpicioLoginInteractorImpl interactor = new SpicioLoginInteractorImpl(
                mApp, user, mMockedCallback
        );
        interactor.run();

        verify(mRepository).login(user);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onLoginFailed();
    }
}
