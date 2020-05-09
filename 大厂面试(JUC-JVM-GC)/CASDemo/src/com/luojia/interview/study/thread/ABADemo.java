package com.luojia.interview.study.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA����Ľ����AtomicStampedReference
 * 
 * @author Romantic-Lei
 * @create 2020��5��9��
 */
public class ABADemo {

	static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
	static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100, 1);

	public static void main(String[] args) {
		
		System.out.println("===========������ABA����Ĳ���============");
		new Thread(() -> {
			atomicReference.compareAndSet(100, 101);
			atomicReference.compareAndSet(101, 100);
		}, "t1").start();

		new Thread(() -> {
			// �߳�t2��ͣ1���ӣ���֤�߳�t1���һ��ABA����
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(atomicReference.compareAndSet(100, 2020) + "\t" + atomicReference.get());
		}, "t2").start();

		// ��ͣһ���̣߳���֤���������߳����
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("===========������ABA����Ľ��============");
		
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "��һ�ΰ汾��" + stamp);
			
			// ��ͣ�߳�1�� t3�߳�
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
			System.out.println(Thread.currentThread().getName() + "�ڶ��ΰ汾��" + atomicStampedReference.getStamp());
			
			atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
			System.out.println(Thread.currentThread().getName() + "�����ΰ汾��" + atomicStampedReference.getStamp());
		}, "t3").start();
		
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "��һ�ΰ汾��" + stamp);
			
			// ��ͣ�߳�3�� t4�߳�, ��֤�߳�t3���һ��ABA����
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = atomicStampedReference.compareAndSet(100, 2020, stamp, stamp+1);
			
			System.out.println(Thread.currentThread().getName() + "\t�޸ĳɹ��� " + result + "\t��ǰ���°汾��" + atomicStampedReference.getStamp());
			
			System.out.println(Thread.currentThread().getName() + "\t��ǰʵ������ֵ��" + atomicStampedReference.getReference());
		}, "t4").start();
		
	}

}
