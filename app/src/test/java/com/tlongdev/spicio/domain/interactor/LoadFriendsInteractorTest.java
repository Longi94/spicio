package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.storage.LoadFriendsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadFriendsInteractorImpl;
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
 * @author longi
 * @since 2016.04.19.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadFriendsInteractorTest {

    @Mock
    private UserDao mUserDao;

    @Mock
    private LoadFriendsInteractor.Callback mMockedCallback;

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
        when(mUserDao.getFriends()).thenReturn(friends);

        LoadFriendsInteractorImpl interactor = new LoadFriendsInteractorImpl(
                mApp, mMockedCallback
        );
        interactor.run();

        verify(mUserDao).getFriends();
        verifyNoMoreInteractions(mUserDao);
        verify(mMockedCallback).onLoadFriendsFinish(friends);
    }
}
