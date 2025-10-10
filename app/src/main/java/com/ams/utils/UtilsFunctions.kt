package com.ams.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.ams.R
import com.ams.utils.MyAnimationUtils.fadeIn
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import render.animations.Attention
import render.animations.Render
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.apply
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.indices
import kotlin.collections.isEmpty
import kotlin.ranges.until
import kotlin.text.isEmpty
import kotlin.text.split
import kotlin.text.toInt
import kotlin.to

object UtilsFunctions {
    @SuppressLint("StaticFieldLeak")
    private lateinit var render: Render
    var TAG = "TAG12345"
    private val animatedPositions = mutableSetOf<Int>()


    val navOptionsPopUpIn = NavOptions.Builder()
        .setPopUpTo(R.id.parent_nav_graph, inclusive = true)
        .build()



    fun morphToProgress(textView: TextView, progressBar: LinearProgressIndicator) {
        textView.text = "Loading..."
        progressBar.visibility = View.VISIBLE
        animateProgress(progressBar)
    }

    fun animateProgress(progressBar : LinearProgressIndicator) {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 1500
        animator.addUpdateListener {
            val progress = it.animatedValue as Int
            progressBar.progress = progress
        }
        animator.start()
    }


    fun morphToTick(imageView: ImageView, cardView: CardView) {
        imageView.fadeIn()
        cardView.isEnabled = false
    }


    fun animateItem(view: View, position: Int, delayPerItem: Long = 150L, duration: Long = 500L) {
        if (!animatedPositions.contains(position)) {
            view.alpha = 0f
            view.visibility = View.VISIBLE
            view.animate()
                .alpha(1f)
                .setStartDelay(position * delayPerItem)
                .setDuration(duration)
                .start()
            animatedPositions.add(position)
        }
    }



