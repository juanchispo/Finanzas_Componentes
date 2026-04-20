package com.juanchispo.finanzas_componentes.viewmodels

import androidx.lifecycle.ViewModel
import com.juanchispo.finanzas_componentes.language.AppLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageViewModel : ViewModel() {
    private val _language = MutableStateFlow(AppLanguage.ES)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
    }

    fun toggle() {
        _language.value = if (_language.value == AppLanguage.ES) AppLanguage.EN else AppLanguage.ES
    }
}
