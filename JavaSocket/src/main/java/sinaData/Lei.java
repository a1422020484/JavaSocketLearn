package sinaData;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;

public class Lei {
	public static GuPiaoData selelctedPiaoData;

	public static void main(String[] args) throws IOException {
		UIData.getInstance();
	}

	public static void writeUIData() {
		if (selelctedPiaoData == null || selelctedPiaoData.getName() == "") {
			return;
		}
		JLabel l = UIData.getInstance().getNameJlabel();
		JFrame f = UIData.getInstance().getMainFrame();
		UIData.getInstance().getNameJlabel().setText(selelctedPiaoData.getName());
		UIData.getInstance().getTodayOpenPriceJlabel().setText(selelctedPiaoData.getNowPrice());
		// 文字颜色
		l.setForeground(Color.red);
		l.setBounds(50, 150, 280, 30);
		UIData.getInstance().getTodayOpenPriceJlabel().setForeground(Color.red);
		UIData.getInstance().getTodayOpenPriceJlabel().setBounds(130, 150, 280, 30);
		f.add(UIData.getInstance().getTodayOpenPriceJlabel());
		f.add(l);
		f.setVisible(true);
	}

	static ScheduledExecutorService runningTimer = null;

	public static ActionListener listenerStart = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton startButton = UIData.getInstance().getStartButton();
			if (startButton.getText().equals("停止监控")) {
				runningTimer.shutdownNow();
				startButton.setText("开始监控");
				UIData.getInstance().getNameJlabel().setText("");
			} else {
				System.out.println("我要启动了");
				ScheduledExecutorService runningTime = Executors.newScheduledThreadPool(1);
				runningTimer = runningTime;
				runningTime.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						getUrl();
						writeUIData();
					}
				}, 0, 1000, TimeUnit.MILLISECONDS);
				startButton.setText("停止监控");
			}
		}
	};

	@Test
	public static void getUrl() {
		String url = "http://hq.sinajs.cn/list=sh600985";
		try {
			URL u = new URL(url);
			InputStream in = null;
			StringBuilder sb = new StringBuilder();
			String str;
			try {
				in = u.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "GB2312"));
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				String result = sb.toString();
				String[] stocks = result.split(";");
				for (String stock : stocks) {
					String[] datas = stock.split(",");
					if (datas.length <= 30) {
						continue;
					}
					selelctedPiaoData = formatData(datas);
				}
				System.out.println(selelctedPiaoData.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (in != null) {
					in.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static GuPiaoData formatData(String[] datas) {
		GuPiaoData guPiaoData = new GuPiaoData();
		// 根据对照自己对应数据
		guPiaoData.setName(datas[0].split("\"")[1]);
		guPiaoData.setTodayOpenPrice(datas[1]);
		guPiaoData.setYstdayOpenPrice(datas[2]);
		guPiaoData.setNowPrice(datas[3]);
		guPiaoData.setTodayHightPrice(datas[4]);
		guPiaoData.setTodayLowPrice(datas[5]);
		guPiaoData.setJingmaiPayPrice1(datas[6]);
		guPiaoData.setJingmaiGetPrice1(datas[7]);
		guPiaoData.setChengjiaoNum(datas[8]);
		guPiaoData.setChengjiaoTall(datas[9]);
		guPiaoData.setPayPrice1(datas[10]);
		guPiaoData.setPayNumber1(datas[11]);
		guPiaoData.setPayPrice2(datas[12]);
		guPiaoData.setPayNumber2(datas[13]);
		guPiaoData.setPayPrice3(datas[14]);
		guPiaoData.setPayNumber3(datas[15]);
		guPiaoData.setPayPrice4(datas[16]);
		guPiaoData.setPayNumber4(datas[17]);
		guPiaoData.setPayPrice5(datas[18]);
		guPiaoData.setPayNumber5(datas[19]);

		guPiaoData.setGetPrice1(datas[20]);
		guPiaoData.setGetNumber1(datas[21]);
		guPiaoData.setGetPrice2(datas[22]);
		guPiaoData.setGetNumber2(datas[23]);
		guPiaoData.setGetPrice3(datas[24]);
		guPiaoData.setGetNumber3(datas[25]);
		guPiaoData.setGetPrice4(datas[26]);
		guPiaoData.setGetNumber4(datas[27]);
		guPiaoData.setGetPrice5(datas[28]);
		guPiaoData.setGetNumber5(datas[29]);
		guPiaoData.setData(datas[30]);
		guPiaoData.setTime(datas[31]);
		guPiaoData.setOther(datas[32]);
		return guPiaoData;
	}
}
