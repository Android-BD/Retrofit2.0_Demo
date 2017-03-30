package com.demo.zenglb.retrofittest.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.demo.zenglb.retrofittest.R;
import com.demo.zenglb.retrofittest.base.BaseActivity;
import com.demo.zenglb.retrofittest.http.HttpCall;
import com.demo.zenglb.retrofittest.http.HttpCallBack;
import com.demo.zenglb.retrofittest.http.HttpResponse;
import com.demo.zenglb.retrofittest.http.download.AppUpdateUtils;
import com.demo.zenglb.retrofittest.http.download.FileUtil;
import com.demo.zenglb.retrofittest.http.download.ProgressResponseBody;
import com.demo.zenglb.retrofittest.http.param.Datas;
import com.demo.zenglb.retrofittest.http.param.JobsData;
import com.demo.zenglb.retrofittest.http.param.LoginParams;
import com.demo.zenglb.retrofittest.http.param.PushReportData;
import com.demo.zenglb.retrofittest.http.result.EasyResult;
import com.demo.zenglb.retrofittest.http.result.LoginResult;
import com.demo.zenglb.retrofittest.http.result.Messages;
import com.demo.zenglb.retrofittest.http.result.Modules;
import com.demo.zenglb.retrofittest.http.result.VersionMess;
import com.google.gson.Gson;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * If your api is not restful,maybe this will be helpful;Retrofit2.0 example，include Log in ,Log out ,Token is disable,Refresh Token,download app and update etc
 * <p>
 * Retrofit2.0 ，针对api不是那么Restful 的情况再次封装Http 请求,包括登录（oauth），登出，基本http请求和下载升级，自动刷新Token等完整的http 操作的闭环、
 * <p>
 * <p>
 * anylife.zlb@gamil.com
 */
public class MainActivity extends BaseActivity {
	private final String TAG = MainActivity.class.getSimpleName();
	private TextView message;
	private TextView textViewLogin;
	public static String refreshToken;
	private RecyclerView mRecyclerView = null;
	private MyAdapter myAdapter;
	private List<String> data = new ArrayList<>();

