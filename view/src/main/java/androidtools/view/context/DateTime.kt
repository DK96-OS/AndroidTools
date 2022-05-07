package androidtools.view.context

import android.content.Context
import android.text.format.DateUtils

/** Format timestamp values as date and time strings */

/** DateUtils Format: ShowTime */
fun formatTime(ctx: Context?, time: Long)
    : String = DateUtils.formatDateTime(ctx, time, DateUtils.FORMAT_SHOW_TIME)

/** DateUtils Format: ShowDate, AbbrevMonth, ShowTime */
fun formatDate(ctx: Context?, time: Long)
    : String = DateUtils.formatDateTime(ctx, time, 
    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_SHOW_TIME)

/** DateUtils Format: ShowDate, AbbrevMonth */
fun formatDateNoTime(ctx: Context?, time: Long)
    : String = DateUtils.formatDateTime(ctx, time, 
    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH)

