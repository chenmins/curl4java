package org.chenmin.curl4java;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface CurlEasyLib extends Library {

	interface DataHandler extends Callback {
		//size_t write_callback(char *ptr, size_t size, size_t nmemb, void *userdata);
		int invoke(Pointer contents, int size, int nmemb, Pointer userp);
	}
	
	interface ProgressHandler extends Callback {
		//int progress_callback(void *clientp,   double dltotal,   double dlnow,   double ultotal,   double ulnow);
		int invoke(Pointer contents, double dltotal, double dlnow,   double ultotal,   double ulnow);
	}
	

	CurlEasyLib INSTANCE = (CurlEasyLib) Native.loadLibrary("curl",
			CurlEasyLib.class);

	String curl_version();

	Pointer curl_easy_init();

	void curl_easy_reset(Pointer curl);

	Pointer curl_easy_duphandle(Pointer curl);

	void curl_easy_cleanup(Pointer curl);

	int curl_easy_setopt(Pointer curl, int option, Object obj);

	int curl_easy_getinfo(Pointer curl, int info, Object args);

	int curl_easy_perform(Pointer curl);

	String curl_easy_strerror(int errorNum);

	curl_slist.ByReference curl_slist_append(curl_slist.ByReference slist, String header);

	void curl_slist_free_all(curl_slist.ByReference slist);
	
	void curl_free(Pointer p);

}
