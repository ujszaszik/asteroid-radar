package com.udacity.asteroidradar.ui.main.binding

import android.view.View.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.network.NetworkStatus

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String?) {
    url?.let {
        Glide.with(context).load(PictureValidator.getImageToShow(context, it))
            .error(R.drawable.ic_broken_image).centerCrop().into(this)
    }
}


@BindingAdapter("isPotentiallyHazardousVector")
fun ImageView.isPotentiallyHazardousVector(isPotentiallyHazardous: Boolean) {
    if (isPotentiallyHazardous) setImageResource(R.drawable.ic_status_potentially_hazardous)
    else setImageResource(R.drawable.ic_status_normal)
}

@BindingAdapter("isPotentiallyHazardousHeaderImage")
fun ImageView.isPotentiallyHazardousHeaderImage(isPotentiallyHazardous: Boolean) {
    if (isPotentiallyHazardous) setImageResource(R.drawable.asteroid_hazardous)
    else setImageResource(R.drawable.asteroid_safe)
}

@BindingAdapter("isPotentiallyHazardousContentDescription")
fun ImageView.isPotentiallyHazardousContentDescription(isPotentiallyHazardous: Boolean) {
    contentDescription =
        if (isPotentiallyHazardous) context.getString(R.string.potentially_hazardous_asteroid_image)
        else context.getString(R.string.not_hazardous_asteroid_image)
}

@BindingAdapter("asteroidPictureNetworkStatus")
fun ImageView.asteroidPictureNetworkStatus(status: NetworkStatus?) {
    fun ImageView.setVisibleImage(id: Int) {
        visibility = VISIBLE.also { setImageResource(id) }
    }

    status?.let {
        when (it) {
            is NetworkStatus.Loading -> setVisibleImage(R.drawable.loading_animation)
            is NetworkStatus.Error -> setVisibleImage(R.drawable.ic_connection_error)
            is NetworkStatus.Success -> visibility = GONE
        }
    }
}

@BindingAdapter("asteroidListNetworkStatus")
fun ProgressBar.asteroidListNetworkStatus(status: NetworkStatus?) {
    fun goneIfNotLoading() {
        visibility = GONE
    }

    status?.let {
        visibility = when (it) {
            is NetworkStatus.Loading -> VISIBLE
            else -> INVISIBLE
        }
    } ?: goneIfNotLoading()
}

@BindingAdapter("networkVisibilityStatus")
fun RecyclerView.networkVisibilityStatus(status: NetworkStatus?) {
    fun visibleIfNotLoading() {
        visibility = VISIBLE
    }

    status?.let {
        visibility = when (it) {
            is NetworkStatus.Loading -> GONE
            else -> VISIBLE
        }
    } ?: visibleIfNotLoading()
}

@BindingAdapter("showEmptyListLayout")
fun ConstraintLayout.showEmptyListLayout(value: Boolean?) {
    fun goneIfNull() {
        visibility = GONE
    }

    value?.let {
        visibility = if (it) VISIBLE else GONE
    } ?: goneIfNull()
}