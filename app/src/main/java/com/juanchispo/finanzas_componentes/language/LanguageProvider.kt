package com.juanchispo.finanzas_componentes.language

import androidx.compose.runtime.compositionLocalOf

/** CompositionLocal que provee los strings del idioma actual a toda la UI. */
val LocalStrings = compositionLocalOf<AppStrings> {
    error("No AppStrings provided")
}