    fun showDatePickerDialog(context: Context, textView: TextView) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context,
            { _, yearF, monthF, dayF ->
                val correctedMonth = monthF + 1
                val formattedDay = if (dayF < 10) "0$dayF" else "$dayF"
                val formattedMonth = if (correctedMonth < 10) "0$correctedMonth" else "$correctedMonth"
                val formattedDate = "$yearF-$formattedMonth-$formattedDay"
                textView.text = formattedDate
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }
    fun getCurrentFormattedDate(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
    @SuppressLint("ConstantLocale")
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    fun animateViews(
        vararg views: View, startDelayMs: Long = 0, staggerDelayMs: Long = 100, durationMs: Long = 300,
        initialAlpha: Float = 0f, targetAlpha: Float = 1f, onAnimationEnd: (() -> Unit)? = null) {
        if (views.isEmpty()) {
            onAnimationEnd?.invoke()
            return
        }
        var completedAnimations = 0
        val totalAnimations = views.size
        views.forEachIndexed { index, view ->
            view.alpha = initialAlpha
            view.visibility = View.VISIBLE
            view.animate()
                .alpha(targetAlpha)
                .setStartDelay(startDelayMs + index * staggerDelayMs)
                .setDuration(durationMs)
                .withEndAction {
                    completedAnimations++
                    if (completedAnimations == totalAnimations) {
                        onAnimationEnd?.invoke()
                    }
                }
                .start()
        }
    }


    fun View.setOnClickListeners(delay: Long = 300, action: () -> Unit) {
        this.setOnClickListener {
            this.animate()
                .scaleX(0.85f)
                .scaleY(0.85f)
                .setDuration(200)
                .withEndAction {
                    this.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(50)
                        .start()
                }
                .start()
            this.postDelayed({
                action.invoke()
            }, delay)
        }
    }

    fun View.setOnClickListenerZoom(delay: Long = 300, action: () -> Unit) {
        this.setOnClickListener {
            this.animate()
                .scaleX(1.15f)
                .scaleY(1.15f)
                .setDuration(200)
                .withEndAction {
                    this.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(50)
                        .start()
                }
                .start()
            this.postDelayed({
                action.invoke()
            }, delay)
        }
    }

    fun View.expand(duration: Long = 300) {
        measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = measuredHeight

        layoutParams.height = 0
        visibility = View.VISIBLE

        val valueAnimator = ValueAnimator.ofInt(0, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        valueAnimator.duration = duration
        valueAnimator.start()
    }

    // Extension function to animate collapse
    fun View.collapse(duration: Long = 300) {
        val initialHeight = measuredHeight

        val valueAnimator = ValueAnimator.ofInt(initialHeight, 0)
        valueAnimator.addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        valueAnimator.duration = duration
        valueAnimator.doOnEnd {
            visibility = View.GONE
        }
        valueAnimator.start()
    }



    fun showToast(context: Context, message: String) {
        val inflater = LayoutInflater.from(context)
        val customView: View = inflater.inflate(R.layout.custom_toast, null)
        val toastMessage: TextView = customView.findViewById(R.id.tvMessage)
        val toastIcon: ImageView = customView.findViewById(R.id.ivToast)
        toastMessage.text = message
        toastIcon.setImageResource(R.drawable.app_logo)
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        customView.startAnimation(animation)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = customView
        toast.show()
    }

    fun showSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    fun isValidPhoneNumber(countryCode: String, phoneNumber: String): Boolean {
        val (minLength, maxLength) = when (countryCode) {

            // Exactly 10 digits (India, USA, Canada, Pakistan, UK)
            "91", "1", "92", "44" -> 10 to 10

            // UAE (8–9 digits)
            "971" -> 8 to 9

            // Nepal
            "977" -> 10 to 10

            // Australia
            "61" -> 9 to 9

            // Singapore
            "65" -> 8 to 8

            // Germany (7–13 digits)
            "49" -> 7 to 13

            // France
            "33" -> 9 to 9

            // Saudi Arabia
            "966" -> 9 to 9

            // Brazil
            "55" -> 10 to 11

            // Default fallback
            else -> 4 to 15
        }

        return phoneNumber.length in minLength..maxLength
    }



    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun showLog(message:String,tag: String = "MyLogTag") {
        Log.d(tag, message)
    }


    fun setTextAndFocusChangeListener(context: Context, vararg linearLayouts: LinearLayout) {
        val errorColor = R.color.red
        val focusColor = R.color.app_dark
        val unfocusColor = R.color.app_gray

        linearLayouts.forEach { linearLayout ->
            val editText = linearLayout.getChildAt(0) as EditText
            editText.doOnTextChanged { text, start, before, count ->
                if(editText.text.toString().isEmpty()) {
                    if(editText.isFocused){
//                        linearLayout.setBackgroundResource(R.drawable.et_bg_error)
                        linearLayout.foregroundTintList = ContextCompat.getColorStateList(context, errorColor)
                    }
                    else{
//                        linearLayout.setBackgroundResource(R.drawable.et_bg_unfocused)
                        linearLayout.foregroundTintList = ContextCompat.getColorStateList(context, unfocusColor)
                    }
                } else {
                    if(editText.isFocused){
//                        linearLayout.setBackgroundResource(R.drawable.et_bg_focused)
                        linearLayout.foregroundTintList = ContextCompat.getColorStateList(context, focusColor)
                    }
                    else{
//                        linearLayout.setBackgroundResource(R.drawable.et_bg_unfocused)
                        linearLayout.foregroundTintList = ContextCompat.getColorStateList(context, unfocusColor)
                    }
                }
            }

            editText.setOnFocusChangeListener { _, focused ->
                if(focused){
//                    linearLayout.setBackgroundResource(R.drawable.et_bg_focused)
                    linearLayout.foregroundTintList = ContextCompat.getColorStateList(context, focusColor)
                }
                else{
//                    linearLayout.setBackgroundResource(R.drawable.et_bg_unfocused)
                    linearLayout.foregroundTintList = ContextCompat.getColorStateList(context, unfocusColor)
                }

            }

        }
    }

    fun setTextAndFocusChangeListener(vararg textViews: TextView) {

        textViews.forEach { textView ->
            val textInputLayout = textView.parent.parent as TextInputLayout

            textView.doOnTextChanged { text, start, before, count ->
                if(textView.text.toString().isEmpty()) {
                    if(textView.isFocused){

                    }
                    else{
                        textInputLayout.error = ""
                    }
                } else {
                    if(textView.isFocused){
                        textInputLayout.error = ""
//                        textView.setBackgroundResource(R.drawable.et_bg_focused)
                    }
                    else{
                        textInputLayout.error = ""
//                        textView.setBackgroundResource(R.drawable.et_bg_unfocused)
                    }
                }
            }
            textInputLayout.setErrorIconOnClickListener {
                textInputLayout.error = ""
            }
            /*textView.setOnFocusChangeListener { _, focused ->
                if(focused){
//                    textView.setBackgroundResource(R.drawable.et_bg_focused)
                }
                else{
//                    textView.setBackgroundResource(R.drawable.et_bg_unfocused)
                }

            }*/

        }
    }

    fun focusOnView(context: Context, editText: EditText, nestedScrollView: NestedScrollView) {
        nestedScrollView.post {

            nestedScrollView.smoothScrollTo(0, editText.top)

            editText.apply {
                requestFocus()
                isSelected = true
                showAnimation(context, this)
            }
        }
    }
    fun focusOnView(context: Context, editText: EditText, nestedScrollView: NestedScrollView, linearLayout: LinearLayout) {
        nestedScrollView.post {

            nestedScrollView.smoothScrollTo(0, linearLayout.top)

            editText.apply {
                requestFocus()
                isSelected = true
                showAnimation(context, this)
            }
        }
    }



    fun showAnimation(context: Context, view: View) {
        render = Render(context)

        render.setAnimation(Attention().Bounce(view))
        render.start()
    }

    fun showHidePassword(vararg pairs: Pair<ImageView, EditText>)
    {
        pairs.forEach { pair ->
            pair.first.apply {
                setOnClickListeners {
                    if(this.isSelected)
                    {
                        this.isSelected=false
                        pair.second.transformationMethod= PasswordTransformationMethod.getInstance()
                    }
                    else{
                        this.isSelected=true
                        pair.second.transformationMethod=null
                    }
                }
            }
        }
    }




    fun displaySelectedItemsAsChips(
        context: Context,
        ids: ArrayList<String>,
        names: ArrayList<String>,
        chipGroup: ChipGroup,
        icCloseVisible: Boolean = true,
        chipStrokeColorIs: Int = R.color.btn_dark_blue,
        chipBackgroundColorIs: Int = R.color.btn_dark_blue,
        chipTextColorIs: Int = R.color.white,
        closeIconColorIs: Int = R.color.white,
    ) {
        chipGroup.removeAllViews()

        for (i in ids.indices) {
            val chip = Chip(context).apply {
                val name = names[i]
                text = name
                isCloseIconVisible = icCloseVisible
                chipCornerRadius = 50f
                elevation = 4f
                chipStrokeWidth = 2f
                chipStrokeColor = ContextCompat.getColorStateList(context, chipStrokeColorIs)
                chipBackgroundColor = ContextCompat.getColorStateList(context, chipBackgroundColorIs)
                closeIconTint = ContextCompat.getColorStateList(context, closeIconColorIs)
                setTextColor(ContextCompat.getColor(context, chipTextColorIs))

                setOnCloseIconClickListener {
                    // Remove from pending lists
                    ids.removeAt(i)
                    names.removeAt(i)
                    // Update display
                    displaySelectedItemsAsChips(
                        context = context,
                        ids,
                        names,
                        chipGroup,
                    )
                }
            }
            chipGroup.addView(chip)
        }
    }


    fun RecyclerView.smoothScrollToPositionCentered(position: Int) {
        val layoutManager = layoutManager as LinearLayoutManager
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
                return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
            }
        }
        smoothScroller.targetPosition = position
        layoutManager.startSmoothScroll(smoothScroller)
    }


