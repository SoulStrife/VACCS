To start
1. Import on root VACCS directory as an existing maven project
2. Preferences->Plug-in Development->API Baselines, change Missing API baseline from error to ignore
3. Preferences->Plug-in Development->Target Platform, select Eclipse 4.3 CDT
4. Run as eclipse application

For Linux:
install the package libwebkitgtk-1.0-0 for the swt browser to work

To add function names to check for edit:
/com.googlecode.cppcheclipse.ui/files/functionNames.txt

To add function groups to assign function names to:
/com.googlecode.cppcheclipse.ui/files/functionGroups.txt

To export plugin at the end of development I recommend looking at this tutorial which should point you in the right direction:
http://www.vogella.com/tutorials/EclipsePlugin/article.html (specifically look at creating an update site)
