package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.storage.SaveFriendsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveFriendsInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.module.FakeThreadingModule;
import com.tlongdev.spicio.storage.dao.UserDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveFriendsInteractorTest {

    @Mock
    private UserDao mUserDao;

    @Mock
    private SaveFriendsInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Before
    public void setUp() {
        FakeDaoModule daoModule = new FakeDaoModule();
        daoModule.setUserDao(mUserDao);

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
    public void testQuery(){
        List<User> friends = new LinkedList<>();

        SaveFriendsInteractorImpl interactor = new SaveFriendsInteractorImpl(
                mApp, friends, mMockedCallback
        );
        interactor.run();

        verify(mUserDao).addFriends(friends);
        verifyNoMoreInteractions(mUserDao);
        verify(mMockedCallback).onSaveFriendsFinish();
    }
}
