package com.example.videoapp.models.components;

import com.example.videoapp.models.data.RestCall;
import com.example.videoapp.models.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

@Singleton
@Component(modules={NetModule.class})
public interface NetComponent {
    RestCall restCall();
}
