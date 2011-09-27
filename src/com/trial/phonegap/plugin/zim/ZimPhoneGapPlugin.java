/**
 * 
 */
package com.trial.phonegap.plugin.zim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;
import org.openzim.ZIMTypes.ZIMFile;
import org.openzim.ZIMTypes.ZIMReader;

import android.util.Log;

import com.phonegap.api.Plugin;

import com.phonegap.api.PluginResult;

import com.phonegap.api.PluginResult.Status;
/**
 * @author Christian
 *
 */
public class ZimPhoneGapPlugin extends Plugin {

	/** List Action */

	public static final String ACTION_GETARTICLEDATA="getArticleData";

	/*

	* (non-Javadoc)

	* 

	* @see com.phonegap.api.Plugin#execute(java.lang.String,

	* org.json.JSONArray, java.lang.String)

	*/

	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		Log.d("zimgap", "Plugin Called");

		PluginResult result = null;

		if (ACTION_GETARTICLEDATA.equals(action)) {
			try {
				String zimFileName = data.getString(0);
				String articleTitle = data.getString(1);
				
				String nameSpace = data.getString(2);
				if (nameSpace.length()!=  1) {
					throw new JSONException("nameSpace parameter is not a char");
				}
				JSONObject articleData = getArticleData(zimFileName,articleTitle,nameSpace.charAt(0));

				Log.d("zimgap", "end of zimTest");

				result = new PluginResult(Status.OK, articleData);

			}  catch (JSONException jsonEx) {

				Log.d("zimgap", "Got JSON Exception "+ jsonEx.getMessage());

				result = new PluginResult(Status.JSON_EXCEPTION);

			}

		}

		else {

			result = new PluginResult(Status.INVALID_ACTION);

			Log.d("zimgap", "Invalid action : "+action+" passed");

		}

		return result;
	}
	private JSONObject getArticleData(String zimFileName, String articleTitle, char nameSpace) throws JSONException {
		JSONObject articleData = new JSONObject();
        File zimFile = new File(zimFileName);
        Log.d("zimgap",""+zimFile.exists());
        ZIMFile file = new ZIMFile(zimFileName);
		// Associate the Zim File with a Reader
		ZIMReader zReader = new ZIMReader(file);

		try {
			ByteArrayOutputStream articleDataByteArray = zReader.getArticleData(articleTitle,nameSpace);
			if (articleDataByteArray==null) {
				Log.w("zimgap", "Article \""+articleTitle+"\" not found");
			} else {
				String articleText =articleDataByteArray.toString("utf-8");
				articleData.put("articletext", articleText);

				Log.d("zimgap","Article read successfully");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return articleData;		
	}
}
