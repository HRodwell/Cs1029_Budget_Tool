I removed the calculate button as it was redundant after the spreadsheet behavior was added

ComboBox creation and adding listeners uses methods to try and minimise repetetive code

select all on focus gained listener for textFields to make using the tool less horrible especially with the keybord 

undoIsActive flag is to stop the actionListeners from being triggered while undoAction is setting values programmatically

On rereading the brief near the deadline, I did try to seperate the history feature into a seperate class but broke everything in doing so so its all in one class. Hopefully the code is still strctures in a pretty readable way.

