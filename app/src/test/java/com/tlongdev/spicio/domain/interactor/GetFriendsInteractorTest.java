package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.GetFriendsInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFriendsInteractorImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author longi
 * @since 2016.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetFriendsInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private GetFriendsInteractor.Callback mMockedCallback;

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
        List<User> friends = new ArrayList<>();

        when(mRepository.getFriends(1L, 2L)).thenReturn(friends);

        GetFriendsInteractorImpl interactor = new GetFriendsInteractorImpl(
                mApp, 1L, 2L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getFriends(1L, 2L);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetFriendsFinish(friends);
    }

    @Test
    public void testFail() {
        when(mRepository.getFriends(1L, 2L)).thenReturn(null);

        GetFriendsInteractorImpl interactor = new GetFriendsInteractorImpl(
                mApp, 1L, 2L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getFriends(1L, 2L);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetFriendsFail();
    }
}
