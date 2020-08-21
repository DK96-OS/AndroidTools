# AndroidTools
**A collection of software components for simplifying Android application development.**

## RecyclerView ##
Does creating a new RecyclerView excite you? Well, with these components you are going to be excited for sure! The benefits of having these classes in your project include:
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
To get started using the ResponseAdapter, create the simplest use case: a non-responsive Adapter 
1. Add an implementation of this class to your Fragment
    * `val adapter = object: ResponseAdapter(R.layout.vh_example) {}`
2. Override getItemCount, provide the size of your dataset
    * `override fun getItemCount() = array?.size ?: 0`
3. Override bindView
    * `override fun bindView(v: View, index: Int) {}`
    * Use the index parameter to retrieve the data
      * Use convenience methods: `getFromArray(index, array)` or `getFromList(index, list)`
    * Use the view parameter to access the layout with `v.findViewById<>()` or synthetic Kotlinx
      * Use `v.findViewById<>()` if resource ids are in another module

### Optional Features ###
#### ViewHolder ClickResponse ####
In the ResponseAdapter subclass, you must override 2 methods. The initViewListeners(v, vh) method sets up response triggers and respond(index, action) handles them.

* `initViewListeners(v: View, vh: ResponseVH) {}`
    * Set Listeners on the views, using v and findViewById or synthetic Kotlinx
    * Inside each listener call `vh.action(string)`
      * Use the string to differentiate between Listeners and desired responses to these events
      * If your use case is simple, you can omit the action string

* `respond(index: Int, action: String? = null) {}`
    * Generally, this function starts with `val data = getFromArray(index, array)` and then `when (action) {}`
      * If your use case is simple, you can ignore action and focus on data or index

#### ListItemSelection ####
*This version was built for data that is retrieved on Fragment Start, and updated on Fragment Stop.*
*There is an alternate version in development for working with LiveData having sources that update during Fragment interaction* 

Add an instance of ListItemSelectionManager to your Fragment class. This component automatically notifies the ResponseAdapter when an item selection state changes.
1. Update the ResponseAdapter declaration
    * The ResponseAdapter must implement the ListItemSelectionManager.Listener interface.
      * This override method does not need to be written
2. Update ResponseAdapter viewListener and response methods to include item selection events
    * In the response handler, call the ListItemSelectionManager's selectIndex method
3. Interact with ListItemSelectionManager 
    * Call `selectIndex(index)` in `response(index, action)` if the desired action is to select or deselect views
    * If using optionsMenu or ActionMode to interact with selected items, get their indices with `getSelectedIndices()`
    * When ActionMode finishes or menu callback returns, call `clear()` to clear the data and update views
    * In `bindView()`, use `checkSelectionFor(index)` to determine if it is selected, and update view appearance
4. Update ListItemSelectionManager with lifecycle and data events
    * In onStart, pass the indices that are initially selected
    * In onStop
      * Call `getSelectedIndices()` and update your persisted data model with the selected data
      * Calling `clear()` will notify the Adapter unnnecessarily, just let it be destroyed with the Fragment
    * If your dataset indices change, you should call `clear()` before updating the dataset
