package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SearchUsersInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.SearchUsersInteractor;
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
 * @author Long
 * @since 2016. 03. 30.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchUsersInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private SearchUsersInteractor.Callback mMockedCallback;

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
    public void testSearch() {
        List<User> users = new ArrayList<>();

        when(mRepository.searchUser("test", 1L)).thenReturn(users);

        SearchUsersInteractorImpl interactor = new SearchUsersInteractorImpl(
                mApp, "test", 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).searchUser("test", 1L);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSearchFinished(users);
    }
}
