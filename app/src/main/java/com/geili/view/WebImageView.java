package com.geili.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.geili.util.CommonUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class WebImageView extends ImageView
{

	private String mCacheFolder;

	private int quality;

	private Bitmap bitmap;

	public WebImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.quality = attrs.getAttributeIntValue("android", "quatity", 100);

		this.mCacheFolder = CommonUtils.getFilePath()+"/load/images";
	}
	
	@Override
	public boolean isInEditMode() {return true;};

	public WebImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WebImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public String getmCacheFolder() {
		return mCacheFolder;
	}

	public void setmCacheFolder(String mCacheFolder) {
		this.mCacheFolder = mCacheFolder;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			downLoadThread = null;
			if (bitmap != null)
				setImageBitmap(bitmap);
		}
	};
	Thread downLoadThread = null;

	@Override
	public void setImageURI(final Uri uri) {
		if(uri==null)return;
		final String path = uri.toString();
		Log.d(getClass().getSimpleName(), "image path=" + path);
		if (uri.getScheme() !=null && uri.getScheme().startsWith("http")) {
			StringBuilder folder = new StringBuilder(mCacheFolder);

			folder.append(uri.getPath());
			int s = folder.indexOf("?");
			if (s > -1) {
				folder.delete(s, folder.length());
			}
			final File localPath = new File(folder.toString());

			final String fileName = localPath.getName();

			if (this.mCacheFolder == null) {
				Log.w(getClass().getSimpleName(), "not set cache folder,image will not download");
				return;
			}

			downLoadThread = new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = new Message();

					Bundle data = new Bundle();
					if (localPath.exists()) {
						bitmap = getLoacalBitmap(localPath.getAbsolutePath());
						if (bitmap != null)
							msg.what = 1;
					} else {
						InputStream is = CommonUtils.getStream(path);
						//Log.d(getClass().getSimpleName(), is.toString());
						if(is!=null){ 
							
							bitmap = BitmapFactory.decodeStream(is);
							String imgpath = saveFile(bitmap, localPath.getParent(), fileName);
							if (imgpath != null) {
								msg.what = 1;
							}
						}
					}

					msg.setData(data);

					handler.sendMessage(msg);
				}
			});
			downLoadThread.start();
		} else {
			super.setImageURI(uri);
		}
	}

	/**
	 * 加载本地图片 http://bbs.3gstdy.com
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 * @return String 
	 */
	public String saveFile(Bitmap bm, String folder, String fileName)  {
		File dirFile = new File(folder + "/");
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				Log.e(getClass().getSimpleName(), "create folder failed:" + dirFile);
			}
		}

		File myCaptureFile = new File(dirFile, fileName);
		Log.d(getClass().getSimpleName(), myCaptureFile.getPath());
		 if(!myCaptureFile.exists()){
			 try {
				myCaptureFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			if(bm != null) 
				bm.compress(Bitmap.CompressFormat.JPEG, 40, bos);
			else {
				return null;
			}
			
		} catch (FileNotFoundException e) {
			return null;
		} finally{
			if(bos !=null)
			try {
				bos.flush();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return myCaptureFile.getAbsolutePath();
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	@Override
	protected void finalize() throws Throwable {
		if(downLoadThread!=null)downLoadThread.interrupt();
		super.finalize();
	};
	
//	public Bitmap returnBitMap(String url) {
//		URL myFileUrl = null;
//		Bitmap bitmap = null;
//		try {
//			myFileUrl = new URL(url);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		try {
//			HttpURLConnection conn = (HttpURLConnection) myFileUrl
//					.openConnection();
//			conn.setDoInput(true);
//			conn.connect();
//			InputStream is = conn.getInputStream();
//			//Log.e("", conn.getResponseMessage());
//			bitmap = BitmapFactory.decodeStream(is);
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return bitmap;
//	};


}

