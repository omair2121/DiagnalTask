package com.darkcoder.diagonal.di

import android.app.Application
import android.content.Context
import com.codinginflow.imagesearchapp.ui.gallery.MovieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object ActivityModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideAdapter(context: Context) = MovieAdapter(context)

}