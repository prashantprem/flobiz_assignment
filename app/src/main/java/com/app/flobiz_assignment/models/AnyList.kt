package com.app.flobiz_assignment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
class AnyList(
    val item : @RawValue Any? = null
) : Parcelable