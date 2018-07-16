package com.jianshi.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.dataSource.model.DataPackage;
import com.dataSource.model.JsjgData;

public class test1 implements Runnable{
	private static boolean b=false;
	private static test1 t;
	
	static{
		t=new test1();
	}	
	
	private test1(){
		
	}
	
	public static void start() {
		if(!b){
			new Thread(t).start();
			b=true;
		}
			
	}
	
	public static void stop() {
		b=false;
	}

	@Override
	public void run() {
		try {
			System.out.println("开始发数   =>  "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			Random random = new Random();
			long t=0;
            while (b) {
            	DataPackage pack=new DataPackage();
        		pack.setDataSource(0);
        		pack.setDeviceId(1+"");
        		pack.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        		pack.setSid("2");
                pack.addVal("1",random.nextInt(2)+"");
                pack.addVal("2",random.nextInt(100)+"");
                pack.addVal("3",random.nextInt(60)+"");
                pack.addVal("4",random.nextInt(200)+"");
                DataManager.put(pack);
                
                pack=new DataPackage();
        		pack.setDataSource(0);
                pack.setDeviceId(2+"");
        		pack.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        		pack.setSid("2");
        		pack.addVal("5",random.nextInt(2)+"");
                pack.addVal("6",random.nextInt(100)+"");
                pack.addVal("7",random.nextInt(60)+"");        		
                DataManager.put(pack);
                
                pack=new DataPackage();
        		pack.setDataSource(0);
        		pack.setDeviceId(24459+"");
        		pack.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        		pack.setSid("2");
                pack.addVal("1",random.nextInt(2)+"");
                pack.addVal("2",random.nextInt(100)+"");
                pack.addVal("3",random.nextInt(60)+"");
                DataManager.put(pack);
                
                pack=new DataPackage();
        		pack.setDataSource(0);
        		pack.setDeviceId(24460+"");
        		pack.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        		pack.setSid("2");
                pack.addVal("4",random.nextInt(2)+"");
                pack.addVal("5",random.nextInt(100)+"");
                DataManager.put(pack);
                
                JsjgData jsjg=new JsjgData();
                jsjg.setSatId("24459");
                jsjg.setJsjgCode("TC");
                jsjg.addVal("number", "K12");
                jsjg.addVal("code", "12");
                jsjg.addVal("texec", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
                jsjg.addVal("count", random.nextInt(10)+"");
                jsjg.addVal("dev", "100");
                jsjg.addVal("sloop", "成功");
                jsjg.addVal("bloop", "失败");
                DataManager.addJsjg(jsjg);
                
                //System.out.println("数据时间   =>  "+pack.getDataTime());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		finally{
		}
	}
	
	public static void main(String[] args) {
		test1 test=new test1();
		new Thread(test).start();
	}

}
