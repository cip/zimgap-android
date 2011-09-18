/**
 * 
 */
package com.trial.phonegap.plugin.zim;

import java.io.File;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import android.util.Log;

import com.phonegap.api.Plugin;

import com.phonegap.api.PluginResult;

import com.phonegap.api.PluginResult.Status;
/**
 * @author Christian
 *
 */
public class DirectoryListPlugin extends Plugin {

	/** List Action */

	public static final String ACTION_LIST="list";
	public static final String ACTION_ZIMTEST="zim_test";

	/*

	* (non-Javadoc)

	* 

	* @see com.phonegap.api.Plugin#execute(java.lang.String,

	* org.json.JSONArray, java.lang.String)

	*/

	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		Log.d("DirectoryListPlugin", "Plugin Called");

		PluginResult result = null;

		if (ACTION_LIST.equals(action)) {

			try {

				String fileName = data.getString(0);

				JSONObject fileInfo = getDirectoryListing(new File(fileName));

				Log.d("DirectoryListPlugin", "Returning "+ fileInfo.toString());

				result = new PluginResult(Status.OK, fileInfo);

			} catch (JSONException jsonEx) {

				Log.d("DirectoryListPlugin", "Got JSON Exception "+ jsonEx.getMessage());

				result = new PluginResult(Status.JSON_EXCEPTION);

			}

		} else if (ACTION_ZIMTEST.equals(action)) {
			try {
				String zimFileName = data.getString(0);
				String articleTitle = data.getString(1);
				
				JSONObject articleData = zimTest(zimFileName,articleTitle);

				Log.d("zimgap", "end of zimTest");

				result = new PluginResult(Status.OK, articleData);

			}  catch (JSONException jsonEx) {

				Log.d("DirectoryListPlugin", "Got JSON Exception "+ jsonEx.getMessage());

				result = new PluginResult(Status.JSON_EXCEPTION);

			}

		}

		else {

			result = new PluginResult(Status.INVALID_ACTION);

			Log.d("DirectoryListPlugin", "Invalid action : "+action+" passed");

		}

		return result;
	}
	private JSONObject zimTest(String zimFileName, String articleTitle) {
		JSONObject articleData = new JSONObject();
		
		return articleData;
		/*fileInfo.put("filename", file.getName());

		fileInfo.put("isdir", file.isDirectory());/

		if (file.isDirectory()) {

			JSONArray children = new JSONArray();

			fileInfo.put("children", children);

			if (null != file.listFiles()) {

				for (File child : file.listFiles()) {

					children.put(getDirectoryListing(child));

				}

			}

		}

		return fileInfo;*/
	}
	/**

	 * Gets the Directory listing for file, in JSON format

	 * @param file The file for which we want to do directory listing

	 * @return JSONObject representation of directory list. 
	 *  e.g {"filename":"/sdcard","isdir":true,"children":[{"filename":"a.txt","isdir":false},{..}]}

	 * @throws JSONException

	 */

	private JSONObject getDirectoryListing(File file) throws JSONException {

		JSONObject fileInfo = new JSONObject();

		fileInfo.put("filename", file.getName());

		fileInfo.put("isdir", file.isDirectory());

		if (file.isDirectory()) {

			JSONArray children = new JSONArray();

			fileInfo.put("children", children);

			if (null != file.listFiles()) {

				for (File child : file.listFiles()) {

					children.put(getDirectoryListing(child));

				}

			}

		}

		return fileInfo;

	}

}
