CMPUT 301: Assignment 0
=======================
Stefan Martynkiw -- 1296154 -- smartynk@ualberta.ca
September 25, 2014



Copyright 2014 Stefan Martynkiw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


Attributed works. 
-----------------
All examples adapted from the Android documentation at http://developer.android.com are liscenced as Creative Commons Attribution 2.5 Generic (CC BY 2.5). Changes were made to ALL code copied from http://developer.android.com. In compliance with the CC BY 2.5 Liscence, a link to the liscence is presented here: http://creativecommons.org/licenses/by/2.5/legalcode.

Each use of code from developer.android.com is annotated by comments in the source code of the project.

Some answers were adapted from StackOverflow. Each instance of adaption in the code is clearly marked with a comment, a link to the liscence, and a link to the exact article used. In compliance with the liscence, a link to the liscence is provided here: http://creativecommons.org/licenses/by-sa/2.5/

Package Names 
-------------
The package name is com.example.smtd. This arises from "SMartynk ToDo". Eclipse's renaming refactor tools will likely break the XML resources.


Application Documentation
========================

-UML diagrams. UML diagrams are provided

-APK file. APK file is provided.

Basic Use
------
 - The main view has a todo list. Click the + button to add an item.
 - Tap and hold on an item to bring up the multi-select. From here you can delete one or more items.
 - As well, in the multi-select, you can archive and email multiple items.
 - In the settings menu, the "Toggle Archive View" option toggles between the unarchived list and the archived list. 
 - In this menu, Email All Items lets you send all (both archived and unarchived) items.
 - Summarize items shows the required summary counts.

Things not immediately obvious from the UML Diagram
====================================================
-tMultiChoiceListener takes in two adapters (adapter, otheradapter). When the Activity (called TodoList) is in 
 unarchived mode, the instance of TMultiChoiceListener is set with setTodoAdapter(unarchived, archived) adapters.
 When the mode of the application is toggled, toggleArchiveView() changes which adapter is bound to the ListView. 
 It also sets setTodoAdapter(archived, unarchived).
 
 -tMultiChoiceListener, upon the archive/unarchive action button clicked, moves the TItem from adapter to 
 otheradapter. Depending on how its member variables are set up, this has the effect of archiving or unarchiving 
 the item. Next time I'm using fragments to change the view.
 
 -TItem has an isSelected boolean. This refers to whether or not its selected in the multi chooser. This is probably
 bad OO. I should have made another adapter with observers to the two adapters containing data.

