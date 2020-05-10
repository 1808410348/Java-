package com.luojia.interview.study;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author Romantic-Lei
 * @create 2020��5��9��
 * ����߳�ͬʱ��ȡһ����Դ��û���κ����⣬����Ϊ�����㲢��������ȡ������ԴӦ�ÿ���ͬʱ����
 * ����
 * �����һ���߳���ȥд������Դ���Ͳ������������߳̿��ԶԸ���Դ���ж���д
 * ����
 * 	��-���ܹ���
 * 	��-д���ܹ���
 *	 д-д���ܹ���
 */
public class ReadWriteLockDemo {
	
	public static void main(String[] args) {
		MyCache myCache = new MyCache();
		
		for (int i = 1; i <= 5; i++) {
			int tempInt = i;
			new Thread(() -> {
				myCache.put(tempInt+"", tempInt);
			}, String.valueOf(i)).start();
		}
		for (int i = 1; i <= 5; i++) {
			int tempInt = i;
			new Thread(() -> {
				myCache.get(tempInt+"");
			}, String.valueOf(i)).start();
		}
	}
}

// ��Դ��
class MyCache{
	private volatile Map<String, Object> map = new HashMap<>();
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public void put(String key, Object value) {
		rwLock.writeLock().lock();
		System.out.println(Thread.currentThread().getName()+"\t����д��"+key);
		try {
			// ģ������ӵ��
			TimeUnit.MILLISECONDS.sleep(300);
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+"\tд�����"+value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public void get(String key) {
		rwLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t���ڶ�ȡ");
			// ģ������ӵ��
			TimeUnit.MILLISECONDS.sleep(300);
			Object res = map.get(key);
			System.out.println(Thread.currentThread().getName()+"\t��ȡw���"+res);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rwLock.readLock().unlock();
		}
	}
	
}
