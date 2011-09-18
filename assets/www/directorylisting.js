/**

 *  

 * @return Object literal singleton instance of DirectoryListing

 */

var DirectoryListing = function() {
};

/**
  * @param directory The directory for which we want the listing
  * @param successCallback The callback which will be called when directory listing is successful
  * @param failureCallback The callback which will be called when directory listing encouters an error
  */
DirectoryListing.prototype.list = function(directory,successCallback, failureCallback) {

 return PhoneGap.exec(    successCallback,    //Success callback from the plugin

      failureCallback,     //Error callback from the plugin

      'DirectoryListPlugin',  //Tell PhoneGap to run "DirectoryListingPlugin" Plugin

      'list',              //Tell plugin, which action we want to perform

      [directory]);        //Passing list of args to the plugin

};

DirectoryListing.prototype.zimtest = function(zimFileName, articleTitle,successCallback, failureCallback) {
	return PhoneGap.exec(    successCallback,    //Success callback from the plugin

	      failureCallback,     //Error callback from the plugin

	      'DirectoryListPlugin',  //Tell PhoneGap to run "DirectoryListingPlugin" Plugin

	      'zimtest',              //Tell plugin, which action we want to perform
	      [zimFileName,articleTitle]);        //Passing list of args to the plugin

	};


PhoneGap.addConstructor(function() {

                   PhoneGap.addPlugin("directoryListing", new DirectoryListing());                   

               });