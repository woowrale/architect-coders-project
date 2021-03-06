package com.woowrale.openlibrary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.woowrale.openlibrary.di.factory.ViewModelFactory
import com.woowrale.openlibrary.ui.details.DetailsViewModel
import com.woowrale.openlibrary.ui.global.local.GlobalLocalViewModel
import com.woowrale.openlibrary.ui.global.remote.GlobalRemoteViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GlobalLocalViewModel::class)
    abstract fun bindGlobalLocalViewModel(globalLocalViewModel: GlobalLocalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GlobalRemoteViewModel::class)
    abstract fun bindGlobalRemoteViewModel(globalRemoteViewModel: GlobalRemoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)