# AndroidTools
**A collection of software components for simplifying Android application development.**

## RecyclerView ##
RecyclerView is everywhere! With these components in your project, you will receive many benefits:
* **Time Efficiency**
  - [x] No more ViewHolder classes required
  - [x] Fewer abstract methods, more open methods
  - [x] Convenience methods provided
* **Simpler Adapters**
  - [x] Easy Anonymous Classes
  - [x] Reduced Cognitive Load
  - [x] Separates VH initialization from binding
* **Flexibility**
  - [x] Supports various use cases
  - [x] Add ItemSelection with two-way communication  

### Getting Started ###
Check out the RecyclerView wiki for detailed information.

## Context ##
The Context class is the gateway to system services and resources. Sometimes, certain functions involving Context will appear repeatedly throughout projects. This package includes some of these commonly used functions.
### NetworkObserver ###
Internet access management is a very common task in app development. The recommended Android API for Networks has changed over time, and not in a small way. The classes that were once given for this purpose have been entirely deprecated.

The NetworkObserver makes these changes less important to you, especially if you just want to know, yes or no, is there  internet access. The steps to implementing this component are:
1. Construct your NetworkObserver in a ViewModel
2. In every Activity that hosts a fragment that uses NetworkObserver:
   * Register the NetworkObserver in `onStart` by calling `registerWith(this)`
   * Unregister the NetworkObserver in `onStop` with `unregisterWith(this)`
3. Call `getLiveData()` and observe it in your fragments/lifecycleOwners
   * Alternatively, use `getCurrentValue()`
#### Network Constraints ####
If you have tasks that are better suited for specific types of networks, such as WiFi vs Mobile data, then you will need to override `buildNetworkRequest()` and use `NetworkRequest.Builder()` to specify the network constraints that will apply. Check the documentation on NetworkRequest for details on network constraints.
