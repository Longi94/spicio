package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.GetFullUserDataInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFullUserDataInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.SeriesActivities;
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

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetFullUserDataInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private GetFullUserDataInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Mock
    private User mUser;

    @Mock
    private List<Series> mSeries;

    @Mock
    private Map<Integer, SeriesActivities> mActivitiesMap;

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

        when(mUserFull.getSeries()).thenReturn(mSeries);
        when(mUserFull.getUser()).thenReturn(mUser);
        when(mUserFull.getActivities()).thenReturn(mActivitiesMap);
    }

    @Test
    public void testSuccess() {
        when(mRepository.getUser(1L, true)).thenReturn(mUserFull);

        GetFullUserDataInteractorImpl interactor = new GetFullUserDataInteractorImpl(
                mApp, 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getUser(1L, true);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetFullUserDataFinished(mUser, mSeries, mActivitiesMap);
    }

    @Test
    public void testFail() {
        when(mRepository.getUser(1L, true)).thenReturn(null);

        GetFullUserDataInteractorImpl interactor = new GetFullUserDataInteractorImpl(
                mApp, 1L, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getUser(1L, true);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetFullUserDataFail();
    }
}
