package com.rushiti.endrit.moviesdiary.di;

import com.rushiti.endrit.moviesdiary.ui.dialog.NewFilmDialog;
import com.rushiti.endrit.moviesdiary.ui.list.ToWatchFragment;
import com.rushiti.endrit.moviesdiary.ui.list.WatchedFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DbModule.class})
public interface AppComponent {
    void inject(ToWatchFragment target);
    void inject(WatchedFragment target);
    void inject(NewFilmDialog target);
}