	private static String getUpdateJsonStr = "{\n" +
			"    \"type\": \"update\",\n" +
			"    \"appVersion\": 38,\n" +
			"    \"appMessage\": \"WHAT IS NEW\\n• Now you can draw or add text and emojis to photos\\n• In groups, you can now mention specific people by typing the @ symbol\",\n" +
			"    \"downLoadUrl\": \"http://test-default-1.oss-cn-shenzhen.aliyuncs.com/201603/APP/38_BipbipMain030301.apk\",\n" +
			"    \"isForceUpdate\": \"true\"\n" +
			"}";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		message = (TextView) findViewById(R.id.message);
		textViewLogin = (TextView) findViewById(R.id.login);
		textViewLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Login();
			}
		});

		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		myAdapter = new MyAdapter(this, data);
		mRecyclerView.setAdapter(myAdapter);
		myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				switch (position) {
					case 0:
						refreshToken();
						break;
					case 1:
						requestModules();
						break;
					case 2:
						logout();
						Toast.makeText(MainActivity.this, "Log out success", Toast.LENGTH_SHORT).show();
						break;
					case 3:
						killToken();
						Toast.makeText(MainActivity.this, "Disable Token", Toast.LENGTH_SHORT).show();
						break;
					case 4:
						killRefreshToken();
						Toast.makeText(MainActivity.this, "Disable Refresh Token", Toast.LENGTH_SHORT).show();
						break;
					case 5:
						checkUpdate();
						break;
					case 6:
						getMessages();
						break;
				}
			}

			@Override
			public void onItemLongClick(View view, int position) {

			}
		});
	}

	/**
	 * Simulate Log out
	 * 模拟登出
	 */
	private void logout() {
		refreshToken = "refreshToken set logout,is null le";
		HttpCall.setToken("Bearer TokenSet-Logout");
		mRecyclerView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 模拟Token失效(过期啊，在其他地方登录)
	 * Disable token (long time no use app,token can use only in 30 days )
	 */
	private void killToken() {
		String tempToken = getOsDisplay("Bearer TokenSet-killToken");
		try {
			URLDecoder.decode(tempToken, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpCall.setToken(tempToken);
	}

	/**
	 * Disable refreshToken过期啊 (long time no use app,refreshToken can use only in 35 days )
	 * refreshToken过期啊，长久没有使用，当然这个时候token也已经失效了
	 */
	private void killRefreshToken() {
		killToken();  //Refresh Token is disable ,so is token
		refreshToken = "refreshToken - killRefreshToken";
	}

	private String getOsDisplay(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	private void updateFuncs() {
		if (data.size() == 0) {
			String[] temp = this.getResources().getStringArray(R.array.languages);
			for (int i = 0; i < temp.length; i++) {
				data.add(temp[i]);
			}
			myAdapter.notifyDataSetChanged();
		}
	}


	/**
	 *
	 */
	private void TestPostJobs() {
		List<JobsData> jobsDatas = new ArrayList<>();
		JobsData jobsData1 = new JobsData("44030020", "LB10991");
		JobsData jobsData2 = new JobsData("44030021", "LB10992");
		jobsDatas.add(jobsData1);
		jobsDatas.add(jobsData2);

		//2.Generic Programming Techniques is the basis of Android develop
		Call<HttpResponse<EasyResult>> postJobsReq = HttpCall.getApiService(null).postJobs(jobsDatas);
		postJobsReq.enqueue(new HttpCallBack<HttpResponse<EasyResult>>(this) {
			@Override
			public void onSuccess(HttpResponse<EasyResult> loginResultHttpResponse) {
//				Log.e(TAG, loginResultHttpResponse.getResult().toString());
//				message.setText(loginResultHttpResponse.getResult().toString());
			}

			@Override
			public void onFailure(int code, String messageStr) {
				super.onFailure(code, messageStr);
				message.setText(code + "  !loginCall!  " + messageStr);
			}
		});
	}


	/**
	 *
	 */
	private void TestReportPushLog() {
		List<PushReportData> pushReportDatas = new ArrayList<>();
		PushReportData pushReportData1 = new PushReportData("1111111", "11111", "111");
		PushReportData pushReportData2 = new PushReportData("2222222", "22222", "222");

		pushReportDatas.add(pushReportData1);
		pushReportDatas.add(pushReportData2);

		Datas<String> datas = new Datas(new Gson().toJson(pushReportDatas)); //好恶心啊，有这样的搞的吗？

		//2.Generic Programming Techniques is the basis of Android develop
		Call<HttpResponse<EasyResult>> RepostResult = HttpCall.getApiService(null).postPushData(datas);
		RepostResult.enqueue(new HttpCallBack<HttpResponse<EasyResult>>(this) {
			@Override
			public void onSuccess(HttpResponse<EasyResult> loginResultHttpResponse) {
//				Log.e(TAG, loginResultHttpResponse.getResult().toString());
//				message.setText(loginResultHttpResponse.getResult().toString());
			}

			@Override
			public void onFailure(int code, String messageStr) {
				super.onFailure(code, messageStr);
				message.setText(code + "  !loginCall!  " + messageStr);
			}
		});
	}


	/**
	 * Login
	 */
	private void Login() {
		//init token and refresh token
		refreshToken = "";
		HttpCall.setToken("");
		LoginParams loginParams = new LoginParams();

		//FBI WARMING,For mutual exchange of learning only,Don't use for other purposes !
		loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
		loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
		loginParams.setGrant_type("password");
		loginParams.setUsername("18826562075");
		loginParams.setPassword("zxcv1234");

		//2.Generic Programming Techniques is the basis of Android develop
		Call<HttpResponse<LoginResult>> loginCall = HttpCall.getApiService(null).goLogin(loginParams);
		loginCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>(this) {
			@Override
			public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
				Log.e(TAG, loginResultHttpResponse.getResult().toString());
				message.setText(loginResultHttpResponse.getResult().toString());
				HttpCall.setToken("Bearer " + loginResultHttpResponse.getResult().getAccessToken());
				refreshToken = loginResultHttpResponse.getResult().getRefreshToken();
				updateFuncs();
				mRecyclerView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailure(int code, String messageStr) {
				super.onFailure(code, messageStr);
				message.setText(code + "  !loginCall!  " + messageStr);
			}
		});
	}

	/**
	 * Reresh Token
	 */
	private void refreshToken() {
		LoginParams loginParams = new LoginParams();
		loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
		loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
		loginParams.setGrant_type("refresh_token");
		loginParams.setRefresh_token(refreshToken);

		Call<HttpResponse<LoginResult>> refreshTokenCall = HttpCall.getApiService(null).refreshToken(loginParams); //刷新Token
		refreshTokenCall.enqueue(new HttpCallBack<HttpResponse<LoginResult>>(this) {
			@Override
			public void onSuccess(HttpResponse<LoginResult> loginResultHttpResponse) {
				Log.e(TAG, loginResultHttpResponse.getResult().toString());
				message.setText(loginResultHttpResponse.getResult().toString());  //display result
				HttpCall.setToken("Bearer " + loginResultHttpResponse.getResult().getAccessToken());
				refreshToken = loginResultHttpResponse.getResult().getRefreshToken();
			}

			@Override
			public void onFailure(int code, String messageStr) {
				super.onFailure(code, messageStr);
				message.setText(code + "  ! refreshToken !  " + messageStr);
			}
		});
	}


	/**
	 * test get http
	 */
	private void requestModules() {
		Call<HttpResponse<Modules>> getModulesCall = HttpCall.getApiService(null).getModules();
		getModulesCall.enqueue(new HttpCallBack<HttpResponse<Modules>>(this) {
			@Override
			public void onSuccess(HttpResponse<Modules> getModulesCallResponse) {
				Log.e(TAG, getModulesCallResponse.getResult().toString());
				message.setText(getModulesCallResponse.getResult().toString());
			}

			@Override
			public void onFailure(int code, String failedText) {
				super.onFailure(code, failedText);
				message.setText(code + "@@getModulesCall@@" + failedText);
			}
		});
	}


	/**
	 * check update
	 *
	 * @Streaming and some rxjava
	 */
	private void checkUpdate() {
		final VersionMess versionMess = new Gson().fromJson(getUpdateJsonStr, VersionMess.class);
		if (versionMess != null && versionMess.getAppVersion() > 0 && !TextUtils.isEmpty(versionMess.getDownLoadUrl())) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("UPDATE")
					.setMessage(versionMess.getAppMessage())
					.setCancelable(false)
					.setPositiveButton("update", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(MainActivity.this, versionMess.getDownLoadUrl(), Toast.LENGTH_SHORT).show();
							downLoadApp(versionMess.getDownLoadUrl());
						}
					})
					.show();
		}
	}


	/**
	 * download app
	 */
	private void downLoadApp(String downloadUrl) {
		final ProgressDialog dialog = AppUpdateUtils.getDownLoadProgressDialog(MainActivity.this);

		HttpCall.getApiService(
				new ProgressResponseBody.ProgressListener() {
					@Override
					public void update(long bytesRead, long contentLength, boolean done) {
						dialog.setMax((int) (contentLength / 1024));
						dialog.setProgress((int) (bytesRead / 1024));
					}
				}
		).downloadApp(downloadUrl).subscribeOn(Schedulers.io())
				.map(new Func1<ResponseBody, File>() {
					@Override
					public File call(ResponseBody responseBody) {
						File apk = new File(Environment.getExternalStorageDirectory(), "YourAppName");
						FileUtil.save(responseBody.byteStream(), apk);
						return apk;
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<File>() {
					@Override
					public void call(File apk) {
						dialog.dismiss();
						AppUpdateUtils.getInstallAppDialog(MainActivity.this, apk).show();
					}
				});
	}

	/**
	 * Test @Query and result is json array, not json object
	 * <p>
	 * Call<HttpResponse< jsonArray >> ,not Call<HttpResponse< jsonObj >>
	 */
	private void getMessages() {
		Call<HttpResponse<List<Messages>>> getMsgsCall = HttpCall.getApiService(null).getMessages(1, 3);
		getMsgsCall.enqueue(new HttpCallBack<HttpResponse<List<Messages>>>(this) {
			@Override
			public void onSuccess(HttpResponse<List<Messages>> listHttpResponse) {
				Log.e(TAG, listHttpResponse.getResult().toString());
				message.setText(listHttpResponse.getResult().toString());
			}

			@Override
			public void onFailure(int code, String messageStr) {
				super.onFailure(code, messageStr);
				message.setText(code + "@@checkMobileCall@@" + messageStr);      //
			}
		});
	}

}
