package org.chenmin.curl4java;


import com.sun.jna.Pointer;

public class Multi  {

	private static final CurlMultiLib lib = CurlMultiLib.INSTANCE;

	private Pointer handle;

	private int[] runningHandles = new int[1];

	public Multi() {
		handle = lib.curl_multi_init();
	}

	public int add(Easy easy) {
		return lib.curl_multi_add_handle(handle, easy.getHandle());
	}

	public int remove(Easy easy) {
		return lib.curl_multi_remove_handle(handle, easy.getHandle());
	}

	public int setopt(int option, Object value) {
		return lib.curl_multi_setopt(handle, option, value);
	}

	public int perform() {
		return lib.curl_multi_perform(handle, runningHandles);
	}

	public int getRunningHandles() {
		return runningHandles[0];
	}

	public void close() {
		if (handle != null) {
			lib.curl_multi_cleanup(handle);
			handle = null;
		}
	}

	public static void main(String[] args) {
		Multi multi = new Multi();

		Easy easy = new Easy();
		easy.setopt(CurlOption.CURLOPT_URL, "http://httpbin.org/ip");
		easy.setopt(CurlOption.CURLOPT_ACCEPT_ENCODING, "gzip");
		
		multi.add(easy);

		Easy easy2 = new Easy();
		easy2.setopt(CurlOption.CURLOPT_URL, "http://httpbin.org/user-agent");
		easy2.setopt(CurlOption.CURLOPT_ACCEPT_ENCODING, "gzip");
		easy2.setopt(CurlOption.CURLOPT_USERAGENT, "java curl");
		
		multi.add(easy2);
		for (;;) {
			multi.perform();
			if (multi.getRunningHandles() == 0) {
				break;
			}
		}
		System.out.println(easy.getInfo());
		System.out.println(easy2.getInfo());

		easy.close();
		easy2.close();
		multi.close();
	}

}
