import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/** NetworkCallback uses LiveData to dispatch network connectivity status 
  * Developed by DK96-OS */
open class NetworkObserver : ConnectivityManager.NetworkCallback() {

    private val isAvailable = MutableLiveData<Boolean>()

    /** Obtain a LiveData for receiving updates to Connection status */
    fun getLiveData(): LiveData<Boolean> = isAvailable

    /** Obtain the current connection status as a Boolean value */
    fun getCurrentValue(): Boolean? = isAvailable.value
  
    /** Tracks multiple network statuses */
    protected val networksAvailable = arrayListOf<Long>()

    override fun onLost(network: Network) {
        super.onLost(network)
        networksAvailable.remove(network.networkHandle)
        if (networksAvailable.isEmpty()) isAvailable.postValue(false)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        if (networksAvailable.isEmpty()) isAvailable.postValue(true)
        networksAvailable.add(network.networkHandle)
    }

    /** Override to set up your own NetworkRequest */
    protected open fun buildNetworkRequest(): NetworkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

    /** Register this NetworkCallback with the Context's ConnectivityManager */
    fun registerWith(ctx: Context) {
        networksAvailable.clear()
        (ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerNetworkCallback(buildNetworkRequest(), this)
    }

    /** Unregister this NetworkCallback from the Context */
    fun unregisterFrom(ctx: Context) {
        networksAvailable.clear()
        (ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .unregisterNetworkCallback(this)
    }

}
