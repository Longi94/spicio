package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.storage.SaveActivitiesInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveActivitiesInteractorImpl;
import com.tlongdev.spicio.domain.model.SeriesActivities;
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

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveActivitiesInteractorTest {

    @Mock
    private UserDao mUserDao;

    @Mock
    private SaveActivitiesInteractor.Callback mMockedCallback;

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
    public void testInsert(){

        Map<Integer, SeriesActivities> activitiesMap = new HashMap<>();

        SaveActivitiesInteractorImpl interactor = new SaveActivitiesInteractorImpl(
                mApp, activitiesMap, mMockedCallback
        );
        interactor.run();

        verify(mUserDao).insertUserActivities(activitiesMap);
        verifyNoMoreInteractions(mUserDao);
        verify(mMockedCallback).onSaveActivitiesFinish();
    }
}
