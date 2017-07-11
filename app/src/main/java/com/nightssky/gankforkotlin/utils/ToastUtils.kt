package com.nightssky.gankforkotlin.utils

import android.app.Fragment
import android.content.Context
import android.widget.Toast

/**
 * Created by user on 2017/7/6.
 */




/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.toast(message: Int): Unit = activity.toast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
fun Context.toast(message: Int) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()



/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.toast(message: CharSequence): Unit = activity.toast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()



/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.longToast(message: Int): Unit = activity.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Context.longToast(message: Int) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()


/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.longToast(message: CharSequence): Unit = activity.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Context.longToast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()