package com.salton123.sf.util.asynctask;

import android.os.HandlerThread;
import android.os.Looper;


/**
 * 异步任务工具。
 * 
 */
public final class AsyncTask {
	private Looper mTaskLooper;
	private SafeDispatchHandler mTaskHandler;
	private HandlerThread mThread;
	
	public AsyncTask() {
		this("AsyncTask");

	}
	
	public AsyncTask(String name) {
		mThread = new HandlerThread(name);
		mThread.start();

		mTaskLooper = mThread.getLooper();
		mTaskHandler = new SafeDispatchHandler(mTaskLooper);

	}
	
	/**
	 * 执行任务，单位milliseconds
	 */
	public void execute(Runnable command) {
		mTaskHandler.removeCallbacks(command);
		mTaskHandler.post(command);
	}
}
