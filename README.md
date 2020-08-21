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
To get started using the ResponseAdapter 
1. Add an implementation of this class to your Fragment
    * `val adapter = object: ResponseAdapter(R.layout.vh_example) {}`
2. Override `getItemCount(): Int`
    * Can be a static number or the size of a list/array
    * If using a variable number or dataset, call notifyDataSetChanged() to update the views
3. Override `bindView(v: View, index: Int)`
    * Use the view parameter to access the layout with `v.findViewById<>()` or Kotlinx
      * Use `v.findViewById<>()` if view resources are in another module
    * Use the index parameter to retrieve the data
      * Use convenience methods: `getFromArray(index, array)` or `getFromList(index, list)`
      * These methods safely access the array or list, whether it is null or the index is out of bounds
 4. Override `respond(index: Int, action: String? = null)`
    * When ResponseVH `action(String?)` is called, ResponseAdapter `respond(Int, String?)` handles it
      * Use `val data = getFromArray(index, arrayList)` to obtain the data that was clicked
      
 ### Default Click Response Behaviour ###
  * By default a ClickListener is set on ResponseVH root view, which calls `action(null)`
    * To prevent Click listener setup, set the second constructor parameter of ResponseAdapter to false
  * To set your own listeners, override `initViewListeners(v: View, vh: ResponseVH)`
    * Inside each listener call `vh.action(String?)`
    * Provide a different string for each type of action you want to perform

### Optional Features ###

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