    fun hideViewsWithTag(parent: View, targetTag: String) {
        if (parent.tag == targetTag) {
            parent.animate()
                .alpha(0f)
                .translationY(50f) // slide down
                .setDuration(300)
                .withEndAction {
                    parent.visibility = View.GONE
                }
                .start()
        }

        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                hideViewsWithTag(parent.getChildAt(i), targetTag)
            }
        }
    }

    fun showViewsWithTag(parent: View, targetTag: String) {
        if (parent.tag == targetTag) {
            parent.alpha = 0f
            parent.translationY = -50f // start slightly above
            parent.visibility = View.VISIBLE
            parent.animate()
                .alpha(1f)
                .translationY(0f) // slide to original position
                .setDuration(300)
                .start()
        }

        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                showViewsWithTag(parent.getChildAt(i), targetTag)
            }
        }
    }




    fun copyTextToClipboard(activity: Activity, textToCopy: String) {
        val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", textToCopy)
        clipboard.setPrimaryClip(clip)

    }




    fun convertIsoToDdMmmYyyyHhMmAFormat(isoDate: String): String {
        return try {
            // Parse ISO string (UTC)
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")

            val date = parser.parse(isoDate)

            // Format into desired pattern
            val formatter = SimpleDateFormat("dd MMM, yyyy, hh:mm a", Locale.getDefault())
            formatter.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    fun convertIsoToDdMmmYyyyFormat(isoDate: String): String {
        return try {
            // Parse ISO string (UTC)
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")

            val date = parser.parse(isoDate)

            // Format into desired pattern
            val formatter = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
            formatter.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun String.toTimeAgo(): String {
        return try {
            // Parse ISO timestamp
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val past = sdf.parse(this) ?: return ""
            val now = Date()

            val diff = now.time - past.time

            return when {
                diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
                diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)}min ago"
                diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)}hr ago"
                diff < TimeUnit.DAYS.toMillis(2) -> "Yesterday"
                else -> {
                    // Show "31 Aug", "30 Aug"
                    val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
                    dateFormat.format(past)
                }
            }
        } catch (e: Exception) {
            ""
        }
    }
    fun TextView.setDrawable(direction: String, drawableRes: Int) {
        // Current drawables (start, top, end, bottom)
        val drawables = compoundDrawables

        var start = drawables[0]
        var top = drawables[1]
        var end = drawables[2]
        var bottom = drawables[3]

        // Replace only the requested direction
        when (direction) {
            "start" -> start = if (drawableRes != 0) ContextCompat.getDrawable(context, drawableRes) else null
            "top" -> top = if (drawableRes != 0) ContextCompat.getDrawable(context, drawableRes) else null
            "end" -> end = if (drawableRes != 0) ContextCompat.getDrawable(context, drawableRes) else null
            "bottom" -> bottom = if (drawableRes != 0) ContextCompat.getDrawable(context, drawableRes) else null
        }

        // Set updated drawables back
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }

    fun TextView.setDrawableStart(drawable: Int) = setDrawable("start", drawable)
    fun TextView.setDrawableTop(drawable: Int) = setDrawable("top", drawable)
    fun TextView.setDrawableEnd(drawable: Int) = setDrawable("end", drawable)
    fun TextView.setDrawableBottom(drawable: Int) = setDrawable("bottom", drawable)


    fun isExpired(expiry: String): Boolean {
        val parts = expiry.split("/")
        if (parts.size != 2) return true
        val month = parts[0].toInt()
        val year = "20${parts[1]}".toInt()

        val cal = Calendar.getInstance()
        val currentMonth = cal.get(Calendar.MONTH) + 1
        val currentYear = cal.get(Calendar.YEAR)

        return year < currentYear || (year == currentYear && month < currentMonth)
    }
}