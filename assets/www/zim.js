/**

 *  

 * @return Object literal singleton instance of Zim

 */

var Zim = function() {
};

/**
  * @param directory The directory for which we want the listing
  * @param successCallback The callback which will be called when directory listing is successful
  * @param failureCallback The callback which will be called when directory listing encouters an error
  */
Zim.prototype.list = function(directory,successCallback, failureCallback) {

 return PhoneGap.exec(    successCallback,    //Success callback from the plugin

      failureCallback,     //Error callback from the plugin

      'ZimPhoneGapPlugin',  //Tell PhoneGap to run "DirectoryListingPlugin" Plugin

      'list',              //Tell plugin, which action we want to perform

      [directory]);        //Passing list of args to the plugin

};

/**
 * @param zimFileName The zim file to use
 * @param articleTitle The title of the article to be retrieved
 * @param nameSpace	The namesepace. e.g. 'A' for article 'I' for images 
 * @param successCallback The callback which will be called when retrieving the article data is successful
 * @param failureCallback The callback which will be called when retrieving the article data encounters an error
 */

Zim.prototype.getArticleData = function(zimFileName, articleTitle, nameSpace, successCallback, failureCallback) {
	return PhoneGap.exec(    successCallback,    //Success callback from the plugin

	      failureCallback,     //Error callback from the plugin

	      'ZimPhoneGapPlugin',  //Tell PhoneGap to run "ZimPhoneGapPlugin" Plugin

	      'getArticleData',              //Tell plugin, which action we want to perform
	      [zimFileName,articleTitle, nameSpace]);        //Passing list of args to the plugin

	};


PhoneGap.addConstructor(function() {

                   PhoneGap.addPlugin("zim", new Zim());                   

               });