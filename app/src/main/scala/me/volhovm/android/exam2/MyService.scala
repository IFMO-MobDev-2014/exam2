package me.volhovm.android.exam2

import android.app.IntentService
import android.content.Intent
import android.os.{Bundle, Handler, ResultReceiver}
import android.util.Log

object MyService {
  val SERVICE_NAME = "WeatherLoadService"
  val STATUS_RUNNING = 0
  val STATUS_FINISHED = 1
  val STATUS_ERROR = 2

  val SERVICE_MODE = "service_mode"
  val FIRST_MODE = 0
  val SECOND_MODE = 1
  val THIRD_MODE = 2

  val RECEIVER = "receiver"
}

class MyService extends IntentService("MyService") {
  override def onHandleIntent(intent: Intent): Unit = {
    import me.volhovm.android.exam2.MyService._
    val receiver: ResultReceiver = intent.getParcelableExtra(RECEIVER)
    val mode = intent.getIntExtra(SERVICE_MODE, -1)
    Log.d(SERVICE_NAME, "started service with mode #" + mode)
    try {
      mode match {
        case FIRST_MODE => 
          receiver.send(STATUS_ERROR, null)
        case SECOND_MODE =>
          receiver.send(STATUS_FINISHED, null)
        case THIRD_MODE =>
          receiver.send(STATUS_RUNNING, null)

      }
    } catch {
      case a: Throwable =>
        Log.e("MyService", "Exception for mode #" + mode + ": " + a.toString)
        val bundle: Bundle = new Bundle()
        bundle.putString(Intent.EXTRA_TEXT, a.toString)
        if (receiver != null) receiver.send(STATUS_ERROR, bundle)
    }
  }
}

trait Receiver {
  def onReceiveResult(resCode: Int, resData: Bundle): Unit
}

class WeatherLoadReceiver(handler: Handler) extends ResultReceiver(handler) {
  private var mReceiver: Receiver = null
  def setReceiver(r: Receiver) = mReceiver = r
  override def onReceiveResult(resCode: Int, resData: Bundle) = if (mReceiver != null) mReceiver.onReceiveResult(resCode, resData)
}
