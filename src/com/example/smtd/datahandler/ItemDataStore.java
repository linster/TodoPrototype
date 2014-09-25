/*Copyright 2014 Stefan Martynkiw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.smtd.datahandler;

import java.util.ArrayList;

import com.example.smtd.TItem;

public abstract class ItemDataStore {

	public abstract ArrayList<TItem> UnPackData(String filename);
	public abstract boolean PackData(ArrayList<TItem> source, String filename);
	
	
}
