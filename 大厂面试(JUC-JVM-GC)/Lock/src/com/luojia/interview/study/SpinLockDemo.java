package com.luojia.interview.study;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * @author Romantic-Lei
 * @create 2020��5��9��
 * ʵ��һ��������
 * �������ô���ѭ���Ƚϻ�ȡֱ���ɹ�Ϊֹ��û������wait������
 * 
 * ͨ��CAS���������������A�߳��Ƚ�������myLock�����Լ�������5���ӣ�B�����
 * ��ǰ���̳߳�����������null������ֻ��ͨ�������ȴ���ֱ��A�ͷ���B����Ϊֹ
 */
public class SpinLockDemo {
	
	// ԭ�������߳�
	AtomicReference<Thread> atomicReference = new AtomicReference<>();
	
	public void myLock() {
		Thread thread = Thread.currentThread();
		System.out.println(Thread.currentThread().getName()+"\t come in");
		
		while (!atomicReference.compareAndSet(null, thread)) {
//			System.out.println("����");
		}
	}
	
	public void myUnlock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName()+"\t invoked myUnlock()");
	}
	
	public static void main(String[] args) {
		SpinLockDemo spinLockDemo = new SpinLockDemo();
		
		new Thread(() -> {
			try {
				spinLockDemo.myLock();
				TimeUnit.SECONDS.sleep(4);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			spinLockDemo.myUnlock();
		}, "AA").start();
		
		// ��֤A�߳��Ƚ���
		try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace(); }
		
		new Thread(() -> {
			spinLockDemo.myLock();
			spinLockDemo.myUnlock();
		}, "BB").start();
		
	}

}
